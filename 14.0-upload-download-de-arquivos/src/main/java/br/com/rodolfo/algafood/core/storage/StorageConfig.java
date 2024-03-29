package br.com.rodolfo.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.rodolfo.algafood.core.storage.StorageProperties.TipoStorage;
import br.com.rodolfo.algafood.domain.service.FotoStorageService;
import br.com.rodolfo.algafood.infrastructure.service.storage.LocalFotoStorageService;
import br.com.rodolfo.algafood.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipoStorage", havingValue = "s3")
    AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
            storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(storageProperties.getS3().getRegiao())
        .build();
    }

    @Bean
    FotoStorageService getFotoStorageService() {
        if(TipoStorage.S3.equals(storageProperties.getTipoStorage())) {
            return new S3FotoStorageService();
        }

        return new LocalFotoStorageService();
    }
}
