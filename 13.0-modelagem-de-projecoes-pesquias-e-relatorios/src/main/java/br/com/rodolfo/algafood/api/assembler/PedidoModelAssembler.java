package br.com.rodolfo.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.PedidoModel;
import br.com.rodolfo.algafood.domain.models.Pedido;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollection(Collection<Pedido> pedidos) {
        return pedidos.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
