package br.com.rodolfo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.input.PedidoFilterInput;
import br.com.rodolfo.algafood.domain.repository.filter.PedidoFilter;

@Component
public class PedidoFilterInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoFilter toDomainObject(PedidoFilterInput pedidoFilterInput) {
        return modelMapper.map(pedidoFilterInput, PedidoFilter.class);
    }
}
