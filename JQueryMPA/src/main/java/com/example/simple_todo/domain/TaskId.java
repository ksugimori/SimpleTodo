package com.example.simple_todo.domain;

public record TaskId(Long value) implements Comparable<TaskId> {
    @Override
    public int compareTo(TaskId other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
