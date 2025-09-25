package com.fit.ws.resource.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExerciseExpandDto {
	
	public ExerciseExpandDto() {
	}
	
	private String idExercise;
	private String nameExercise;
	private String avatarExercise;
	private String description;
	private long restTime;
	private List<ExerciseSetDto> sets;
}
