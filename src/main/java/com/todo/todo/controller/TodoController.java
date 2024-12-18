package com.todo.todo.controller;

import java.util.List;

import com.todo.todo.dto.CreateTodoRequest;
import com.todo.todo.dto.EditTodoRequest;
import com.todo.todo.model.Todo;
import com.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@AllArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getAll() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable Integer id) {
        Todo todo = todoService.findById(id);
        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return todo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody CreateTodoRequest createTodoRequest) {
        return todoService.create(createTodoRequest.getText());
    }

    @PutMapping("/{id}")
    public Todo edit(@PathVariable Integer id, @Valid @RequestBody EditTodoRequest editTodoRequest) {
        Todo todo = todoService.edit(id, editTodoRequest);
        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return todo;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Todo delete(@PathVariable Integer id) {
        Todo todo = todoService.delete(id);
        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return todo;
    }

}