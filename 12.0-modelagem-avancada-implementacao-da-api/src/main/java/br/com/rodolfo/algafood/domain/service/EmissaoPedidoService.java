package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.exception.PedidoNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.FormaPagamento;
import br.com.rodolfo.algafood.domain.models.Pedido;
import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.models.Usuario;
import br.com.rodolfo.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);

        pedido.definirFrete();
        pedido.calcularValorTotal();
        pedido.atribuirPedidoAosItens();

        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEndereco().getCidade().getId());
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());

        if (restaurante.naoAceitaformaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                formaPagamento.getDescricao()));
        }

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEndereco().setCidade(cidade);
        pedido.setCliente(usuario);

        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProdutoService.buscarOuFalhar(item.getProduto().getId(), restaurante.getId());

            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    public Pedido buscarOuFalhar(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
    }
}
