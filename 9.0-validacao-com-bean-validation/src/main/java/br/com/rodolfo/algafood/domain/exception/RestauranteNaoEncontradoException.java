package br.com.rodolfo.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(Long idRestaurante) {
        super(String.format("Não existe cadastro de restaurante com código %d", idRestaurante));
    }
}
