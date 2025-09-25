package com.fit.ws.resource.utils;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fit.ws.resource.models.RoutineModel;

@Component
public class RoutineUtils {
	public Document toDocument(RoutineModel routineModel) {
		Document document = new Document();
		
		document.append("id", routineModel.getId().toHexString());
		document.append("name", routineModel.getName());
		document.append("createTime", routineModel.getCreateTime() == null ? 0 : routineModel.getCreateTime().getTime());
		document.append("updateTime", routineModel.getUpdateTime() == null ? 0 : routineModel.getUpdateTime().getTime());
		document.append("listExercises", routineModel.getListExercises());
		document.append("creator", routineModel.getCreator());
		return document;
	}
	
	public List<Document> toListDocuments(List<RoutineModel> listRoutines){
		return listRoutines.stream().map(item->toDocument(item)).toList();		
	}
}
