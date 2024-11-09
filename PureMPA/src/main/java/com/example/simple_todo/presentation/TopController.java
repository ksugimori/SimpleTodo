package com.example.simple_todo.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {
    @GetMapping("/")
    public String index() {
        return "redirect:tasks";
    }
}