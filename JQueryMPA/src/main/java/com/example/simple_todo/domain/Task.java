package com.example.simple_todo.domain;

public record Task(TaskId id, String description, boolean isCompleted) {
}
