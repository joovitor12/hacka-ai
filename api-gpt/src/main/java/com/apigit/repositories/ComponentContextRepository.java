package com.apigit.repositories;


import com.apigit.entities.ComponentContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentContextRepository extends MongoRepository<ComponentContext, String> {
}
