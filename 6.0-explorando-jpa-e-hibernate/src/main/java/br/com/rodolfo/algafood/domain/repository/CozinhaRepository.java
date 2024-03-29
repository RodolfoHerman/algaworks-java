package br.com.rodolfo.algafood.domain.repository;

import java.util.List;

import br.com.rodolfo.algafood.domain.models.Cozinha;

public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findCozinhasByNomeContaining(String nome);

    boolean existsCozinhaByNome(String nome);
}
