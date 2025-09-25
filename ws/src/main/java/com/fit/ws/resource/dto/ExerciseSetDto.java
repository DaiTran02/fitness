package com.fit.ws.resource.dto;

import lombok.Data;

@Data
public class ExerciseSetDto {
	
	public ExerciseSetDto() {
		
	}
	
	private int kilograms;
	private int reps;
	private long times;
	private int km;
}
