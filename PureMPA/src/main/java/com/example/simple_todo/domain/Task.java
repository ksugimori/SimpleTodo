package com.example.simple_todo.domain;

public record Task(TaskId id, String description, boolean isCompleted) {

    public Task(TaskId id, String description) {
        this(id, description, false);
    }

    public Task complete() {
        return new Task(
                this.id,
                this.description,
                true
        );
    }
}
