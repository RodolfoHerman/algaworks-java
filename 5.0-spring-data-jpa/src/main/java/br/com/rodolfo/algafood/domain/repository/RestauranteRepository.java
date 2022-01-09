package br.com.rodolfo.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findTodosByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findTodosByNomeContainingAndCozinhaId(String nome, long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2RestaurantesByNomeContaining(String nome);

    int countRestaurantesByCozinhaId(Long cozinhaId);
}
