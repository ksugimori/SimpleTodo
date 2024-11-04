package com.example.simple_todo.repository;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private long sequence;
    private final Map<TaskId, Task> map;

    public TaskRepositoryImpl() {
        this.sequence = 1;
        this.map = new HashMap<>();
    }

    @Override
    public TaskId nextId() {
        return new TaskId(sequence++);
    }

    @Override
    public void save(Task task) {
        this.map.put(task.id(), task);
    }

    @Override
    public Collection<Task> findAll() {
        return this.map.values();
    }

    @Override
    public Task findById(TaskId id) {
        return this.map.get(id);
    }

    @Override
    public boolean deleteById(TaskId id) {
        return this.map.remove(id) != null;
    }
}
