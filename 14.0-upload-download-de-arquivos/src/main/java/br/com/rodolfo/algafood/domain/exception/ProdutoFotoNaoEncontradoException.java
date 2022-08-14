package br.com.rodolfo.algafood.domain.exception;

public class ProdutoFotoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoFotoNaoEncontradoException(long produtoId, Long restauranteId) {
        super(String.format(
            "Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
            produtoId, restauranteId));
    }
}
