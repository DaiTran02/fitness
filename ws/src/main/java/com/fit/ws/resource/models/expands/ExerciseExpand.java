package com.fit.ws.resource.models.expands;

import java.util.List;

import lombok.Data;

@Data
public class ExerciseExpand {
	private String idExercise;
	private String nameExercise;
	private String avatarExercise;
	private String description;
	private long restTime;
	private List<ExerciseSet> sets;
}
