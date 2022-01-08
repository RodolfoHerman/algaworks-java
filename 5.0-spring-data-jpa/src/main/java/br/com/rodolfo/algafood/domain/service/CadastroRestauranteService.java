package br.com.rodolfo.algafood.domain.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.CozinhaRepository;
import br.com.rodolfo.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if(Objects.isNull(cozinha)) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de cozinha com código %d", cozinhaId)
            );
        }

        restaurante.setCozinha(cozinha);

        return restauranteRepository.salvar(restaurante);
    }
}
