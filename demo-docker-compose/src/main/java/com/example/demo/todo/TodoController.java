package com.example.demo.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;
	
	@GetMapping("/todos/{nickname}")
	public ResponseEntity<List<TodoResponse>> todos(@PathVariable("nickname") String nickname) {
		List<TodoResponse> responses = todoService.getMyTodos(nickname);
		
		return ResponseEntity.ok().body(responses);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<CreateTodoResponse> post(@RequestBody CreateTodoRequest request) {
		CreateTodoResponse response = todoService.addTodo(request);
		
		return ResponseEntity.ok().body(response);
	}
}
