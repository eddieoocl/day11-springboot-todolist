package com.todo.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditTodoRequest {
    @NotNull
    private String text;

    @NotNull
    private Boolean done;
}
