package com.todo.todo.controller;

import java.util.List;

import com.todo.todo.dto.TodoCreateDto;
import com.todo.todo.model.Todo;
import com.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getCompanies() {
        return todoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody TodoCreateDto todoCreateDto) {
        return todoService.create(todoCreateDto.getText());
    }
}