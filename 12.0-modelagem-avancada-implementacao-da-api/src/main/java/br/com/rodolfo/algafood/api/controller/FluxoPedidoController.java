package br.com.rodolfo.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/pedidos/{pedido-codigo}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable("pedido-codigo") String pedidoCodigo) {
        fluxoPedidoService.confirmar(pedidoCodigo);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable("pedido-codigo") String pedidoCodigo) {
        fluxoPedidoService.cancelar(pedidoCodigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable("pedido-codigo") String pedidoCodigo) {
        fluxoPedidoService.entregar(pedidoCodigo);
    }
}
