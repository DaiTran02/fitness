package com.fit.ws.resource.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;
import com.fit.ws.resource.dto.PostCreateDto;
import com.fit.ws.resource.dto.filter.PostFilter;
import com.fit.ws.resource.models.PostModel;
import com.fit.ws.resource.services.PostService;
import com.fit.ws.resource.utils.PostUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@Tag(name ="Post", description = "Manage post")
public class PostController {
	private final PostService postService;
	private final PostUtils postUtils;
	
	@PostMapping(path = "post")
	public Object createNewPost(@RequestBody PostCreateDto createPost) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(postUtils.toDocument(postService.createNewPost(createPost)));
		return responseAPI.build();
	}
	
	@GetMapping(path = "posts")
	public Object getListPosts(@RequestParam(name = "limit", required = true) int limit,
					@RequestParam(name = "skip", required = true) int skip) {
		PostFilter postFilter = new PostFilter();
		postFilter.setLimit(limit);
		
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		
		List<PostModel> data = postService.getListPostByFilter(postFilter);
		responseAPI.setTotal(data.size());
		responseAPI.setResutl(postUtils.toListDocument(data));
		return responseAPI.build();
	}
	
	@GetMapping(path = "posts/owner/{id}")
	public Object getListPostsOwner(@PathVariable String id) {
		PostFilter postFilter = new PostFilter();
		postFilter.setIdUser(id);
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(postUtils.toListDocument(postService.getListPostOwner(postFilter)));
		return responseAPI.build();
	}
	
	@DeleteMapping(path = "post/{id}")
	public Object deletePost(@PathVariable String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		postService.delete(new ObjectId(id));
		responseAPI.setResutl("Thành công");
		return responseAPI.build();
	}

}
