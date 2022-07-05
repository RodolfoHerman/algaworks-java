package br.com.rodolfo.algafood.api.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDiariaModel {

    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
