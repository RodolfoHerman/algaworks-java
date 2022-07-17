package br.com.rodolfo.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.models.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto where restaurante_id = :restaurante_id and id = :produto_id")
    Optional<Produto> findById(@Param("produto_id") Long produtoId, @Param("restaurante_id") long restauranteId);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
    List<Produto> findAtivosByRestaurante(@Param("restaurante") Restaurante restaurante);
}
