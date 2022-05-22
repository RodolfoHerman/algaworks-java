package br.com.rodolfo.algafood.api.model.mixin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;
}
