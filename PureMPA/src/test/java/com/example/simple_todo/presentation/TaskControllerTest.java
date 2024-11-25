package com.example.simple_todo.presentation;

import com.example.simple_todo.domain.Task;
import com.example.simple_todo.domain.TaskId;
import com.example.simple_todo.domain.TaskRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    private final MockMvc mockMvc;

    private final TaskRepository taskRepository;

    @Autowired
    public TaskControllerTest(MockMvc mockMvc, TaskRepository taskRepository) {
        this.mockMvc = mockMvc;
        this.taskRepository = taskRepository;
    }

    @BeforeEach
    public void setUp() {
        this.taskRepository.reset();
    }

    @Test
    public void testIndex() throws Exception {
        Task task1 = new Task(new TaskId(1L), "テスト１", false);
        Task task2 = new Task(new TaskId(2L), "テスト２", true);
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"))
                .andExpect(model().attribute("tasks", containsInAnyOrder(task1, task2)))
                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "牛乳を買う"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));

        assertThat(taskRepository.findAll()).containsExactlyInAnyOrder(
                new Task(new TaskId(1L), "牛乳を買う", false)
        );
    }

    @Test
    public void testComplete() throws Exception {
        Task task1 = new Task(new TaskId(1L), "テスト１", false);
        Task task2 = new Task(new TaskId(2L), "テスト２", true);
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(post("/tasks/complete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));

        assertThat(taskRepository.findAll()).containsExactlyInAnyOrder(
                new Task(new TaskId(1L), "テスト１", true),
                new Task(new TaskId(2L), "テスト２", true)
        );
    }

    @Test
    public void testComplete_データが存在しない場合エラーメッセージがセットされること() throws Exception {
        mockMvc.perform(post("/tasks/complete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("errorMessage", "タスクが存在しません"))
                .andExpect(view().name("redirect:/tasks"));
    }

    @Test
    public void testDelete() throws Exception {
        Task task1 = new Task(new TaskId(1L), "テスト１", false);
        Task task2 = new Task(new TaskId(2L), "テスト２", true);
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(post("/tasks/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("taskId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));

        assertThat(taskRepository.findAll()).containsExactlyInAnyOrder(
                new Task(new TaskId(2L), "テスト２", true)
        );
    }
}