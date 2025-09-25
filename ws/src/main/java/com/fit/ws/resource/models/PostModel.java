package com.fit.ws.resource.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fit.ws.resource.models.expands.PostMedia;

import lombok.Data;

@Data
@Document(collection = "post")
public class PostModel {
	@Id
	@Field(value = "_id")
	private ObjectId id;
	
	@Field(value = "caption")
	private String caption;
	
	@Field(value = "userId")
	private String userId;
	
	@Field(value = "username")
	private String username;
	
	@Field(value = "media")
	private List<PostMedia> media;
	
	@Field(value = "createTime")
	private Date createTime;
}
