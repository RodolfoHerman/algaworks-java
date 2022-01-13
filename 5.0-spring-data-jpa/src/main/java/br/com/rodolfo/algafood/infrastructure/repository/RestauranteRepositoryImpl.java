package br.com.rodolfo.algafood.infrastructure.repository;

import static br.com.rodolfo.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.rodolfo.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = new StringBuilder("from Restaurante where 0 = 0 ");
        var parametros = new HashMap<String, Object>();

        if(StringUtils.hasText(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%".concat(nome).concat("%"));
        }

        if(Objects.nonNull(taxaFreteInicial)) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if(Objects.nonNull(taxaFreteFinal)) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach((key, value) -> query.setParameter(key, value));

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findWithCriteriaAPI(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%".concat(nome).concat("%")));
        }

        if(Objects.nonNull(taxaFreteInicial)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(Objects.nonNull(taxaFreteFinal)) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(Predicate[]::new));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratisENomeSemelhante(String nome) {
        return restauranteRepository.findAll(comFreteGratis()
            .and(comNomeSemelhante(nome)));
    }
}
