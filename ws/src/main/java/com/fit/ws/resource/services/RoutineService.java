package com.fit.ws.resource.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.fit.ws.core.commons.AbstractCrudService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.resource.dto.RoutineCreateDto;
import com.fit.ws.resource.models.RoutineModel;
import com.fit.ws.resource.models.expands.Creator;
import com.fit.ws.resource.models.expands.ExerciseExpand;
import com.fit.ws.resource.models.expands.ExerciseSet;
import com.fit.ws.resource.repositorys.RoutineRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoutineService extends AbstractCrudService<RoutineModel, ObjectId>{
	
	private final RoutineRepository routineRepository;
	private final ModelMapper modelMapper;

	@Override
	protected MongoRepository<RoutineModel, ObjectId> getRepository() {
		return routineRepository;
	}
	
	public RoutineModel createNewRoutine(RoutineCreateDto routineCreate) {
		RoutineModel newRoutine = new RoutineModel();
		newRoutine.setCreateTime(new Date());
		newRoutine.setCreator(modelMapper.map(routineCreate.getCreator(), Creator.class));
		newRoutine.setName(routineCreate.getName());
		List<ExerciseExpand> listExerciseExpands = new ArrayList<ExerciseExpand>();
		  if (routineCreate.getListExercises() != null) {
		        routineCreate.getListExercises().forEach(item -> {
		           ExerciseExpand exerciseExpand = new ExerciseExpand();
		            exerciseExpand.setIdExercise(item.getIdExercise());
		            exerciseExpand.setNameExercise(item.getNameExercise());
		            exerciseExpand.setAvatarExercise(item.getAvatarExercise());
		            exerciseExpand.setDescription(item.getDescription());
		            exerciseExpand.setRestTime(item.getRestTime());
		            
		            
		            List<ExerciseSet> listSets = new ArrayList<>();
		            if (item.getSets() != null) {
		                item.getSets().forEach(it -> {
		                    ExerciseSet oneSet = new ExerciseSet();
		                    oneSet.setKilograms(it.getKilograms());
		                    oneSet.setReps(it.getReps());
		                    oneSet.setTimes(it.getTimes());
		                    oneSet.setKm(it.getKm());
		                    listSets.add(oneSet);
		                });
		            }
		            exerciseExpand.setSets(listSets);
		            listExerciseExpands.add(exerciseExpand);
		        });
		    }
		    newRoutine.setListExercises(listExerciseExpands);
		return create(newRoutine);
	}
	
	public RoutineModel updateRoutine(String id,RoutineCreateDto routine) {
		RoutineModel updateRoutine = getById(new ObjectId(id));
		if(updateRoutine == null) throw new AppException(ErrorCode.DATA_NOT_FOUND);
		
		updateRoutine.setName(routine.getName());
		updateRoutine.setUpdateTime(new Date());
		List<ExerciseExpand> listExerciseExpands = new ArrayList<ExerciseExpand>();
		  if (routine.getListExercises() != null) {
			  routine.getListExercises().forEach(item -> {
		           ExerciseExpand exerciseExpand = new ExerciseExpand();
		            exerciseExpand.setIdExercise(item.getIdExercise());
		            exerciseExpand.setNameExercise(item.getNameExercise());
		            exerciseExpand.setAvatarExercise(item.getAvatarExercise());
		            exerciseExpand.setDescription(item.getDescription());
		            exerciseExpand.setRestTime(item.getRestTime());
		            exerciseExpand.setDescription(item.getDescription());
		            
		            List<ExerciseSet> listSets = new ArrayList<>();
		            if (item.getSets() != null) {
		                item.getSets().forEach(it -> {
		                    ExerciseSet oneSet = new ExerciseSet();
		                    oneSet.setKilograms(it.getKilograms());
		                    oneSet.setReps(it.getReps());
		                    oneSet.setTimes(it.getTimes());
		                    oneSet.setKm(it.getKm());
		                    listSets.add(oneSet);
		                });
		            }
		            exerciseExpand.setSets(listSets);
		            listExerciseExpands.add(exerciseExpand);
		        });
		    }
		  updateRoutine.setListExercises(listExerciseExpands);
		  return update(updateRoutine);
	}
	
	

}
