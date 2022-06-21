package br.com.rodolfo.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rodolfo.algafood.api.model.EnderecoModel;
import br.com.rodolfo.algafood.domain.models.Endereco;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        var modelMap = new ModelMapper();

        var enderecoToEnderecoModelTypeMap = modelMap.createTypeMap(Endereco.class, EnderecoModel.class);
        enderecoToEnderecoModelTypeMap.<String>addMapping(
            endereco -> endereco.getCidade().getEstado().getNome(),
            (enderecoModel, value) -> enderecoModel.getCidade().setEstado(value));

        return modelMap;
    }
}
