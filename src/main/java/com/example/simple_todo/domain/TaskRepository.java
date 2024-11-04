package com.example.simple_todo.domain;

import java.util.Collection;

public interface TaskRepository {
    TaskId nextId();

    void save(Task task);

    Collection<Task> findAll();

    Task findById(TaskId id);
}
