package com.fit.ws.core.commons;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class AbstractCrudService <T,ID>{
	protected abstract MongoRepository<T, ID> getRepository();
	
	public T create(T model) {
		return getRepository().save(model);
	}
	
	public T update(T model) {
		return getRepository().save(model);
	}
	
	public T getById(ID id) {
		return getRepository().findById(id).orElse(null);
	}
	
	public List<T> getAll(){
		return getRepository().findAll();
	}
	
	public void delete(ID id) {
		getRepository().deleteById(id);
	}
}
