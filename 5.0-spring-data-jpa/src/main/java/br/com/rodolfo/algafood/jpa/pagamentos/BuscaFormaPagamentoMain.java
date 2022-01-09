package br.com.rodolfo.algafood.jpa.pagamentos;

import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.FormaPagamento;
import br.com.rodolfo.algafood.domain.repository.FormaPagamentoRepository;

public class BuscaFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(1l);

        formaPagamento.ifPresent((formaPagamentoPresent) -> System.out.println(formaPagamentoPresent));
    }
}
