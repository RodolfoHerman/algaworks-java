package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.PedidoModelAssembler;
import br.com.rodolfo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.rodolfo.algafood.api.model.PedidoModel;
import br.com.rodolfo.algafood.api.model.PedidoResumoModel;
import br.com.rodolfo.algafood.domain.repository.PedidoRepository;
import br.com.rodolfo.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollection(pedidoRepository.findAll());
    }

    @GetMapping("/{pedido-id}")
    public PedidoModel buscar(@PathVariable("pedido-id") Long id) {
        return pedidoModelAssembler.toModel(emissaoPedidoService.buscarOuFalhar(id));
    }
}
