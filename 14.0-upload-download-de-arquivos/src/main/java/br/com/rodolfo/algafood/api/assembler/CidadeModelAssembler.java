package br.com.rodolfo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.CidadeModel;
import br.com.rodolfo.algafood.domain.models.Cidade;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public List<CidadeModel> toCollection(List<Cidade> cidades) {
        return cidades.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
