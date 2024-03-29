package br.com.rodolfo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

    private String uri;
    private String title;

    private ProblemType(String path, String title) {
        this.uri = "http://localhost:8080".concat(path);
        this.title = title;
    }
}
