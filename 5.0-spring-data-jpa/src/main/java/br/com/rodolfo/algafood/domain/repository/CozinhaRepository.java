package br.com.rodolfo.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findCozinhasByNomeContaining(String nome);

    boolean existsCozinhaByNome(String nome);
}
