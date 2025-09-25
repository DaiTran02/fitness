package com.fit.ws.resource.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.fit.ws.resource.enums.MuscleType;
import com.fit.ws.resource.models.ExerciseModel;

@Component
public class ExerciseUtils {
	
	public Document toDocument(ExerciseModel exercisesModel) {
		Document document = new Document();
		document.append("id", exercisesModel.getId().toHexString());
		document.append("name", exercisesModel.getName());
		document.append("equipment", exercisesModel.getEquipment());
		document.append("muscle", exercisesModel.getMuscle());
		document.append("media", exercisesModel.getMedia());
		document.append("image", exercisesModel.getImage());
		document.append("muscleType", exercisesModel.getMuscleType());
		return document;
	}
	
	public List<Document> toListDocument(List<ExerciseModel> listExcercisesModels){
		List<Document> list = new ArrayList<Document>();
		
		listExcercisesModels.stream().forEach(model->{
			Document document = new Document();
			document.append("id", model.getId().toHexString());
			document.append("name", model.getName());
			document.append("equipment", model.getEquipment());
			document.append("muscle", model.getMuscle());
			document.append("media", model.getMedia());
			document.append("image", model.getImage());
			document.append("muscleType", model.getMuscleType());
			list.add(document);
		});
		
		return list;
	}
	
	public List<Document> toListMuscleType(List<Pair<String, String>> listItems){
		List<Document> list = new ArrayList<Document>();
		
		MuscleType.getListTypes().forEach(model->{
			Document document = new Document();
			document.append("key", model.getKey());
			document.append("value", model.getValue());
			list.add(document);
		});
		
		return list;
	}
	

}
