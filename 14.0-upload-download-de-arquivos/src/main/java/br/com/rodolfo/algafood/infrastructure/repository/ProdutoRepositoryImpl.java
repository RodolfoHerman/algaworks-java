package br.com.rodolfo.algafood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.models.FotoProduto;
import br.com.rodolfo.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto foto) {
        return manager.merge(foto);
    }

    @Override
    @Transactional
    public void delete(FotoProduto foto) {
        manager.remove(foto);
    }
}
