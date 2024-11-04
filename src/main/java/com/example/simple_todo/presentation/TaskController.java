package com.example.simple_todo.presentation;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import com.example.simple_todo.presentation.form.TaskCompleteForm;
import com.example.simple_todo.presentation.form.TaskCreateForm;
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
        List<Task> tasks = taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();

        model.addAttribute("tasks", tasks);

        return "tasks";
    }

    @PostMapping
    public String create(@ModelAttribute TaskCreateForm form, Model model) {
        TaskId id = taskRepository.nextId();
        Task task = new Task(id, form.getSubject());

        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute TaskCompleteForm form, Model model) {
        TaskId id = new TaskId(form.getTaskId());

        Task task = taskRepository.findById(id);
        taskRepository.save(task.complete());

        return "redirect:/tasks";
    }

}
