package br.com.rodolfo.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.rodolfo.algafood.domain.models.Estado;

public class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
