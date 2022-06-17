package br.com.rodolfo.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodolfo.algafood.api.model.input.RestauranteInput;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.models.Restaurante;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: identifier of an
        // instance of Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }
}