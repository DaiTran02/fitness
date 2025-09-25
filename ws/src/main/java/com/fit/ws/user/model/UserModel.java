package com.fit.ws.user.model;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fit.ws.user.AuthProvider;
import com.fit.ws.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserModel implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	@Field(value = "_id")
	private ObjectId id;
	
	@Field(value = "username")
	private String username;
	
	@Field(value = "email")
	private String email;
	
	@Field(value = "password")
	private String password;
	
	@Field(value = "full_name")
	private String fullName;
	
	@Field(value = "avatar")
	private String avatar;
	
	@Field(value = "follower")
	private List<String> follower;
	
	@Field(value = "following")
	private List<String> following;
	
	@Field(value = "role")
	private Role role;
	
	@Field(value = "provider")
	private AuthProvider provider;
	
	@Field(value = "providerId")
	private String providerId;
	
	public String getIdAsString() {
		return id.toHexString();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
