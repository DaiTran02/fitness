package com.fit.ws.user.controller;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;
import com.fit.ws.user.UserUtils;
import com.fit.ws.user.dto.FilterUserDto;
import com.fit.ws.user.dto.FollowingOtherUser;
import com.fit.ws.user.dto.UpdateUsername;
import com.fit.ws.user.dto.UserUpdateDto;
import com.fit.ws.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@Tag(name ="User", description = "Manage user")
public class UserController {
	
	private final UserService userService;
	private final UserUtils userUtils;
	
	
	@GetMapping("users")
	public Object getAllUsers() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toListDocument(userService.getAll()));
		return responseAPI.build();
	}
	
	@GetMapping("user/{id}")
	public Object getInfoUser(@PathVariable String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.getById(new ObjectId(id))));
		return responseAPI.build();
	}
	
	@GetMapping("user/check/{username}")
	public Object checkUsername(@PathVariable String username) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userService.checkUsername(username));
		return responseAPI.build();
	}
		
	@PutMapping("user/{id}")
	public Object updateUser(@PathVariable String id, @RequestBody UserUpdateDto updateDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.updateUser(id, updateDto)));
		return responseAPI.build();
	}
	
	@PutMapping("user/first/{id}")
	public Object updateUsername(@PathVariable String id, @RequestBody UpdateUsername updateUsername) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.updateUsername(id, updateUsername)));
		return responseAPI.build();
	}
	
	@PutMapping("user/avatar/{id}")
	public Object uploadAvatar(@PathVariable String id,@RequestBody UserUpdateDto updateDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.uploadAvatarUser(id, updateDto)));
		return responseAPI.build();
	}
	
	@PutMapping("user/following/{id}")
	public Object followingOther(@PathVariable String id,@RequestBody FollowingOtherUser followingOtherUser) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.followingUser(id, followingOtherUser)));
		return responseAPI.build();
	}
	
	@PutMapping("user/follower/{id}")
	public Object updateFollower(@PathVariable String id,@RequestBody FollowingOtherUser userFollow) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toDocument(userService.updateFollowerUser(id, userFollow)));
		return responseAPI.build();
	}
	
	@GetMapping("user/suggest/{id}")
	public Object getGussestUsers(@PathVariable String id) {
		FilterUserDto filterUserDto = new FilterUserDto();
		filterUserDto.setIdUser(id);
		
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(userUtils.toListDocument(userService.getListSuggestUsers(filterUserDto)));
		return responseAPI.build();
	}

}
