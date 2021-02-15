package br.com.rodolfo.algafood.notificador;

import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;

@Component
public class NotificadorEmail {

    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf(
            "Notificando %s através do e-mail %s: %s \n",
            cliente.getNome(), cliente.getEmail(), mensagem
        );
    }
}
