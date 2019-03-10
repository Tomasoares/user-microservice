package br.com.creativedrivebrasil.usermicroservice.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;
    
	@Override
	public List<User> getAll(GetAllUserFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
