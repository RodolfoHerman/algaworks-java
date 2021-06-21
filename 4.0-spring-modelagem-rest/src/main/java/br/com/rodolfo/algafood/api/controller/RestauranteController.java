package br.com.rodolfo.algafood.api.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

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
}
