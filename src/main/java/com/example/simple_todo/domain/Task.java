package com.example.simple_todo.domain;

public record Task(TaskId id, String subject, boolean isCompleted) {

    public Task(TaskId id, String subject) {
        this(id, subject, false);
    }
}
