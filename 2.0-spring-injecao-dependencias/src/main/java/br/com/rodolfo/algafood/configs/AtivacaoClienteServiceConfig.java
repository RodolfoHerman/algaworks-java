package br.com.rodolfo.algafood.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rodolfo.algafood.services.AtivacaoClienteService;

@Configuration
public class AtivacaoClienteServiceConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public AtivacaoClienteService ativacaoClienteService() {
        return new AtivacaoClienteService();
    }
}
