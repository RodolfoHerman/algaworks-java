package br.com.rodolfo.algafood.notificador;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;

@Primary
@Component
public class NotificadorEmail implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {

        System.out.printf(
            "Notificando %s através do e-mail %s usando: %s \n",
            cliente.getNome(), cliente.getEmail(), mensagem
        );
    }
}
