package br.com.rodolfo.algafood.services;

import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.NotificadorEmail;

@Component
public class AtivacaoClienteService {
    
    private NotificadorEmail notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
