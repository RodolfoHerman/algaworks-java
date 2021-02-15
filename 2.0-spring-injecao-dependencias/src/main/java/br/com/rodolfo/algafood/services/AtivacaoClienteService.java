package br.com.rodolfo.algafood.services;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.Notificador;

public class AtivacaoClienteService {
    
    private Notificador notificador;

    public AtivacaoClienteService(Notificador notificadorEmail) {
        this.notificador = notificadorEmail;

        System.out.println("AtivacaoClienteService: " + this.notificador);
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
