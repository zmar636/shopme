package com.jmcosta.shopmebackend.roles.repository;

import com.jmcosta.shopmebackend.AbstractContainerBaseTest;
import com.jmcosta.shopmebackend.roles.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // deactivate the default behaviour
@DataJpaTest
class RoleRepositoryTest extends AbstractContainerBaseTest {
    @Autowired
    RoleRepository repository;

    @Test
    public void testCreateFirstRole() {
        Role admin = new Role("Admin", "Manage everything");
        Role role = repository.save(admin);

        assertTrue(repository.findById(role.getId()).isPresent());
    }

    @Test
    public void createRemainingRoles() {
        Role sales = new Role("Sales", "Manage product price, customers, shipping, orders and sales report");
        Role editor = new Role("Editor", "Manage categories, brands, products, articles, and menus");
        Role shipper = new Role("Shipper", "View products, view orders and update order status");
        Role assistant = new Role("Assistant", "Manage questions and reviews");
        repository.saveAll(List.of(sales, editor, shipper, assistant));
    }
}