package br.com.rodolfo.algafood.notificador;

import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;

@TipoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorSMS implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {

        System.out.printf(
            "Notificando %s por SMS através do telefone %s: %s \n",
            cliente.getNome(), cliente.getTelefone(), mensagem
        );
    }
}
