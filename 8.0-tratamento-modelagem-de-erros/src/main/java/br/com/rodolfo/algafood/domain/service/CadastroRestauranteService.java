package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de restaurante com código %d";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, "restaurante", id)));
    }
}
