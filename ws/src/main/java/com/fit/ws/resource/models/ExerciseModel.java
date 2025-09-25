package com.fit.ws.resource.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "exercise")
public class ExerciseModel {
	@Id
	@Field(value = "_id")
	private ObjectId id;
	
	@Field(value = "name")
	private String name;
	
	@Field(value = "equipment")
	private String equipment;
	
	@Field(value = "muscle")
	private String muscle;
	
	@Field(value = "media")
	private String media;
	
	@Field(value = "image")
	private String image;
	
	@Field(value = "muscle_type")
	private String muscleType;
	

}
