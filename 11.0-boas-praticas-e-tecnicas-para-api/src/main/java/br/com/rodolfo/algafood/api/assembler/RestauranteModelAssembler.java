package br.com.rodolfo.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.CozinhaModel;
import br.com.rodolfo.algafood.api.model.RestauranteModel;
import br.com.rodolfo.algafood.domain.models.Restaurante;

@Component
public class RestauranteModelAssembler {

    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranetModel = new RestauranteModel();
        restauranetModel.setId(restaurante.getId());
        restauranetModel.setNome(restaurante.getNome());
        restauranetModel.setTaxaFrete(restaurante.getTaxaFrete()); 
        restauranetModel.setCozinha(cozinhaModel);

        return restauranetModel;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
            .map(this::toModel)
            .collect(Collectors.toList());
    }
}
