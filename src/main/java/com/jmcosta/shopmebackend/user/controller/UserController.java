package com.jmcosta.shopmebackend.user.controller;

import com.jmcosta.shopmebackend.roles.entity.Role;
import com.jmcosta.shopmebackend.user.entity.User;
import com.jmcosta.shopmebackend.user.exception.UserNotFoundException;
import com.jmcosta.shopmebackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("listUsers", userList);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> roles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("pageTitle", "Create new user");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping ("/users/edit/{id}")
    public String edit(
            @PathVariable(name = "id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(id);
            List<Role> roleList = userService.listRoles();
            model.addAttribute("user", user);
            model.addAttribute("listRoles", roleList);
            model.addAttribute("pageTitle", "Edit User");
            return "user_form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }
}
