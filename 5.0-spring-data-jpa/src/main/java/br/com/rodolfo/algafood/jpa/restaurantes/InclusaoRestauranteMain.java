package br.com.rodolfo.algafood.jpa.restaurantes;

import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
        Cozinha cozinha = new Cozinha();
        cozinha.setId(1l);

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setCozinha(cozinha);
        restaurante1.setNome("Bar do Chico");
        restaurante1.setTaxaFrete(BigDecimal.ONE);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setCozinha(cozinha);
        restaurante2.setNome("Bar e Restaurante");
        restaurante2.setTaxaFrete(BigDecimal.TEN);

        restaurante1 = restauranteRepository.save(restaurante1);
        restaurante2 = restauranteRepository.save(restaurante2);

        System.out.println(restaurante1);
        System.out.println(restaurante2);
    }
}
