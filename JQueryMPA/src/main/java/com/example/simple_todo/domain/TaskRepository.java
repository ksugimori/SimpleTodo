package com.example.simple_todo.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    /**
     * {@link TaskId} を採番する。
     *
     * @return 他と重複しないタスクID
     */
    TaskId nextId();

    /**
     * タスクを保存する。
     *
     * @param task タスク
     */
    void save(Task task);

    /**
     * すべてのタスクを取得する。
     *
     * @return タスクの一覧。ソート順は不定。
     */
    List<Task> findAll();

    /**
     * タスクを取得する。
     *
     * @param id タスクID
     * @return タスク
     */
    Optional<Task> findById(TaskId id);

    /**
     * タスクを削除する。
     *
     * @param id タスクID
     * @return 削除に成功した場合は true
     */
    boolean deleteById(TaskId id);

    /**
     * すべてのタスクを削除し、シーケンスを初期値に戻す。
     */
    void reset();
}
