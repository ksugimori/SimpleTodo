package com.example.simple_todo.presentation.api;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository.reset();
    }

    @Test
    public void testReadAll() throws Exception {
        taskRepository.save(new Task(new TaskId(1L), "テスト１", false));
        taskRepository.save(new Task(new TaskId(2L), "テスト２", true));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "description": "テスト１",
                                "isCompleted": false
                            },
                            {
                                "id": 2,
                                "description": "テスト２",
                                "isCompleted": true
                            }
                        ]
                        """));

    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .with(request -> {
                            request.setServerName("example.com");
                            return request;
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": null,
                                    "description": "テストです。",
                                    "isCompleted": false
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://example.com/api/tasks/1"))
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "description": "テストです。",
                            "isCompleted": false
                        }
                        """));
    }

    @Test
    public void testUpdate() throws Exception {
        taskRepository.save(new Task(new TaskId(1L), "テスト１", false));
        taskRepository.save(new Task(new TaskId(2L), "テスト２", true));

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "description": "更新のテスト",
                                    "isCompleted": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "description": "更新のテスト",
                            "isCompleted": true
                        }
                        """));

        assertThat(taskRepository.findAll()).containsExactlyInAnyOrder(
                new Task(new TaskId(1L), "更新のテスト", true),
                new Task(new TaskId(2L), "テスト２", true)
        );
    }

    @Test
    public void testDelete() throws Exception {
        taskRepository.save(new Task(new TaskId(1L), "テスト１", false));
        taskRepository.save(new Task(new TaskId(2L), "テスト２", true));

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        assertThat(taskRepository.findAll()).containsExactlyInAnyOrder(
                new Task(new TaskId(2L), "テスト２", true)
        );
    }
}