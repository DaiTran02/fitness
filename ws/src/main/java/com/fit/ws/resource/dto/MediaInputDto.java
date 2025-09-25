package com.fit.ws.resource.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MediaInputDto {
	private MultipartFile file;
	private String description;
}
