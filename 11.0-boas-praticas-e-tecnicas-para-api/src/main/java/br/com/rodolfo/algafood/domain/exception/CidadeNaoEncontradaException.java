package br.com.rodolfo.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(Long idCidade) {
        super(String.format("Não existe um cadastro de cidade com o código %d", idCidade));
    }
}
