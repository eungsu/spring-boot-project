package com.example.demo.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
	private Long id;
	private String nickname;
	private String title;
	private String content;
	private LocalDate dueDate;
	private String completed;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
