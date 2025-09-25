package com.fit.ws.resource.repositorys;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fit.ws.resource.models.MediaModel;

@Repository
public interface MediaReponsitory extends MongoRepository<MediaModel, ObjectId>{

}
