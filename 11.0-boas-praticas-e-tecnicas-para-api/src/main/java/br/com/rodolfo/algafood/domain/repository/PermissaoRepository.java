package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> { }
