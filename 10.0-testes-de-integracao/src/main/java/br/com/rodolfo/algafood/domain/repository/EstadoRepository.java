package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> { }
