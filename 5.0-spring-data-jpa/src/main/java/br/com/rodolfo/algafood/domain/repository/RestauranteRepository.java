package br.com.rodolfo.algafood.domain.repository;

import java.util.List;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscar(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);
}
