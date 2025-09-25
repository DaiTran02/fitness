package com.fit.ws.resource.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.fit.ws.core.commons.AbstractCrudService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.resource.dto.ExerciseCreateDto;
import com.fit.ws.resource.dto.ExerciseDataDto;
import com.fit.ws.resource.dto.ExerciseFilterDto;
import com.fit.ws.resource.dto.ExerciseListDataDto;
import com.fit.ws.resource.enums.MuscleType;
import com.fit.ws.resource.models.ExerciseModel;
import com.fit.ws.resource.repositorys.ExerciseReponsitoryCustom;
import com.fit.ws.resource.repositorys.ExerciseReponsitory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseService extends AbstractCrudService<ExerciseModel, ObjectId>{
	
	private final ExerciseReponsitory exercisesReponsitory;
	private final ModelMapper modelMapper;
	private final ExerciseReponsitoryCustom excercisesReponsitoryCustom;

	@Override
	protected MongoRepository<ExerciseModel, ObjectId> getRepository() {
		return exercisesReponsitory;
	}
	
	public ExerciseModel createExercise(ExerciseCreateDto createExcerciseModel) {
		ExerciseModel exercisesModel = modelMapper.map(createExcerciseModel, ExerciseModel.class);
		return create(exercisesModel);
	}
	
	public ExerciseModel updateExercise(String id,ExerciseCreateDto updateExerciseDto) {
		ExerciseModel update = getById(new ObjectId(id));
		if(update == null) {
			throw new AppException(ErrorCode.DATA_NOT_FOUND);
		}
		
		update.setName(updateExerciseDto.getName());
		update.setEquipment(updateExerciseDto.getEquipment());
		update.setImage(updateExerciseDto.getImage());
		update.setMedia(updateExerciseDto.getMedia());
		update.setMuscle(updateExerciseDto.getMuscle());
		update.setMuscleType(updateExerciseDto.getMuscleType());
		return update(update);
	}
	
	public List<ExerciseListDataDto> getListExerciseByType(ExerciseFilterDto filters){
		List<ExerciseListDataDto> listData = new ArrayList<ExerciseListDataDto>();
		
		if(filters.getMuscleType() == null) {
			MuscleType.getListTypes().forEach(model->{
				ExerciseFilterDto filter = new ExerciseFilterDto();
				filter.setMuscleType(model.getKey());
				List<ExerciseModel> dataFind = excercisesReponsitoryCustom.getListExercisesByFilter(filter);
				
				List<ExerciseDataDto> data = new ArrayList<ExerciseDataDto>();
				dataFind.forEach(excercises->{
					ExerciseDataDto excercisesDataDto = modelMapper.map(excercises, ExerciseDataDto.class);
					data.add(excercisesDataDto);
				});
				
				ExerciseListDataDto exercisesListDataDto = new ExerciseListDataDto();
				exercisesListDataDto.setKey(model.getKey());
				exercisesListDataDto.setValue(model.getValue());
				exercisesListDataDto.setListExcercises(data);
				
				listData.add(exercisesListDataDto);
			});
		}else {
			List<ExerciseDataDto> data = new ArrayList<ExerciseDataDto>();
			List<ExerciseModel> dataFind = excercisesReponsitoryCustom.getListExercisesByFilter(filters);
			
			dataFind.forEach(excercises->{
				ExerciseDataDto exerciseDataDto = modelMapper.map(excercises, ExerciseDataDto.class);
				data.add(exerciseDataDto);
			});
			
			ExerciseListDataDto exercisesListDataDto = new ExerciseListDataDto();
			MuscleType item = MuscleType.findByKey(filters.getMuscleType());
			exercisesListDataDto.setKey(item.getTitle());
			exercisesListDataDto.setValue(item.getDsr());
			exercisesListDataDto.setListExcercises(data);

			listData.add(exercisesListDataDto);
		}

		
		return listData;
	}
	

}
