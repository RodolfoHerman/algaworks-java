package br.com.rodolfo.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public class RestauranteSpecs {

    private RestauranteSpecs() { }

    public static Specification<Restaurante> comFreteGratis() {
        return (root, query, builder) ->
            builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) ->
            builder.like(root.get("nome"), "%".concat(nome).concat("%"));
    }
}
