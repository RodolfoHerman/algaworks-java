package br.com.rodolfo.algafood.infrastructure.service.storage;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.rodolfo.algafood.core.storage.StorageProperties;
import br.com.rodolfo.algafood.domain.service.FotoStorageService;


public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        var caminhoArquivo = getCaminhoArquivo(nomeArquivo);

        var url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
            .url(url.toString())
        .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());
            objectMetaData.setContentLength(novaFoto.getContentLength());

            var putObjectRequest = new PutObjectRequest(
                storageProperties.getS3().getBucket(),
                caminhoArquivo,
                novaFoto.getInputStream(),
                objectMetaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);

        } catch (Exception ex) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3", ex);
        }
    }

    @Override
    public void remover(String nomeFoto) {
        try {
            var caminhoArquivo = getCaminhoArquivo(nomeFoto);

            var deleteObjectRequest = new DeleteObjectRequest(
                storageProperties.getS3().getBucket(), caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);

        } catch (Exception ex) {
            throw new StorageException("Não foi possível excluir arquivo da Amazon S3", ex);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(),
            nomeArquivo);
    }
}
