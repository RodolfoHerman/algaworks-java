package br.com.rodolfo.algafood.jpa.permissoes;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Permissao;
import br.com.rodolfo.algafood.domain.repository.PermissaoRepository;

public class AlteracaoPermissaoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        Permissao permissao = new Permissao();
        permissao.setId(1l);
        permissao.setNome("CONSULTAR");
        permissao.setDescricao("Permite consultar produtos no banco de dados.");

        permissao = permissaoRepository.save(permissao);

        System.out.println(permissao);
    }
}
