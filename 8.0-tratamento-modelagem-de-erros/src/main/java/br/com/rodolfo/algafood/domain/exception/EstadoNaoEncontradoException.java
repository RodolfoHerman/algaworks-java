package br.com.rodolfo.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public EstadoNaoEncontradoException(Long idEstado) {
        super(String.format("Não existe um cadastro de estado com o código %d", idEstado));
    }
}
