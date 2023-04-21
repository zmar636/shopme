package com.jmcosta.shopmebackend.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("")
    public String controlPanel() {
        return "index";
    }
}
