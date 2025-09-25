package com.fit.ws.resource.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "media")
public class MediaModel {
	@Id
	@Field(value = "_id")
	private ObjectId id;
	
	@Field(value = "createTime")
	private Date createTime;
	
	@Field(value = "fileDesription")
	private String fileDescription;
	
	@Field(value = "fileType")
	private String fileType;
	
	@Field(value = "filePath")
	private String filePath;

}
