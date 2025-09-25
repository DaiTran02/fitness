package com.fit.ws.resource.utils;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fit.ws.resource.models.MediaModel;
import com.fit.ws.resource.services.FileLocalService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MediaUtils {
	
	private final FileLocalService fileLocalService;
	
	public Document toDocumentFile(MediaModel mediaModel) {
		Document document = new Document();
		document.append("id", mediaModel.getId().toHexString());
		document.append("type", mediaModel.getFileType());
		document.append("description", mediaModel.getFileDescription());
		document.append("path", mediaModel.getFilePath());
		document.append("createTime", mediaModel.getCreateTime().getTime());
		
		return document;
	}
	
	public Document toDocumentFileContent(MediaModel mediaModel) {
		Document document = new Document();
		document.append("id", mediaModel.getId().toHexString());
		document.append("type", mediaModel.getFileType());
		document.append("description", mediaModel.getFileDescription());
		document.append("createTime", mediaModel.getCreateTime().getTime());
		document.append("content", fileLocalService.getFile(mediaModel.getFilePath()));
		
		return document;
	}
}
