package com.fit.ws.resource.repositorys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fit.ws.resource.dto.ExerciseFilterDto;
import com.fit.ws.resource.models.ExerciseModel;
import com.fit.ws.resource.repositorys.ExerciseReponsitoryCustom;

@Repository
public class ExerciseRepositoryCustomImpl implements ExerciseReponsitoryCustom{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private List<Criteria> createCriteria(ExerciseFilterDto filter){
		List<Criteria> listCriterias = new ArrayList<Criteria>();
		
		if(filter.getMuscleType() != null) {
			listCriterias.add(Criteria.where("muscle_type").is(filter.getMuscleType()));
		}
		
		
		return listCriterias;
	}

	@Override
	public List<ExerciseModel> getListExercisesByFilter(ExerciseFilterDto filter) {
		Query query = new Query();
		List<Criteria> criterias = createCriteria(filter);
		query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
		return mongoTemplate.find(query, ExerciseModel.class);
	}

}
