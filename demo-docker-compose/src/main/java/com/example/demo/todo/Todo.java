package com.example.demo.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickname;
	private String title;
	private String content;
	private LocalDate dueDate;
	private String completed;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
