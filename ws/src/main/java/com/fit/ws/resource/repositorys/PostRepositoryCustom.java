package com.fit.ws.resource.repositorys;

import java.util.List;

import com.fit.ws.resource.dto.filter.PostFilter;
import com.fit.ws.resource.models.PostModel;

public interface PostRepositoryCustom {
	List<PostModel> listPostOwner(PostFilter postFilter);
	List<PostModel> findPostByFilter(PostFilter postFilter);
}
