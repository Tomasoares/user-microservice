package br.com.creativedrivebrasil.usermicroservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
    
}
