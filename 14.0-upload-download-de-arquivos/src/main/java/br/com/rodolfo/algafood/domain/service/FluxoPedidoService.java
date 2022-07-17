package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.models.Pedido;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }
}
