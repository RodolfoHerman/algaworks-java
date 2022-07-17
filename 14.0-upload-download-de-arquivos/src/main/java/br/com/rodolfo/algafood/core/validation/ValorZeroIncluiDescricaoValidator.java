package br.com.rodolfo.algafood.core.validation;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valido = Boolean.TRUE;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), this.valorField)
                .getReadMethod().invoke(objetoValidacao);

            String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), this.descricaoField)
                .getReadMethod().invoke(objetoValidacao);

            if(Objects.nonNull(valor) && BigDecimal.ZERO.compareTo(valor) == 0 && Objects.nonNull(descricao)) {
                valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }

            return valido;

        } catch (Exception ex) {
            throw new ValidationException(ex);
        }
    }
}
