package br.com.rodolfo.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(Long id) {
        super(String.format("Não existe cadastro de usuário com código %d", id));
    }
}
