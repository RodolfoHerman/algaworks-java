package br.com.rodolfo.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.CozinhaRepository;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
        return cozinhaRepository.findCozinhasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/exists-por-nome")
    public boolean cozinhasExistsPorNome(@RequestParam("nome") String nome) {
        return cozinhaRepository.existsCozinhaByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(
        @RequestParam("taxaInicial") BigDecimal taxaInicial,
        @RequestParam("taxaFinal") BigDecimal taxaFinal
    ) {
        return restauranteRepository.findTodosByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorNome(
        @RequestParam("nome") String nome,
        @RequestParam("cozinhaId") Long cozinhaId
    ) {
        return restauranteRepository.findTodosByNomeContainingAndCozinhaId(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantesPrimeiroPorNome(@RequestParam("nome") String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top-2-por-nome")
    public List<Restaurante> restaurantesTop2PorNome(@RequestParam("nome") String nome) {
        return restauranteRepository.findTop2RestaurantesByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/count-por-cozinha-id")
    public int restaurantesCountPorCozinhaId(@RequestParam("cozinhaId") Long cozinhaId) {
        return restauranteRepository.countRestaurantesByCozinhaId(cozinhaId);
    }
}
