package br.com.creativedrivebrasil.usermicroservicer.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.creativedrivebrasil.usermicroservicer.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;

public interface UserRepository extends MongoRepository<User, String> {

    //public List<GetAllUserFilter> getAll(String lastName);
    
}
