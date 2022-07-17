package br.com.rodolfo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.CozinhaModel;
import br.com.rodolfo.algafood.domain.models.Cozinha;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModel toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> toCollection(List<Cozinha> cozinhas) {
        return cozinhas.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
