package br.com.rodolfo.algafood.notificador;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

import br.com.rodolfo.algafood.notificador.enums.NivelUrgencia;

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface TipoNotificador {

    NivelUrgencia value();
}
