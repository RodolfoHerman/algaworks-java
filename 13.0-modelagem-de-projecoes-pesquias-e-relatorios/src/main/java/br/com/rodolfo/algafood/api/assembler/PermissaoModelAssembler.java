package br.com.rodolfo.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.PermissaoModel;
import br.com.rodolfo.algafood.domain.models.Permissao;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollection(Collection<Permissao> permissoes) {
        return permissoes.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
