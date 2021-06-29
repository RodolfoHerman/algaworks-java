package br.com.rodolfo.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
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
        return restauranteRepository.listar();
    }

    @GetMapping("/{restaurante-id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restaurante-id") Long id) {
        Restaurante restaurante = restauranteRepository.buscar(id);
    
        if(Objects.nonNull(restaurante)) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restaurante-id}")
    public ResponseEntity<?> atualizar(
        @PathVariable("restaurante-id") long id,
        @RequestBody Restaurante restaurante
    ) {
        Restaurante restauranteSalvo = restauranteRepository.buscar(id);

        if(Objects.nonNull(restauranteSalvo)) {
            try {
                BeanUtils.copyProperties(restaurante, restauranteSalvo, "id");
                restauranteSalvo = cadastroRestauranteService.salvar(restauranteSalvo);

                return ResponseEntity.ok(restauranteSalvo);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{restaurante-id}")
    public ResponseEntity<?> autualizarParcial(
        @PathVariable("restaurante-id") Long id,
        @RequestBody Map<String, Object> values
    ) {
        Restaurante restaurante = restauranteRepository.buscar(id);

        if(Objects.nonNull(restaurante)) {
            merge(values, restaurante);

            return atualizar(id, restaurante);
        }

        return ResponseEntity.notFound().build();
    }

    private void merge(Map<String, Object> values, Restaurante restaurante) {
        ObjectMapper mapper = new ObjectMapper();
        Restaurante restauranteOrigem = mapper.convertValue(values, Restaurante.class);

        values.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, key);
            field.setAccessible(Boolean.TRUE);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restaurante, novoValor);
        });
    }
}
