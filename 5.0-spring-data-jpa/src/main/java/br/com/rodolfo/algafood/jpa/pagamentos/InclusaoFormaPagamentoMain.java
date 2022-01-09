package br.com.rodolfo.algafood.jpa.pagamentos;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.rodolfo.algafood.AlgafoodApiApplication;
import br.com.rodolfo.algafood.domain.models.FormaPagamento;
import br.com.rodolfo.algafood.domain.repository.FormaPagamentoRepository;

public class InclusaoFormaPagamentoMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);

        FormaPagamento formaPagamento1 = new FormaPagamento();
        formaPagamento1.setDescricao("CHEQUE");

        FormaPagamento formaPagamento2 = new FormaPagamento();
        formaPagamento2.setDescricao("CARTÃO");

        formaPagamento1 = formaPagamentoRepository.save(formaPagamento1);
        formaPagamento2 = formaPagamentoRepository.save(formaPagamento2);

        System.out.println(formaPagamento1);
        System.out.println(formaPagamento2);
    }
}
