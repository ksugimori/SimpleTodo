package com.example.simple_todo.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public record TaskId(@JsonValue Long value) implements Comparable<TaskId> {
    @Override
    public int compareTo(TaskId other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public static TaskId valueOf(String stringValue) {
        return new TaskId(Long.valueOf(stringValue));
    }
}
