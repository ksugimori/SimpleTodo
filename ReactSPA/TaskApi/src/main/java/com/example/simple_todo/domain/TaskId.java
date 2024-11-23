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

    /**
     * 文字列からタスクIDを作成する。
     * <p>
     * {@link org.springframework.web.bind.annotation.PathVariable} でのマッピングに利用される。
     *
     * @param stringValue タスクIDの文字列表現
     * @return タスクID
     */
    public static TaskId valueOf(String stringValue) {
        return new TaskId(Long.valueOf(stringValue));
    }
}
