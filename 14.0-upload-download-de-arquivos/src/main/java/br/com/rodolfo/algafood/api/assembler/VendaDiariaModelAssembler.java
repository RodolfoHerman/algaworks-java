package br.com.rodolfo.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.VendaDiariaModel;
import br.com.rodolfo.algafood.domain.models.dto.VendaDiaria;

@Component
public class VendaDiariaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public VendaDiariaModel toModel(VendaDiaria vendaDiaria) {
        return modelMapper.map(vendaDiaria, VendaDiariaModel.class);
    }

    public List<VendaDiariaModel> toCollection(Collection<VendaDiaria> vendasDiarias) {
        return vendasDiarias.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
