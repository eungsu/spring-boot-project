package com.example.demo.todo;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoResponse {
	private Long id;
	private String nickname;
	private String title;
	private String content;
	private LocalDate dueDate;
}
