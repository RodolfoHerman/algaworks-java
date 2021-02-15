package br.com.rodolfo.algafood.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rodolfo.algafood.notificador.NotificadorEmail;

@Configuration
public class NotificadorConfig {

    @Bean
    public NotificadorEmail notificadorEmail() {
        NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.alfa.com");
        notificadorEmail.setCaixaAlta(true);

        return notificadorEmail;
    }
}
