package com.todo.todo.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.todo.todo.dto.EditTodoRequest;
import com.todo.todo.mapper.TodoMapper;
import com.todo.todo.model.Todo;
import com.todo.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo findById(int id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Todo create(String text) {
        return todoRepository.save(new Todo(text));
    }

    public Todo edit(int id, EditTodoRequest editTodoRequest) {
        Todo todo = this.findById(id);
        if (todo == null) {
            return null;
        }
        return todoRepository.save(TodoMapper.toEntity(id, editTodoRequest));
    }

    public Todo delete(int id) {
        Todo todo = this.findById(id);
        if (todo == null) {
            return null;
        }
        todoRepository.deleteById(id);
        return todo;
    }
}
