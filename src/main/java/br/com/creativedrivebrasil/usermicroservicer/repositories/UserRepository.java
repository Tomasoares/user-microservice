package br.com.creativedrivebrasil.usermicroservicer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    
}
