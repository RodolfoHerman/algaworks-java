package br.com.rodolfo.algafood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.Notificador;
import br.com.rodolfo.algafood.notificador.TipoNotificador;
import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;

@Component
public class AtivacaoClienteService {
    
    @TipoNotificador(NivelUrgencia.NAO_URGENTE)
    @Autowired
    private Notificador notificador;

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }
}
