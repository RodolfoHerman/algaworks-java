package br.com.rodolfo.algafood.notificador;

import br.com.rodolfo.algafood.models.Cliente;

public interface Notificador {

    void notificar(Cliente cliente, String mensagem);
}
