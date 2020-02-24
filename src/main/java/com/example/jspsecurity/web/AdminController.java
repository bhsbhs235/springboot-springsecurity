package com.example.jspsecurity.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    
    @GetMapping("/")
    public String admin() {
        return "admin";
    }

}