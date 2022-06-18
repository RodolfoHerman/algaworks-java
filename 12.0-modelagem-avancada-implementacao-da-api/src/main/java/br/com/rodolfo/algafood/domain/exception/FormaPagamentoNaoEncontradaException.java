package br.com.rodolfo.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(Long id) {
        super(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
    }
}
