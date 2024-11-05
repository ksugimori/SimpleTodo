package com.example.simple_todo.presentation.page;

import com.example.simple_todo.domain.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {
    private final TaskRepository taskRepository;

    public TopController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
