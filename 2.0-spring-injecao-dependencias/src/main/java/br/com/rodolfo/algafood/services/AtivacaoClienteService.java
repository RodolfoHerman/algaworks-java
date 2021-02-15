package br.com.rodolfo.algafood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.models.Cliente;
import br.com.rodolfo.algafood.notificador.Notificador;

@Component
public class AtivacaoClienteService {
    
    // Terceira forma de injeção utilizando o atributo da classe
    @Autowired
    private Notificador notificador;

    // Primeira forma de injeção com 2 construtores
    // @Autowired
    // public AtivacaoClienteService(Notificador notificadorEmail) {
    //     this.notificador = notificadorEmail;
    // }

    // public AtivacaoClienteService(String outroConstrutor) {

    // }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
    }

    // Segunda forma de injeção utilizando os Setters
    // @Autowired
    // public void setNotificador(Notificador notificador) {
    //     this.notificador = notificador;
    // }
}
