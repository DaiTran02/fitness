package com.fit.ws.resource.repositorys;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fit.ws.resource.models.RoutineModel;

public interface RoutineRepository extends MongoRepository<RoutineModel, ObjectId>{

}
