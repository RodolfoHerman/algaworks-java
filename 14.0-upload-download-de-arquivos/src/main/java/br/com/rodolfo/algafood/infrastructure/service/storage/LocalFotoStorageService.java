package br.com.rodolfo.algafood.infrastructure.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import br.com.rodolfo.algafood.core.storage.StorageProperties;
import br.com.rodolfo.algafood.domain.service.FotoStorageService;


public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(),
                Files.newOutputStream(arquivoPath));

        } catch (IOException ex) {
            throw new StorageException("Não foi possível armazenar arquivo.", ex);
        }
    }

    @Override
    public void remover(String nomeFoto) {
        try {
            Path path = getArquivoPath(nomeFoto);
            Files.deleteIfExists(path);

        } catch (IOException ex) {
            throw new StorageException("Não foi possível remover arquivo.", ex);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path path = getArquivoPath(nomeArquivo);

            return FotoRecuperada.builder()
                .inputStream(Files.newInputStream(path))
            .build();

        } catch (IOException ex) {
            throw new StorageException("Não foi possível recuperar arquivo.", ex);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos()
            .resolve(Path.of(nomeArquivo));
    }
}
