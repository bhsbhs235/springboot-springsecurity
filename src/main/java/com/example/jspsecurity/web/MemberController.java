package com.example.jspsecurity.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
    
    @GetMapping("/")
    public String member() {
        return "myinfo";
    }

}