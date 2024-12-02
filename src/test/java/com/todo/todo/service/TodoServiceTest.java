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
    void should_return_todo_when_getById() {
        //given
        Todo todo = new Todo(1, "Todo item 1", false);
        when(mockedTodoRepository.findById(any())).thenReturn(Optional.of(todo));

        //when
        Todo fetchedTodo = todoService.findById(todo.getId());

        //then
        assertEquals(todo.getId(), fetchedTodo.getId());
        assertEquals(todo.getText(), fetchedTodo.getText());
        assertEquals(todo.getDone(), fetchedTodo.getDone());
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
        Todo expectedEditedTodo = new Todo(1, "Edited todo item 1", true);
        when(mockedTodoRepository.findById(any())).thenReturn(Optional.of(todo));
        when(mockedTodoRepository.save(any())).thenReturn(expectedEditedTodo);

        //when
        Todo editedTodo = todoService.edit(todo.getId(), new EditTodoRequest(expectedEditedTodo.getText(), expectedEditedTodo.getDone()));

        //then
        assertEquals(expectedEditedTodo.getId(), editedTodo.getId());
        assertEquals(expectedEditedTodo.getText(), editedTodo.getText());
        assertEquals(expectedEditedTodo.getDone(), editedTodo.getDone());
    }

    @Test
    void should_return_deleted_todo_when_delete() {
        //given
        Todo todo = new Todo(1, "Todo item 1", false);
        when(mockedTodoRepository.findById(any())).thenReturn(Optional.of(todo));

        //when
        Todo deletedTodo = todoService.delete(todo.getId());

        //then
        assertEquals(todo.getId(), deletedTodo.getId());
        assertEquals(todo.getText(), deletedTodo.getText());
        assertEquals(todo.getDone(), deletedTodo.getDone());
    }
}
