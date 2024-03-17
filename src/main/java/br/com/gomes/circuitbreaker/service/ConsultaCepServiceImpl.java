package br.com.gomes.circuitbreaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsultaCepServiceImpl implements ConsultaCepService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "gomesBankCircuitBreaker", fallbackMethod = "fallbackMethod")
    public String consultarCep(String cep) {
        String URL = "https://viacep.com.br/ws/%s/json";
        var cepEntity = restTemplate.getForEntity(String.format(URL, cep), String.class);
        return cepEntity.getBody();
    }

    public String fallbackMethod(Exception e) {
        String URL = "https://jsonplaceholder.typicode.com/posts";
        var posts = restTemplate.getForEntity(URL, String.class);
        return posts.getBody();
    }
}
