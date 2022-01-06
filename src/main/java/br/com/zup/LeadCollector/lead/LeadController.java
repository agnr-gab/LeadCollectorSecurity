package br.com.zup.LeadCollector.lead;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leads")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //por questão de serguraça nunca retorna a model fazer um DTO posteriormente
    public Lead cadastrarLead (@RequestBody Lead lead){
        return leadService.salvarLEad(lead);
    }

    @GetMapping
    public Iterable<Lead> exibirTodosLeads() {
        return leadService.exibirLeads();
    }

    @PutMapping
    public Lead atualizarLead (@RequestBody Lead lead) {
        return leadService.atualizarLead(lead);
    }
}