package br.com.rodolfo.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(Long idCozinha) {
        super(String.format("Não existe um cadastro de cozinha com código %d", idCozinha));
    }
}
