package br.com.rodolfo.algafood.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {

    @Bean
    FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        var filterRegistrantion = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistrantion.setFilter(new SquigglyRequestFilter());
        filterRegistrantion.setOrder(1);
        filterRegistrantion.setUrlPatterns(urlPatterns);

        return filterRegistrantion;
    }
}
