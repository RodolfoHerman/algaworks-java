package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import br.com.rodolfo.algafood.api.assembler.PedidoInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.PedidoModelAssembler;
import br.com.rodolfo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.rodolfo.algafood.api.model.PedidoModel;
import br.com.rodolfo.algafood.api.model.PedidoResumoModel;
import br.com.rodolfo.algafood.api.model.input.PedidoInput;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.models.Pedido;
import br.com.rodolfo.algafood.domain.models.Usuario;
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

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    // @GetMapping
    // public List<PedidoResumoModel> listar() {
    //     return pedidoResumoModelAssembler.toCollection(pedidoRepository.findAll());
    // }

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollection(pedidos);

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if(StringUtils.isNoneBlank(campos)) {
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;
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
