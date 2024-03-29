package br.com.rodolfo.algafood.api.controller;

import static br.com.rodolfo.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.rodolfo.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

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
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;;

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

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> cozinhasPrimeiro() {
        return cozinhaRepository.buscarPrimeiro();
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(
        @RequestParam("taxaInicial") BigDecimal taxaInicial,
        @RequestParam("taxaFinal") BigDecimal taxaFinal
    ) {
        return restauranteRepository.findTodosByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome-taxa-frete")
    public List<Restaurante> restaurantesPorNomeTaxaFrete(
        @RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "taxaInicial", required = false) BigDecimal taxaInicial,
        @RequestParam(value = "taxaFinal", required = false) BigDecimal taxaFinal
    ) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome-taxa-frete-criteria")
    public List<Restaurante> restaurantesPorNomeTaxaFreteCriteria(
        @RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "taxaInicial", required = false) BigDecimal taxaInicial,
        @RequestParam(value = "taxaFinal", required = false) BigDecimal taxaFinal
    ) {
        return restauranteRepository.findWithCriteriaAPI(nome, taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorNome(
        @RequestParam("nome") String nome,
        @RequestParam("cozinhaId") Long cozinhaId
    ) {
        return restauranteRepository.consultarRestaurantesPorNome_Externalizado(nome, cozinhaId);
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

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(@RequestParam("nome") String nome) {
        return restauranteRepository.findAll(comFreteGratis()
            .and(comNomeSemelhante(nome)));
    }

    @GetMapping("/restaurantes/com-frete-gratis-interface")
    public List<Restaurante> restaurantesComFreteGratisInterface(@RequestParam("nome") String nome) {
        // Retira a responsabilidade de montar os specifications no controller no método acima (evita duplicar código e temos só um ponto de modificação)
        return restauranteRepository.findComFreteGratisENomeSemelhante(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro() {
        return restauranteRepository.buscarPrimeiro();
    }
}
