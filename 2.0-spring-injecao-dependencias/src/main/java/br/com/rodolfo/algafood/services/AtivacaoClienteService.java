package br.com.rodolfo.algafood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.Notificador;

@Component
public class AtivacaoClienteService {
    
    @Autowired
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
