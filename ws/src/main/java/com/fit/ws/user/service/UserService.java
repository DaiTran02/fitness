package com.fit.ws.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fit.ws.core.commons.AbstractCrudService;
import com.fit.ws.core.utils.CommonUtils;
import com.fit.ws.user.dto.FilterUserDto;
import com.fit.ws.user.dto.FollowingOtherUser;
import com.fit.ws.user.dto.UpdateUsername;
import com.fit.ws.user.dto.UserUpdateDto;
import com.fit.ws.user.model.UserModel;
import com.fit.ws.user.repository.UserRepository;
import com.fit.ws.user.repository.UserRepositoryCustom;

@Service
public class UserService extends AbstractCrudService<UserModel, ObjectId> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@Override
	protected MongoRepository<UserModel, ObjectId> getRepository() {
		return userRepository;
	}
	
	public UserDetailsService checkUserDetail() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				if(CommonUtils.emailIsValid(username)) {
					Optional<UserModel> userFindByEmail = userRepository.findByEmail(username);
					if(userFindByEmail.isPresent()) {
						UserModel user = userFindByEmail.get();
						user.setUsername(user.getEmail());
						return user;
					}else {
						throw new UsernameNotFoundException("User not found");
					}
				}else {
					return (UserDetails) userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
				}
			}
		};
	}
	
	public Optional<UserModel> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public Optional<UserModel> findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public Optional<UserModel> findByProderId(String providerId){
		return userRepository.findByProviderId(providerId);
	}
	
	public UserModel updateUser(String id,UserUpdateDto update) {
		UserModel userModel = getById(new ObjectId(id));
		userModel.setAvatar(update.getAvatar());
		userModel.setFullName(update.getFullName());
		return update(userModel);
	}
	
	public UserModel updateUsername(String id,UpdateUsername username) {
		UserModel userModel = getById(new ObjectId(id));
		userModel.setUsername(username.getUsername());
		return update(userModel);
	}
	
	public UserModel uploadAvatarUser(String id,UserUpdateDto updateDto) {
		UserModel userModel = getById(new ObjectId(id));
		userModel.setAvatar(updateDto.getAvatar());
		return update(userModel);
	}
	
	public List<UserModel> getListSuggestUsers(FilterUserDto filterUserDto){
		UserModel userModel = getById(new ObjectId(filterUserDto.getIdUser()));
		
		List<UserModel> listSuggest = userRepositoryCustom.listSuggestUsers(filterUserDto);
		
		try {
			userModel.getFollowing().forEach(model->{
				listSuggest.removeIf(item->item.getId().toHexString().equals(model));
			});
		}catch(Exception e) {
			
		}
		
		return listSuggest;
	}
	
	public UserModel followingUser(String id,FollowingOtherUser followingOtherUser) {
		UserModel userModel = getById(new ObjectId(id));
		if(	userModel.getFollowing() == null) {
			List<String> fl = new ArrayList<String>();
			fl.add(followingOtherUser.getUserId());
			userModel.setFollowing(fl);
		}else {
			userModel.getFollowing().add(followingOtherUser.getUserId());
		}
		return update(userModel);
	}
	
	public UserModel updateFollowerUser(String idUserHasFollow,FollowingOtherUser userFollow) {
		UserModel userModel = getById(new ObjectId(idUserHasFollow));
		if(userModel.getFollower() == null) {
			List<String> fl = new ArrayList<String>();
			fl.add(userFollow.getUserId());
			userModel.setFollower(fl);
		}else {
			userModel.getFollower().add(userFollow.getUserId());
		}
		return update(userModel);
	}
	
	public boolean checkUsername(String username) {
		Optional<UserModel> userModel = findByUsername(username);
		return userModel.isPresent();
	}
	


}
