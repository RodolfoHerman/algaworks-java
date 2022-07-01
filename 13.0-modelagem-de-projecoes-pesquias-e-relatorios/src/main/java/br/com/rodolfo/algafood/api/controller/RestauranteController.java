package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.rodolfo.algafood.api.assembler.RestauranteInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.RestauranteModelAssembler;
import br.com.rodolfo.algafood.api.model.RestauranteModel;
import br.com.rodolfo.algafood.api.model.input.RestauranteInput;
import br.com.rodolfo.algafood.api.model.view.RestauranteView;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.exception.RestauranteNaoEncontradoException;
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
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteModel> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping(params = "projecao=apenas-nome")
    @JsonView(RestauranteView.ApenasNome.class)
    public List<RestauranteModel> listarApenasNomes() {
        return listar();
    }

    // @GetMapping
    // public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
    //     List<Restaurante> restaurantes = restauranteRepository.findAll();
    //     List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);

    //     MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesModel);
    //     restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);

    //     if("apenas-nome".equals(projecao)) {
    //         restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
    //     }

    //     if("completo".equals(projecao)) {
    //         restaurantesWrapper.setSerializationView(null);
    //     }

    //     return restaurantesWrapper;
    // }

    // @GetMapping(params = "projecao=resumo")
    // @JsonView(RestauranteView.Resumo.class)
    // public List<RestauranteModel> listarResumido() {
    //     return listar();
    // }

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

    @PutMapping("/{restaurante-id}/ativo")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable("restaurante-id") Long id) {
        cadastroRestauranteService.ativar(id);
    }

    @DeleteMapping("/{restaurante-id}/ativo")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable("restaurante-id") Long id) {
        cadastroRestauranteService.inativar(id);
    }

    @PutMapping("/{restaurante-id}/fechamento")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable("restaurante-id") Long id) {
        cadastroRestauranteService.fechar(id);
    }

    @PutMapping("/{restaurante-id}/abertura")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable("restaurante-id") Long id) {
        cadastroRestauranteService.abrir(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            cadastroRestauranteService.ativarMultiplos(restaurantesIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            cadastroRestauranteService.inativarMultiplos(restaurantesIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}
