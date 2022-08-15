package br.com.rodolfo.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.exception.ProdutoFotoNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.FotoProduto;
import br.com.rodolfo.algafood.domain.repository.ProdutoRepository;
import br.com.rodolfo.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalagoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository
            .findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
        }

        fotoExistente.ifPresent(value ->
            produtoRepository.delete(value));

        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
            .nomeArquivo(foto.getNomeArquivo())
            .inputStream(dadosArquivo)
        .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    @Transactional
    public void excluir(Long produtoId, Long restauranteId) {
        FotoProduto fotoProduto = buscarOuFalhar(produtoId, restauranteId);

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

    public FotoProduto buscarOuFalhar(Long produtoId, Long restauranteId) {
        return produtoRepository.findFotoById(produtoId, restauranteId)
            .orElseThrow(() -> new ProdutoFotoNaoEncontradoException(produtoId, restauranteId));
    }
}
