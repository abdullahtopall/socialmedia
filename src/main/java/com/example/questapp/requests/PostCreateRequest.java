package com.example.questapp.requests;

import lombok.Data;

@Data
public class PostCreateRequest {
	Long id;
	String text;
	Long userId;
	String title;
}
