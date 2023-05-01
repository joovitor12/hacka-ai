package com.apigit.services;

import com.apigit.entities.ComponentContext;
import com.apigit.repositories.ComponentContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentContextService {
    @Autowired
    private ComponentContextRepository repository;

    public String save(ComponentContext componentContext){
        repository.save(componentContext);
        return "Added component " + componentContext.getId();
    }

    public List<ComponentContext> findAll(){
        return repository.findAll();
    }
}
