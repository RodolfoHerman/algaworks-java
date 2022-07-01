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

import br.com.rodolfo.algafood.api.assembler.UsuarioModelAssembler;
import br.com.rodolfo.algafood.api.model.UsuarioModel;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/responsaveis")
public class RestauranteResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listar(@PathVariable("restaurante-id") Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollection(restaurante.getResponsaveis());
    }

    @PutMapping("/{responsavel-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void associarResponsavel(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("responsavel-id") Long responsavelId
    ) {
        cadastroRestauranteService.associarResponsavel(restauranteId, responsavelId);
    }

    @DeleteMapping("/{responsavel-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("responsavel-id") Long responsavelId
    ) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, responsavelId);
    }
}
