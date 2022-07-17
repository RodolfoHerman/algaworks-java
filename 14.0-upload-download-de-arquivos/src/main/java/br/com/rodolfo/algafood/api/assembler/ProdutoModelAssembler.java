package br.com.rodolfo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.ProdutoModel;
import br.com.rodolfo.algafood.domain.models.Produto;

@Component
public class ProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> toCollection(List<Produto> produtos) {
        return produtos.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
