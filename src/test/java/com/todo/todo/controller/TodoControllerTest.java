package com.todo.todo.controller;

import com.todo.todo.model.Todo;
import com.todo.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class TodoControllerTest {
    public static final int NUMBER_OF_TODOS = 10;
    @Autowired
    private MockMvc client;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private JacksonTester<List<Todo>> todoListJacksonTester;

    @Autowired
    private JacksonTester<Todo> todoJacksonTester;


    @BeforeEach
    void setup() {
        todoRepository.deleteAll();
        todoRepository.flush();

        for (int i = 0; i < NUMBER_OF_TODOS; i++) {
            todoRepository.save(new Todo(String.format("Todo item %d", i)));
        }
    }

    @Test
    void should_return_all_todos() throws Exception {
        // Given
        final List<Todo> givenTodos = todoRepository.findAll();

        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        final List<Todo> fetchedTodos = todoListJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodos).hasSameSizeAs(givenTodos);
        assertThat(fetchedTodos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_created_todo() throws Exception {
        // Given
        String text = "Todo item 1";
        String requestBody = String.format("{\"text\": \"%s\" }", text);

        // When
        client.perform(MockMvcRequestBuilders.post("/todos"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(text))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));
    }
}
