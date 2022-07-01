package br.com.rodolfo.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(Long id) {
        super(String.format("Não existe um cadastro de grupo com código %d", id));
    }
}
