package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.PedidoNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.Pedido;
import br.com.rodolfo.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
