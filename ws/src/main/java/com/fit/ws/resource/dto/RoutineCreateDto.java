package com.fit.ws.resource.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoutineCreateDto {
	private String name;
	private List<ExerciseExpandDto> listExercises;
	private CreatorDto creator;
}
