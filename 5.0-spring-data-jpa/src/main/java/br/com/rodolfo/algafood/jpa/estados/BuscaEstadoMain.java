package br.com.rodolfo.algafood.jpa.estados;

import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.EstadoRepository;

public class BuscaEstadoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Optional<Estado> estado = estadoRepository.findById(1l);

        estado.ifPresent((estadoPresent) -> System.out.println(estadoPresent));
    }
}
