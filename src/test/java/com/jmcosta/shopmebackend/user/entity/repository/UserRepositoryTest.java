package com.jmcosta.shopmebackend.user.entity.repository;

import com.jmcosta.shopmebackend.AbstractContainerBaseTest;
import com.jmcosta.shopmebackend.roles.entity.Role;
import com.jmcosta.shopmebackend.roles.repository.RoleRepository;
import com.jmcosta.shopmebackend.user.entity.User;
import com.jmcosta.shopmebackend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@DataJpaTest
public class UserRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void givenUserIsCreate_whenUserRetrievedByEmail_EmailsAreEqual() {
        User user = new User(
                "test@gmail.com",
                "testusername",
                "testFirsName",
                "testLastName");

        userRepository.save(user);
        User savedUser = userRepository.getUserByEmail("test@gmail.com");
        assertEquals(savedUser.getEmail(), user.getEmail());
    }

    @Test
    public void givenUserIsCreatedWithOneRole_whenUserRetrievedByEmail_UserHasOneRole() {
        Role admin = roleRepository.findById(1).get();

        User user = new User(
                "testWithOneRole@gmail.com",
                "testusername",
                "testFirsName",
                "testLastName");
        user.addRole(admin);

        userRepository.save(user);

        User savedUser = userRepository.getUserByEmail("testWithOneRole@gmail.com");
        List<Role> roles = savedUser.getRoles().stream().toList();
        assertEquals(roles.get(0).getName(), "Admin");
    }

    @Test
    public void givenUserDoesNotExist_whenGetUserByEmail_thenUserIsNull() {
        User user = userRepository.getUserByEmail("notExists@gmail.com");
        Assertions.assertNull(user);
    }

    @Test
    public void givenOneUser_whenCountById_thenReturnOne() {
        User user = new User(
                "test@email.com",
                "test",
                "test",
                "test"
        );

        User userWithId = userRepository.save(user);
        Long count = userRepository.countById(userWithId.getId());
        assertEquals(1, count);
    }

    @Test
    public void givenUserIsDisabled_whenUpdateEnabled_thenUserIsEnabled(){
        User user = new User(
                "test@email.com",
                "test",
                "test",
                "test"
        );
        user.setEnabled(false);

        userRepository.save(user);
        User persistedUser = userRepository.getUserByEmail("test@email.com");
        userRepository.updateEnableStatus(persistedUser.getId(), true);
        User persistedUser2 = userRepository.getUserByEmail("test@email.com");
        assertTrue(persistedUser.isEnabled(), "User is enabled");
    }
}
