package br.com.rodolfo.algafood.domain.service;

import java.util.List;

import br.com.rodolfo.algafood.domain.filter.VendaDiariaFilter;
import br.com.rodolfo.algafood.domain.models.dto.VendaDiaria;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
