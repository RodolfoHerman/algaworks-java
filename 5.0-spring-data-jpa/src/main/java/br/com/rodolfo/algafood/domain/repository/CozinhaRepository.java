package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    // List<Cozinha> buscarPorNome(String nome);
}
