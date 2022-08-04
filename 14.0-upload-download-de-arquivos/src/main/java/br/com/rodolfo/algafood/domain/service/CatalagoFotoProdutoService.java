package br.com.rodolfo.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.models.FotoProduto;
import br.com.rodolfo.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalagoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = produtoRepository
            .findFotoById(restauranteId, produtoId);

        fotoExistente.ifPresent(value ->
            produtoRepository.delete(value));

        return produtoRepository.save(foto);
    }
}
