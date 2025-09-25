package com.fit.ws.resource.services;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.fit.ws.core.commons.AbstractCrudService;
import com.fit.ws.core.exceptions.AppException;
import com.fit.ws.core.exceptions.ErrorCode;
import com.fit.ws.resource.dto.PostCreateDto;
import com.fit.ws.resource.dto.filter.PostFilter;
import com.fit.ws.resource.models.PostModel;
import com.fit.ws.resource.repositorys.PostReponsitory;
import com.fit.ws.resource.repositorys.PostRepositoryCustom;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService extends AbstractCrudService<PostModel, ObjectId>{
	
	private final PostReponsitory postReponsitory;
	private final ModelMapper modelMapper;
	private final PostRepositoryCustom postRepositoryCustom;

	@Override
	protected MongoRepository<PostModel, ObjectId> getRepository() {
		return postReponsitory;
	}
	
	public PostModel createNewPost(PostCreateDto createDto) {
		PostModel createPost = modelMapper.map(createDto, PostModel.class);
		createPost.setCreateTime(new Date());
		return create(createPost);
	}
	
	public PostModel updatePost(String id,PostCreateDto updatePost) {
		PostModel postModel = getById(new ObjectId(id));
		if(postModel == null)
			throw new AppException(ErrorCode.DATA_NOT_FOUND);
		
		postModel.setCaption(updatePost.getCaption());
		postModel.setMedia(updatePost.getMedia());
		return update(postModel);
	}
	
	public List<PostModel> getListPostOwner(PostFilter postFilter){
		return postRepositoryCustom.listPostOwner(postFilter);
	}
	
	public List<PostModel> getListPostByFilter(PostFilter postFilter){
		return postRepositoryCustom.findPostByFilter(postFilter);
	}

}
