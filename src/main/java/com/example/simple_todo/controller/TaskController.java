package com.example.simple_todo.controller;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String index(Model model) {
        refreshModels(model);
        return "tasks";
    }

    @PostMapping
    public String create(@ModelAttribute TaskCreateForm form, Model model) {
        TaskId id = taskRepository.nextId();
        Task task = new Task(id, form.getSubject());

        taskRepository.save(task);

        refreshModels(model);
        return "tasks";
    }

    private void refreshModels(Model model) {
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();

        model.addAttribute("createForm", new TaskCreateForm());
        model.addAttribute("tasks", tasks);
    }
}
