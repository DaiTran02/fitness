package com.fit.ws.resource.dto;

import lombok.Data;

@Data
public class ExerciseCreateDto {
	private String name;
	private String equipment;
	private String muscle;
	private String media;
	private String desription;
	private String image;
	private String muscleType;
}
