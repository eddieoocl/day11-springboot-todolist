package com.todo.todo.mapper;

import com.todo.todo.dto.EditTodoRequest;
import com.todo.todo.model.Todo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public static Todo toEntity(int id, EditTodoRequest request) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setText(request.getText());
        todo.setDone(request.getDone());
        return todo;
    }
}