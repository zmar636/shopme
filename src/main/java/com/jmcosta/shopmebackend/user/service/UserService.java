package com.jmcosta.shopmebackend.user.service;

import com.jmcosta.shopmebackend.user.entity.User;
import com.jmcosta.shopmebackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
