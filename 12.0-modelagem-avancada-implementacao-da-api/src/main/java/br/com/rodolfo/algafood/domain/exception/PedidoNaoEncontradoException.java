package br.com.rodolfo.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(Long id) {
        super(String.format("Não existe cadastro de pedido com código %d", id));
    }
}
