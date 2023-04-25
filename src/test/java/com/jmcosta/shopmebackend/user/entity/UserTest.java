package com.jmcosta.shopmebackend.user.entity;

import com.jmcosta.shopmebackend.roles.entity.Role;
import com.jmcosta.shopmebackend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role admin = entityManager.find(Role.class, 1);
        User jmcosta = new User("jmcosta@java.net", "123", "Jose", "Costa");
        jmcosta.addRole(admin);
        User savedUser = userRepository.save(jmcosta);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRole() {
        Role editor = entityManager.find(Role.class, 3);
        Role assistant = entityManager.find(Role.class, 5);
        User sicosta = new User("sicosta@java.net", "123", "Sandra", "Costa");
        sicosta.addRole(editor);
        sicosta.addRole(assistant);
        User savedUser = userRepository.save(sicosta);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

}