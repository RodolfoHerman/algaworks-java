package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.PedidoFilterInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.PedidoInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.PedidoModelAssembler;
import br.com.rodolfo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.rodolfo.algafood.api.model.PedidoModel;
import br.com.rodolfo.algafood.api.model.PedidoResumoModel;
import br.com.rodolfo.algafood.api.model.input.PedidoFilterInput;
import br.com.rodolfo.algafood.api.model.input.PedidoInput;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.models.Pedido;
import br.com.rodolfo.algafood.domain.models.Usuario;
import br.com.rodolfo.algafood.domain.repository.PedidoRepository;
import br.com.rodolfo.algafood.domain.repository.filter.PedidoFilter;
import br.com.rodolfo.algafood.domain.service.EmissaoPedidoService;
import br.com.rodolfo.algafood.infrastructure.repository.spec.PedidoEspecs;

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

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PedidoFilterInputDisassembler pedidoFilterInputDisassembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(
        PedidoFilterInput pedidoFilterInput,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        PedidoFilter filtro = pedidoFilterInputDisassembler.toDomainObject(pedidoFilterInput);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoEspecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler.toCollection(pedidosPage.getContent());

        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable,
            pedidosPage.getNumberOfElements());

        return pedidosResumoModelPage;
    }

    @GetMapping("/{pedido-codigo}")
    public PedidoModel buscar(@PathVariable("pedido-codigo") String pedidoCodigo) {
        return pedidoModelAssembler.toModel(emissaoPedidoService.buscarOuFalhar(pedidoCodigo));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PedidoModel salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            Usuario usuario = new Usuario();
            usuario.setId(1L);

            pedido.setCliente(usuario);

            return pedidoModelAssembler.toModel(emissaoPedidoService.emitir(pedido));

        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}
