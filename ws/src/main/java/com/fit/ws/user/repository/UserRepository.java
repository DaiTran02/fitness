package com.fit.ws.user.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fit.ws.user.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, ObjectId>{
	Optional<UserModel> findByUsername(String username);
	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByProviderId(String providerId);
}
