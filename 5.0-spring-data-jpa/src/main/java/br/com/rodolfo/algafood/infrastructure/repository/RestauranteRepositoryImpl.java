package br.com.rodolfo.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = "from Restaurante where nome like :nome "
            + "and taxaFrete between :taxaInicial and :taxaFinal";

        return manager.createQuery(jpql, Restaurante.class)
            .setParameter("nome", "%".concat(nome).contains("%"))
            .setParameter("taxaInicial", taxaFreteInicial)
            .setParameter("taxaFinal", taxaFreteFinal)
            .getResultList();
    }
}
