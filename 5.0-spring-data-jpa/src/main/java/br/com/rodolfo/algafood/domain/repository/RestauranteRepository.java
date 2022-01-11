package br.com.rodolfo.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findTodosByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> consultarRestaurantesPorNome_Externalizado(String nome, @Param("id") Long cozinhaId);

    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarRestaurantesPorNome(String nome, @Param("id") Long cozinhaId);

    List<Restaurante> findTodosByNomeContainingAndCozinhaId(String nome, long cozinhaId);

    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    List<Restaurante> findTop2RestaurantesByNomeContaining(String nome);

    int countRestaurantesByCozinhaId(Long cozinhaId);
}
