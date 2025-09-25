package com.fit.ws.user;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fit.ws.user.model.UserModel;

@Component
public class UserUtils {
	public Document toDocument(UserModel userModel) {
		Document document = new Document();
		
		document.append("id", userModel.getId().toHexString());
		document.append("username", userModel.getUsername());
		document.append("fullname", userModel.getFullName());
		document.append("avatar", userModel.getAvatar());
		document.append("email", userModel.getEmail());
		document.append("follower", userModel.getFollower());
		document.append("following", userModel.getFollowing());
		
		return document;
	}
	
	public List<Document> toListDocument(List<UserModel> userModels){
		return userModels.stream().map(model->toDocument(model)).toList();
	}
	
}
