package br.com.rodolfo.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(Long id) {
        super(String.format("Não existe cadastro de permissão com código %d", id));
    }
}
