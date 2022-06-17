package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> { }
