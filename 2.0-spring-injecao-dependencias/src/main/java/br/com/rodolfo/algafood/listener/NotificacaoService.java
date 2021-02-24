package br.com.rodolfo.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.notificador.Notificador;
import br.com.rodolfo.algafood.notificador.TipoNotificador;
import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;
import br.com.rodolfo.algafood.services.ClienteAtivadoEvent;

@Component
public class NotificacaoService {

    @TipoNotificador(NivelUrgencia.NAO_URGENTE)
    @Autowired
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo.");
    }
}
