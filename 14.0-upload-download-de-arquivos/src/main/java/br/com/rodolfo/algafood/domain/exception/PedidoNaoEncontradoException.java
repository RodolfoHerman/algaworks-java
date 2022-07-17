package br.com.rodolfo.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Não existe cadastro de pedido com código %s", codigo));
    }
}
