package com.example.simple_todo.presentation;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import com.example.simple_todo.domain.exception.TaskNotFoundException;
import com.example.simple_todo.presentation.form.TaskCreateForm;
import com.example.simple_todo.presentation.form.TaskIdForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Task task = new Task(id, form.getDescription());

        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute TaskIdForm form, Model model) {
        TaskId id = new TaskId(form.getTaskId());

        Task task = taskRepository.findById(id)
                .map(Task::complete)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.save(task);

        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute TaskIdForm form, Model model) {
        TaskId id = new TaskId(form.getTaskId());

        taskRepository.deleteById(id);

        return "redirect:/tasks";
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "タスクが存在しません");
        return "redirect:/tasks";
    }
}
