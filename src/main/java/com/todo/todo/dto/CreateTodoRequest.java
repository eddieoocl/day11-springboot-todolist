package com.todo.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateTodoRequest {
    @NotNull
    private String text;
}
