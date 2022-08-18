package br.com.rodolfo.algafood.core.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

    @Bean
    public AmazonS3 amazonS3(StorageProperties storageProperties) {
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
            storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(storageProperties.getS3().getRegiao())
        .build();
    }
}
