package com.example.demo.todo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRepository todoRepository;
	
	public CreateTodoResponse addTodo(CreateTodoRequest request) {
		Todo todo = new Todo();
		BeanUtils.copyProperties(request, todo);
		todo.setCompleted("N");
		todo.setCreatedDate(LocalDateTime.now());
		
		todoRepository.save(todo);
		
		CreateTodoResponse response = new CreateTodoResponse();
		BeanUtils.copyProperties(todo, response);
		
		return response;
	}
	
	public List<TodoResponse> getMyTodos(String nickname) {
		List<Todo> todos = todoRepository.findByNickname(nickname);
		List<TodoResponse> responses = todos.stream().map(todo -> {
			TodoResponse response = new TodoResponse();
			BeanUtils.copyProperties(todo, response);
			return response;
		}).toList();
		
		return responses;
	}
}
