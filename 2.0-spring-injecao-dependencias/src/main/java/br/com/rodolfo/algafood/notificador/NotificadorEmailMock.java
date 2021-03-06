package br.com.rodolfo.algafood.notificador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;

@Profile("dev")
@TipoNotificador(NivelUrgencia.NAO_URGENTE)
@Component
public class NotificadorEmailMock implements Notificador {

    @Autowired
    private NotificadorProperties properties;

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.println("Host: " + properties.getHostServidor());
        System.out.println("Porta: " + properties.getPortaServidor());

        System.out.printf(
            "MOCK: Notificação seria enviada para %s através do e-mail %s: %s \n",
            cliente.getNome(), cliente.getEmail(), mensagem
        );
    }
}
