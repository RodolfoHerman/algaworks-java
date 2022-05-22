package br.com.rodolfo.algafood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUtils {

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream inputStream = ResourceUtils.class.getResourceAsStream(resourceName);
            return StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
