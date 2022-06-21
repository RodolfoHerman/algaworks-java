package br.com.rodolfo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.GrupoModel;
import br.com.rodolfo.algafood.domain.models.Grupo;

@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel toGrupoModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> toCollection(List<Grupo> grupos) {
        return grupos.stream()
            .map(this::toGrupoModel)
            .collect(Collectors.toList());
    }
}
