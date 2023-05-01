package com.apigit.entities;

import com.apigit.enums.ComponentEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "component_context")
public class ComponentContext implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private ComponentEnum component;
    private String context;
}
