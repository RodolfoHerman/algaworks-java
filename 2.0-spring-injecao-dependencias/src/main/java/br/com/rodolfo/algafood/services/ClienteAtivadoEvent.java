package br.com.rodolfo.algafood.services;

import br.com.rodolfo.algafood.models.Cliente;

public class ClienteAtivadoEvent {

    private Cliente cliente;

    public ClienteAtivadoEvent(Cliente cliente) {
        super();
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return this.cliente;
    }
}
