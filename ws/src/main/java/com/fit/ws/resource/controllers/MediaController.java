package com.fit.ws.resource.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import org.bson.types.ObjectId;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;
import com.fit.ws.resource.dto.MediaInputDto;
import com.fit.ws.resource.models.MediaModel;
import com.fit.ws.resource.services.FileLocalService;
import com.fit.ws.resource.services.MediaService;
import com.fit.ws.resource.utils.MediaUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Media manager", description = "Media manager")
@RequestMapping("/api/v1/media")
@RestController
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class MediaController {
	
	private final MediaService mediaService;
	private final MediaUtils mediaUtils;
	private final FileLocalService fileLocalService;
	
	@PostMapping(path = "/file")
	public Object uploadFile(@ModelAttribute() @Validated MediaInputDto inputDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		try {
			responseAPI.setResutl(mediaUtils.toDocumentFile(mediaService.createMedia(inputDto)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseAPI.build();
	}
	
	@PostMapping(path = "/file/video")
	public Object uploadVideo(@ModelAttribute() @Validated MediaInputDto inputVideo) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		try {
			responseAPI.setResutl(mediaUtils.toDocumentFile(mediaService.storeVideo(inputVideo)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseAPI.build();	
	}
	
	@GetMapping("/file/{id}/stream")
	public ResponseEntity<ResourceRegion> streamVideo(@PathVariable String id, @RequestHeader HttpHeaders headers) throws IOException {
	    MediaModel mediaModel = mediaService.getById(new ObjectId(id));
	    UrlResource video = new UrlResource(Paths.get(fileLocalService.getPathAttachments()).resolve(mediaModel.getFilePath()).toUri());

	    long contentLength = video.contentLength();
	    ResourceRegion region = resourceRegion(video, headers, contentLength);

		 return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				 .contentType(MediaTypeFactory.getMediaType(video)
						 .orElse(MediaType.APPLICATION_OCTET_STREAM)).body(region);
	}

	private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers, long contentLength) {
	    long rangeStart = 0;
	    long rangeEnd = contentLength - 1;
	    if (headers.getRange() != null && !headers.getRange().isEmpty()) {
	        HttpRange httpRange = headers.getRange().get(0);
	        rangeStart = httpRange.getRangeStart(contentLength);
	        rangeEnd = httpRange.getRangeEnd(contentLength);
	    }
	    long rangeLength = Math.min(1 * 1024 * 1024, rangeEnd - rangeStart + 1); // 1MB chunk
	    return new ResourceRegion(video, rangeStart, rangeLength);
	}

	
	@GetMapping(path = "/file")
	public Object getFile(@RequestParam(name = "id") String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(mediaUtils.toDocumentFile(mediaService.getById(new ObjectId(id))));
		
		return responseAPI.build();
	}
	
	@GetMapping(path = "/file/content")
	public Object getContentFile(@RequestParam(name = "id")String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(mediaUtils.toDocumentFileContent(mediaService.getById(new ObjectId(id))));
		return responseAPI.build();
	}

}
