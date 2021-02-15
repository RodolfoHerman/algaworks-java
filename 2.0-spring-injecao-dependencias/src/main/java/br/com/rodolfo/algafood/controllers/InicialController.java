package br.com.rodolfo.algafood.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.services.AtivacaoClienteService;

@Controller
public class InicialController {

    private AtivacaoClienteService ativacaoClienteService;

    public InicialController(AtivacaoClienteService ativacaoClienteService) {
        this.ativacaoClienteService = ativacaoClienteService;

        System.out.println("InicialController: " + this.ativacaoClienteService);
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        Cliente joao = new Cliente("João", "joao@teste.com", "31994478521");
        this.ativacaoClienteService.ativar(joao);

        return "hello";
    }
}
