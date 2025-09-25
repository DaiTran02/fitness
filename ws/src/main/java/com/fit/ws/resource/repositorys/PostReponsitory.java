package com.fit.ws.resource.repositorys;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fit.ws.resource.models.PostModel;

public interface PostReponsitory extends MongoRepository<PostModel, ObjectId>{

}
