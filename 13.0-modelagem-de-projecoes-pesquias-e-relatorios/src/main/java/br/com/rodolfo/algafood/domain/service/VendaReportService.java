package br.com.rodolfo.algafood.domain.service;

import br.com.rodolfo.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
