package com.fit.ws.resource.repositorys.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fit.ws.resource.dto.filter.PostFilter;
import com.fit.ws.resource.models.PostModel;
import com.fit.ws.resource.repositorys.PostRepositoryCustom;

@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

	@Autowired
	private MongoTemplate mongoTemplate;

	private List<Criteria> buildCriteria (PostFilter postFilter){
		List<Criteria> listCriterias = new ArrayList<Criteria>();

		if(postFilter.getIdUser() != null) {
			listCriterias.add(Criteria.where("userId").is(postFilter.getIdUser()));
		}


		return listCriterias;
	}

	@Override
	public List<PostModel> listPostOwner(PostFilter postFilter) {
		Query query = new Query();
		List<Criteria> listCriterias = new ArrayList<Criteria>();
		listCriterias.add(Criteria.where("userId").is(postFilter.getIdUser()));
		query.addCriteria(new Criteria().andOperator(listCriterias));
		return mongoTemplate.find(query, PostModel.class);
	}

	@Override
	public List<PostModel> findPostByFilter(PostFilter postFilter) {
		Query query = new Query();
		List<Criteria> listCriterias = buildCriteria(postFilter);
		if( !listCriterias.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(listCriterias));
		}


		query.limit(postFilter.getLimit());
		query.limit(postFilter.getSkip());

		return mongoTemplate.find(query, PostModel.class);
	}

}
