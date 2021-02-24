package br.com.rodolfo.algafood.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.services.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        System.out.println("Emitindo nota fiscal para o cliente " + event.getCliente().getNome());
    }
}
