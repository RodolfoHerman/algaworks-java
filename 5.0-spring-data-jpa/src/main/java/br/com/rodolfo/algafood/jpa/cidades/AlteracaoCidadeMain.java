package br.com.rodolfo.algafood.jpa.cidades;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.CidadeRepository;

public class AlteracaoCidadeMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);

        Estado estado = new Estado();
        estado.setId(1l);

        Cidade cidade = new Cidade();
        cidade.setId(1l);
        cidade.setNome("Contagem");
        cidade.setEstado(estado);

        cidade = cidadeRepository.save(cidade);

        System.out.println(cidade);
    }
}
