package br.com.rodolfo.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.rodolfo.algafood.api.model.mixin.CidadeMixin;
import br.com.rodolfo.algafood.domain.models.Cidade;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
