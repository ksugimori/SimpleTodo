package com.example.simple_todo.repository;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@link #sequence} の初期値。
     */
    private static final int SEQUENCE_INIT_VALUE = 1;

    /**
     * タスクID採番用の値。
     * RDBMS におけるシーケンスオブジェクトのようなもの。
     */
    private long sequence;

    /**
     * 内部的に値を保持するMap
     */
    private final Map<TaskId, Task> map;

    public TaskRepositoryImpl() {
        this.sequence = SEQUENCE_INIT_VALUE;
        this.map = new HashMap<>();
    }

    @Override
    public TaskId nextId() {
        return new TaskId(sequence++);
    }

    @Override
    public void save(Task task) {
        logger.info("save task: {}, isCompleted={}", task.id(), task.isCompleted());
        this.map.put(task.id(), task);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(this.map.values());
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return Optional.ofNullable(this.map.get(id));
    }

    @Override
    public boolean deleteById(TaskId id) {
        logger.info("delete task: {}", id);
        return this.map.remove(id) != null;
    }

    @Override
    public void reset() {
        logger.info("reset task repository...");
        this.map.clear();
        this.sequence = SEQUENCE_INIT_VALUE;
    }
}
