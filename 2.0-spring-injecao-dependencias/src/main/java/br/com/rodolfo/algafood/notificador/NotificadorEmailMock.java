package br.com.rodolfo.algafood.notificador;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;

@Profile("dev")
@TipoNotificador(NivelUrgencia.NAO_URGENTE)
@Component
public class NotificadorEmailMock implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {

        System.out.printf(
            "MOCK: Notificação seria enviada para %s através do e-mail %s usando: %s \n",
            cliente.getNome(), cliente.getEmail(), mensagem
        );
    }
}
