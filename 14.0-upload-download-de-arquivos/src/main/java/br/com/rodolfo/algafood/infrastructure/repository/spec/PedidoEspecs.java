package br.com.rodolfo.algafood.infrastructure.repository.spec;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.rodolfo.algafood.domain.filter.PedidoFilter;
import br.com.rodolfo.algafood.domain.models.Pedido;

public class PedidoEspecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {
            if(Pedido.class.equals(query.getResultType())) {
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            var predicates = new ArrayList<Predicate>();

            if(Objects.nonNull(filtro.getClienteId())) {
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if(Objects.nonNull(filtro.getRestauranteId())) {
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if(Objects.nonNull(filtro.getDataCriacaoInicio())) {
                predicates.add(builder.greaterThanOrEqualTo(
                    root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }

            if(Objects.nonNull(filtro.getDataCriacaoFim())) {
                predicates.add(builder.lessThanOrEqualTo(
                    root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
