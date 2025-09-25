package com.fit.ws.user.repository;

import java.util.List;

import com.fit.ws.user.dto.FilterUserDto;
import com.fit.ws.user.model.UserModel;

public interface UserRepositoryCustom {
	List<UserModel> listSuggestUsers(FilterUserDto filterUserDto);

}
