package br.com.rodolfo.algafood.jpa.estados;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.EstadoRepository;

public class InclusaoEstadoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Estado estado1 = new Estado();
        estado1.setNome("SP");

        Estado estado2 = new Estado();
        estado2.setNome("RJ");

        estado1 = estadoRepository.save(estado1);
        estado2 = estadoRepository.save(estado2);

        System.out.println(estado1);
        System.out.println(estado2);
    }
}
