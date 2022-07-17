package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.exception.ProdutoNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long produtoId, Long restauranteId) {
        return produtoRepository.findById(produtoId, restauranteId)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }
}
