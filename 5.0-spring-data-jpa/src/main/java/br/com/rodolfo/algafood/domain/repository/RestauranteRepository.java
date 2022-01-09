package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodolfo.algafood.domain.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> { }
