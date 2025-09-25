package com.fit.ws.resource.models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fit.ws.resource.models.expands.Creator;
import com.fit.ws.resource.models.expands.ExerciseExpand;

import lombok.Data;

@Data
@Document(collection = "routine")
public class RoutineModel {
	@Id
	@Field(value = "_id")
	private ObjectId id;
	
	@Field(value = "name")
	private String name;
	
	@Field(value = "createTime")
	private Date createTime ;
	
	@Field(value = "updateTime")
	private Date updateTime;
	
	@Field(value = "listExercises")
	private List<ExerciseExpand> listExercises;
	
	@Field(value = "creator")
	private Creator creator;
}
