package br.com.rodolfo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.input.VendaDiariaFilterInput;
import br.com.rodolfo.algafood.domain.filter.VendaDiariaFilter;

@Component
public class VendaDiariaFilterInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public VendaDiariaFilter toDomainObject(VendaDiariaFilterInput vendaDiariaFilterInput) {
        return modelMapper.map(vendaDiariaFilterInput, VendaDiariaFilter.class);
    }
}
