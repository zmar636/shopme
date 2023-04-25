package com.jmcosta.shopmebackend.user.controller;

import com.jmcosta.shopmebackend.user.entity.User;
import com.jmcosta.shopmebackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("listUsers",userList);
        return "users";
    }
}
