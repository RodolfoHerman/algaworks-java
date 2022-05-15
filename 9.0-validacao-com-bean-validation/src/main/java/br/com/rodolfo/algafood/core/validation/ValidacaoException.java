package br.com.rodolfo.algafood.core.validation;

import org.springframework.validation.BeanPropertyBindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    private BeanPropertyBindingResult bindingResult;
}
