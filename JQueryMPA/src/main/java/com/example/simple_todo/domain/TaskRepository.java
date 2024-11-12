package com.example.simple_todo.domain;

import java.util.List;

public interface TaskRepository {
    TaskId nextId();

    void save(Task task);

    List<Task> findAll();

    Task findById(TaskId id);

    boolean deleteById(TaskId id);
}
