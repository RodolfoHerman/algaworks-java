package br.com.rodolfo.algafood.domain.service;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeFoto);

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString().concat("_").concat(nomeOriginal);
    }

    default void substituir(String nomeFotoExistente, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if(Objects.nonNull(nomeFotoExistente)) {
            this.remover(nomeFotoExistente);
        }
    }

    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private String contentType;
        private Long contentLength;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {

        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return StringUtils.isNotBlank(url);
        }
    }
}
