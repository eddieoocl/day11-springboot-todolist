package com.todo.todo.service;

import com.todo.todo.dto.EditTodoRequest;
import com.todo.todo.mapper.TodoMapper;
import com.todo.todo.model.Todo;
import com.todo.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository mockedTodoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void should_return_all_todos_when_getAll() {
        //given
        when(mockedTodoRepository.findAll()).thenReturn(List.of(new Todo("Todo item 1")));

        //when
        List<Todo> todos = todoService.findAll();

        //then
        assertEquals(1, todos.size());
        assertEquals("Todo item 1", todos.get(0).getText());
    }

    @Test
    void should_return_created_todo_when_create() {
        //given
        Todo todo = new Todo(1, "Todo item 1", false);
        when(mockedTodoRepository.save(any())).thenReturn(todo);

        //when
        Todo createdTodo = todoService.create("Todo item 1");

        //then
        assertEquals(todo.getText(), createdTodo.getText());
        assertEquals(todo.getDone(), createdTodo.getDone());
    }

    @Test
    void should_return_edited_todo_when_edit() {
        //given
        Todo todo = new Todo(1, "Todo item 1", false);
        when(mockedTodoRepository.findById(any())).thenReturn(Optional.of(todo));

        //when
        String text = "Edited todo item 1";
        Boolean done = true;

        Todo editedTodo = todoService.edit(todo.getId(), new EditTodoRequest(text, done));

        //then
        assertEquals(text, editedTodo.getText());
        assertEquals(done, editedTodo.getDone());
    }
}
