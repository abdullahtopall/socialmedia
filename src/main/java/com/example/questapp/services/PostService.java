package com.example.questapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.questapp.entities.Post;
import com.example.questapp.entities.User;
import com.example.questapp.repos.PostRepository;
import com.example.questapp.requests.PostCreateRequest;
import com.example.questapp.requests.PostUpdateRequest;

@Service
public class PostService {
	private PostRepository postRepository;
	private UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService=userService;
	}

	public List<Post> getAllPosts(Optional<Long> userId) {
		if(userId.isPresent()) {
			return this.postRepository.findAllByUserId(userId.get());
		}
		return this.postRepository.findAll();
	}

	public Post getOnePost(Long postId) {
		return this.postRepository.findById(postId).orElse(null);
	}

	public Post createOnePost(PostCreateRequest newRequest) {
		User user = this.userService.getOneUser(newRequest.getUserId());
		if(user == null) {
			return null;
		} else {
			Post toSave = new Post();
			toSave.setId(newRequest.getId());
			toSave.setText(newRequest.getText());
			toSave.setTitle(newRequest.getTitle());
			toSave.setUser(user);
			return this.postRepository.save(toSave);
		}
	}

	public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = this.postRepository.findById(postId);
		if(post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(postUpdateRequest.getText());
			toUpdate.setTitle(postUpdateRequest.getTitle());
			this.postRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}

	public void deleteOnePostById(Long postId) {
		this.postRepository.deleteById(postId);
	}
}
