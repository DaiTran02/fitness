package com.fit.ws.resource.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExerciseListDataDto {
	private String key;
	private String value;
	private List<ExerciseDataDto> listExcercises;
}
