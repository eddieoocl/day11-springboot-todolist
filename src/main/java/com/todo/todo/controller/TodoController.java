package com.todo.todo.controller;

import java.util.List;

import com.todo.todo.model.Todo;
import com.todo.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getCompanies() {
        return todoService.findAll();
    }
}