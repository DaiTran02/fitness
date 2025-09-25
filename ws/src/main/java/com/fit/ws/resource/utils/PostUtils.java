package com.fit.ws.resource.utils;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fit.ws.resource.models.PostModel;

@Component
public class PostUtils {
	
	public Document toDocument(PostModel postModel) {
		Document document = new Document();
		document.append("id", postModel.getId().toHexString());
		document.append("caption", postModel.getCaption());
		document.append("userId", postModel.getUserId());
		document.append("username", postModel.getUsername());
		document.append("media", postModel.getMedia());
		document.append("createTime", postModel.getCreateTime().getTime());
		return document;
	}
	
	public List<Document> toListDocument(List<PostModel> listPost){
		return listPost.stream().map(model->toDocument(model)).toList();
	}
	
}
