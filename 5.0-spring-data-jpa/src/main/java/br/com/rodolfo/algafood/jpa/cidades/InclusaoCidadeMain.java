package br.com.rodolfo.algafood.jpa.cidades;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.CidadeRepository;

public class InclusaoCidadeMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        Estado estado = new Estado();
        estado.setId(1l);

        Cidade cidade1 = new Cidade();
        cidade1.setNome("Contagem");
        cidade1.setEstado(estado);

        Cidade cidade2 = new Cidade();
        cidade2.setNome("Betim");
        cidade2.setEstado(estado);

        cidade1 = cidadeRepository.save(cidade1);
        cidade2 = cidadeRepository.save(cidade2);

        System.out.println(cidade1);
        System.out.println(cidade2);
    }
}
