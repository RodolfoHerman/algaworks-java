package br.com.rodolfo.algafood.jpa.restaurantes;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

public class AlteracaoRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1l);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1l);
        restaurante.setTaxaFrete(new BigDecimal("5.50"));
        restaurante.setNome("Bar do Chico");
        restaurante.setCozinha(cozinha);

        restaurante = restauranteRepository.salvar(restaurante);

        System.out.println(restaurante);
    }
}
