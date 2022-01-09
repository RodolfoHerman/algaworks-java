package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> { }
