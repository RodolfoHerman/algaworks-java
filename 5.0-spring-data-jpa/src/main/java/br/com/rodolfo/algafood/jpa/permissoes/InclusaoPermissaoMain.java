package br.com.rodolfo.algafood.jpa.permissoes;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.Permissao;
import br.com.rodolfo.algafood.domain.repository.PermissaoRepository;

public class InclusaoPermissaoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        Permissao permissao1 = new Permissao();
        permissao1.setNome("ESCREVER_PRODUTOS");
        permissao1.setDescricao("Permite escrever produtos.");
    
        Permissao permissao2 = new Permissao();
        permissao2.setNome("CONSULTAR_CIDADES");
        permissao2.setDescricao("Permite consultar cidades.");

        permissao1 = permissaoRepository.save(permissao1);
        permissao2 = permissaoRepository.save(permissao2);

        System.out.println(permissao1);
        System.out.println(permissao2);
    }
}
