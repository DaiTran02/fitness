package com.fit.ws.user.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.fit.ws.user.dto.FilterUserDto;
import com.fit.ws.user.model.UserModel;
import com.fit.ws.user.repository.UserRepositoryCustom;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public List<UserModel> listSuggestUsers(FilterUserDto filterUserDto) {
		List<UserModel> listUser = mongoTemplate.findAll(UserModel.class);
		listUser.removeIf(user->user.getId().toHexString().equals(filterUserDto.getIdUser()));
		
		return listUser;
	}

}
