package br.com.rodolfo.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.FormaPagamentoModel;
import br.com.rodolfo.algafood.domain.models.FormaPagamento;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollection(Collection<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
