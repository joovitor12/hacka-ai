package com.apigit.controllers;

import com.apigit.entities.ComponentContext;
import com.apigit.services.ComponentContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComponentContextController {

    @Autowired
    private ComponentContextService service;

    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody ComponentContext componentContext){
        String result = service.save(componentContext);
        return ResponseEntity.ok().body(result);
    }

}
