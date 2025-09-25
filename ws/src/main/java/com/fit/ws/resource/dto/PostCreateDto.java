package com.fit.ws.resource.dto;

import java.util.List;

import com.fit.ws.resource.models.expands.PostMedia;

import lombok.Data;

@Data
public class PostCreateDto {
	private String caption;
	private String username;
	private String userId;
	private List<PostMedia> media;
}
