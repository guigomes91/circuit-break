package br.com.gomes.circuitbreaker.controller;

import br.com.gomes.circuitbreaker.service.ConsultaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cep")
public class CepController {

    @Autowired
    private ConsultaCepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<String> getCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(cepService.consultarCep(cep));
    }
}
