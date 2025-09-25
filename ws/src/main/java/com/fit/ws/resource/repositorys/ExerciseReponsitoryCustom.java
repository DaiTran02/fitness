package com.fit.ws.resource.repositorys;

import java.util.List;

import com.fit.ws.resource.dto.ExerciseFilterDto;
import com.fit.ws.resource.models.ExerciseModel;

public interface ExerciseReponsitoryCustom {
	List<ExerciseModel> getListExercisesByFilter(ExerciseFilterDto filter);
}
