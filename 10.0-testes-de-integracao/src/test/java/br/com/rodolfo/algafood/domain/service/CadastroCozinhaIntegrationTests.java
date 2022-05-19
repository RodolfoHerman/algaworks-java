package br.com.rodolfo.algafood.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodolfo.algafood.domain.exception.CozinhaNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rodolfo.algafood.domain.models.Cozinha;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Test
    void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brasileira");

        cozinha = cadastroCozinhaService.salvar(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isNotNull();
    }

    @Test
    void deveRetornarErro_QuandoCadastrarCozinhaComDadosIncoretos() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        ConstraintViolationException error = Assertions.assertThrows(
            ConstraintViolationException.class, () -> cadastroCozinhaService.salvar(cozinha)
        );

        assertThat(error).isNotNull();
    }

    @Test
    void deveRetornarErro_QuandoExcluirCozinhaComIdInexistente() {
        CozinhaNaoEncontradaException error = Assertions.assertThrows(
            CozinhaNaoEncontradaException.class, () -> { cadastroCozinhaService.excluir(Long.MAX_VALUE); }
        );

        assertThat(error).isNotNull();
    }

    @Test
    void deveRetornarErro_QuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException error = Assertions.assertThrows(
            EntidadeEmUsoException.class, () -> { cadastroCozinhaService.excluir(1L); }
        );

        assertThat(error).isNotNull();
    }
}
