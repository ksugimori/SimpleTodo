package com.example.simple_todo.presentation.api;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> readAll() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task request) {
        Task task = new Task(
                taskRepository.nextId(),
                request.subject(),
                request.isCompleted()
        );

        taskRepository.save(task);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.id())
                .toUri();

        return ResponseEntity.created(location).body(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable TaskId id, @RequestBody Task task) {
        if (!task.id().equals(id)) {
            throw new RuntimeException("");
        }

        taskRepository.save(task);

        return task;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable TaskId id) {
        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
