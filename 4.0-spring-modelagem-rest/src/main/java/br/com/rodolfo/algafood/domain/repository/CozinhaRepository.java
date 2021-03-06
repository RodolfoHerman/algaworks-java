package br.com.rodolfo.algafood.domain.repository;

import java.util.List;

import br.com.rodolfo.algafood.domain.models.Cozinha;

public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscar(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Long id);
}
