package br.com.rodolfo.algafood.domain.repository;

import br.com.rodolfo.algafood.domain.models.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);

    void delete(FotoProduto foto);
}
