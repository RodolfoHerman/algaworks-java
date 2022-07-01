package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.FormaPagamentoAssembler;
import br.com.rodolfo.algafood.api.model.FormaPagamentoModel;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable("restaurante-id") Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return formaPagamentoAssembler.toCollection(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{forma-pagamento-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("forma-pagamento-id") Long formaPagamentoId
    ) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{forma-pagamento-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("forma-pagamento-id") Long formaPagamentoId
    ) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }
}
