package br.com.creativedrivebrasil.usermicroservicer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    //public List<GetAllUserFilter> getAll(String lastName);
    
}
