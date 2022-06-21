package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.exception.RestauranteNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();
        
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        restaurante.inativar();
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
            .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
