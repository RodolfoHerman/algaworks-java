package br.com.rodolfo.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

    private Long id;
    private String nome;
    private String email;
}
