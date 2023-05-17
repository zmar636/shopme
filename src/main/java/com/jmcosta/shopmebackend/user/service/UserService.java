package com.jmcosta.shopmebackend.user.service;

import com.jmcosta.shopmebackend.roles.entity.Role;
import com.jmcosta.shopmebackend.roles.repository.RoleRepository;
import com.jmcosta.shopmebackend.user.entity.User;
import com.jmcosta.shopmebackend.user.exception.UserNotFoundException;
import com.jmcosta.shopmebackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user) {
        boolean isUpdate = user.getId() != null;

        if (isUpdate) {
            User currentUser = userRepository
                    .findById(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("User with id \"" + user.getId() + "\" was not found"));

            if (user.getPassword().isEmpty()) {
                user.setPassword(currentUser.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    public void delete(Integer id) {

        Long count = userRepository.countById(id);

        if(count == 0) {
            throw new UserNotFoundException("User with id: \"" + id + "\" was not found");
        }

        userRepository.deleteById(id);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = id == null;

        if (isCreatingNew) {
            return false;
        } else {
            return Objects.equals(userByEmail.getId(), id);
        }
    }

    public User getById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: \"" + id + "\" was not found")
                );
    }
}
