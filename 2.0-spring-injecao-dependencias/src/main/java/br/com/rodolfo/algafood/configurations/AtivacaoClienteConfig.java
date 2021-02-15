package br.com.rodolfo.algafood.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rodolfo.algafood.notificador.Notificador;
import br.com.rodolfo.algafood.services.AtivacaoClienteService;

@Configuration
public class AtivacaoClienteConfig {

    @Bean
    public AtivacaoClienteService ativacaoClienteService(Notificador notificador) {
        return new AtivacaoClienteService(notificador);
    }
}
