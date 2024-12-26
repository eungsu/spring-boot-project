package com.example.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {

	@NotEmpty(message = "제목은 필수항목입니다.")
	@Size(max = 200, message = "제목 글자 수는 최대 200글자까지 가능합니다.")
	private String title;
	
	@NotEmpty(message = "내용을 필수항목입니다.")
	private String content;
}
