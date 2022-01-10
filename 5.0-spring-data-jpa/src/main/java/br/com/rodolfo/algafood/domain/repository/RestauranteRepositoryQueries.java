package br.com.rodolfo.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
