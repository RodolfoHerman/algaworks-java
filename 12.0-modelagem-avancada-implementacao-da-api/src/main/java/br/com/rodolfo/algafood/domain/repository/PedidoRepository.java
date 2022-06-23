package br.com.rodolfo.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodolfo.algafood.domain.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> { }
