package com.example.simple_todo.presentation.page;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class TopController {
    private final TaskRepository taskRepository;

    public TopController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();

        model.addAttribute("tasks", tasks);

        return "index";
    }
}
