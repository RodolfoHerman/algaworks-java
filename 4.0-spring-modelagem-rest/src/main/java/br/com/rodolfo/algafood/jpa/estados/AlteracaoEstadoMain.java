package br.com.rodolfo.algafood.jpa.estados;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.EstadoRepository;

public class AlteracaoEstadoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Estado estado = new Estado();
        estado.setId(1l);
        estado.setNome("SP");

        estado = estadoRepository.salvar(estado);

        System.out.println(estado);
    }
}
