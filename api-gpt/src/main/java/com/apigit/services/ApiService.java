package com.apigit.services;

import com.apigit.entities.ApiResponse;
import com.apigit.entities.ComponentContext;
import com.apigit.entities.Message;
import com.apigit.enums.ComponentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    private ComponentContextService componentContextService;
    public void setData(ApiResponse body, ComponentEnum componentEnum, String insight){
        body.setDeployment("gpt-35-turbo");
        body.setTemperature(1);
        body.setN(1);
        body.setStop("string");
        body.setMaxTokens(4096);
        body.setPresencePenalty(0);
        body.setFrequencyPenalty(0);
        body.setLogitBias(Map.of("", 0.0));
        body.setUser("studai");

        List<ComponentContext> list = componentContextService.findAll();
        ComponentContext result = list.stream()
                .filter(c -> c.getComponent().equals(componentEnum))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not founded component with name " + componentEnum));

        Message message = new Message("user", result.getContext());
        Message messageSystem = new Message("system", "Este gráfico é extremamente útil para entender como seu contato inteligente está sendo adotado pelo público. " +
                "Observando o número de contatos únicos recorrentes, é possível entender o quão engajadas estão as pessoas que estão interagindo com o chatbot. Além disso," +
                " a taxa de recorrência também é um indicador importante, pois mostra quantas pessoas estão voltando a interagir com o chatbot após uma primeira experiência. " +
                "Com essas informações, é possível ajustar a estratégia de atendimento e melhorar a experiência do usuário com o contato inteligente, aumentando assim as chances " +
                "de conversão e fidelização de clientes");
        Message messageInsight = new Message("user", insight);

        body.getMessages().add(message);
        body.getMessages().add(messageSystem);
        body.getMessages().add(messageInsight);

    }

    public String createInsight(ComponentEnum componentEnum, String insight){
        //primeiro encontra o componente que foi enviado no parametro da requisicao (ex: CONTATOS)
        // depois disso concatena ele com a analise pedida pelo usuario
        List<ComponentContext> list = componentContextService.findAll();
        ComponentContext result = list.stream()
                .filter(c -> c.getComponent().equals(componentEnum))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not founded component with name " + componentEnum));
        String insightText = result.getContext() + " Com base no CONTEXTO passado, " + insight;
        return insightText;

    }
}
