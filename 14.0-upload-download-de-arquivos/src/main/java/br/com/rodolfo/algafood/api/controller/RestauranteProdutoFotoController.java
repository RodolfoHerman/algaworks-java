package br.com.rodolfo.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/produtos/{produto-id}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId,
        @Valid FotoProdutoInput fotoProdutoInput
    ) {
        var nomeArquivo = UUID.randomUUID().toString().concat("_")
            .concat(fotoProdutoInput.getArquivo().getOriginalFilename());

        var arquivoFoto = Path.of("C:\\Users\\h-e-r\\Documents", nomeArquivo);

        System.out.println(fotoProdutoInput.getDescricao());
        System.out.println(arquivoFoto);
        System.out.println(fotoProdutoInput.getArquivo().getContentType());

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
