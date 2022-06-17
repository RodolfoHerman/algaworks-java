package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.RestauranteInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.RestauranteModelAssembler;
import br.com.rodolfo.algafood.api.model.RestauranteModel;
import br.com.rodolfo.algafood.api.model.input.RestauranteInput;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;
import br.com.rodolfo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{restaurante-id}")
    public RestauranteModel buscar(@PathVariable("restaurante-id") Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restaurante-id}")
    public RestauranteModel atualizar(
        @PathVariable("restaurante-id") long id,
        @RequestBody @Valid RestauranteInput restauranteInput
    ) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restaurante);

        try {
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
