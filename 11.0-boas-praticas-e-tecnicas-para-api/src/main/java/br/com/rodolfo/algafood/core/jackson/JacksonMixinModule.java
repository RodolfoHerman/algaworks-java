package br.com.rodolfo.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.mixin.CidadeMixin;
import br.com.rodolfo.algafood.api.model.mixin.CozinhaMixin;
import br.com.rodolfo.algafood.api.model.mixin.RestauranteMixin;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }
}
