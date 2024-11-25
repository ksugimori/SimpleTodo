package com.example.simple_todo.domain.exception;

import com.example.simple_todo.domain.TaskId;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(TaskId id) {
        super("task not found. ID: " + id);
    }
}
