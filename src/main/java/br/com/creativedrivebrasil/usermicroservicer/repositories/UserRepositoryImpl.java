package br.com.creativedrivebrasil.usermicroservicer.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;
    
	@Override
	public List<User> getAll(GetAllUserFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
