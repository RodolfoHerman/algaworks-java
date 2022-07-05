package br.com.rodolfo.algafood.domain.filter;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaFilter {

    private Long restauranteId;
    private OffsetDateTime dataCriacaoInicio;
    private OffsetDateTime dataCriacaoFim;
}