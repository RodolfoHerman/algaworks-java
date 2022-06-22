package br.com.rodolfo.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        super(String.format("Não existe cadastro de produto com código %d para o restaurante de código %d", produtoId, restauranteId));
    }
}
