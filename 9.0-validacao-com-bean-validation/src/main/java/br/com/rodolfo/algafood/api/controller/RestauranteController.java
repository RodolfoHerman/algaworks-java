package br.com.rodolfo.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restaurante-id}")
    public Restaurante buscar(@PathVariable("restaurante-id") Long id) {
        return cadastroRestauranteService.buscarOuFalhar(id)    ;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return cadastroRestauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restaurante-id}")
    public Restaurante atualizar(
        @PathVariable("restaurante-id") long id,
        @RequestBody @Valid Restaurante restaurante
    ) {
        Restaurante restauranteSalvo = cadastroRestauranteService.buscarOuFalhar(id);

        BeanUtils.copyProperties(restaurante, restauranteSalvo, 
            "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

        try {
            return cadastroRestauranteService.salvar(restauranteSalvo);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{restaurante-id}")
    public Restaurante autualizarParcial(
        @PathVariable("restaurante-id") Long id,
        @RequestBody Map<String, Object> values,
        HttpServletRequest request
    ) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        merge(values, restaurante, request);

        return atualizar(id, restaurante);
    }

    private void merge(Map<String, Object> values, Restaurante restaurante, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, Boolean.TRUE);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.TRUE);
    
            Restaurante restauranteOrigem = mapper.convertValue(values, Restaurante.class);
    
            values.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, key);
                field.setAccessible(Boolean.TRUE);
    
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
    
                ReflectionUtils.setField(field, restaurante, novoValor);
            });
        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);

            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
        }
        
    }
}
