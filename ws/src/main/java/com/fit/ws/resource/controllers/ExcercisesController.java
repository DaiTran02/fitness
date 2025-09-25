package com.fit.ws.resource.controllers;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;
import com.fit.ws.resource.dto.ExerciseCreateDto;
import com.fit.ws.resource.dto.ExerciseFilterDto;
import com.fit.ws.resource.enums.MuscleType;
import com.fit.ws.resource.services.ExerciseService;
import com.fit.ws.resource.utils.ExerciseUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@Tag(name ="Exercises", description = "Manage exercises")
public class ExcercisesController {
	private final ExerciseService exercisesService;
	private final ExerciseUtils exercisesUtils;
	
	
	@PostMapping(path = "exercises")
	public Object createExercises(@RequestBody ExerciseCreateDto createExerciseDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(exercisesService.createExercise(createExerciseDto));
		return responseAPI.build();
	}
	
	@PutMapping(path = "exercises")
	public Object updateExercises(@RequestParam String id, @RequestBody ExerciseCreateDto update) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(exercisesService.updateExercise(id, update));
		return responseAPI.build();
	}
	
	@GetMapping("exercises")
	public Object getAllExercises() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(exercisesUtils.toListDocument(exercisesService.getAll()));
		return responseAPI.build();
	}
	
	@GetMapping("exercises/all")
	public Object getAllExercisesByType(@RequestParam(name = "muscleType",required = false) String muscleType, 
										 @RequestParam(name = "equipment", required = false) String equipment) {
		ResponseAPI responseAPI = new ResponseAPI();
		
		ExerciseFilterDto filter = new ExerciseFilterDto();
		filter.setEquipment(equipment);
		filter.setMuscleType(muscleType);
		
		responseAPI.setOk();
		responseAPI.setResutl(exercisesService.getListExerciseByType(filter));
		return responseAPI.build();
	}
	
	@GetMapping("exercises/type")
	public Object getListTypeMucle() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(exercisesUtils.toListMuscleType(MuscleType.getListTypes()));
		
		return responseAPI.build();
	}
	
	@GetMapping("exercises/info")
	public Object getOneExercise(@RequestParam String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(exercisesUtils.toDocument(exercisesService.getById(new ObjectId(id))));
		
		return responseAPI.build();
	}

}
