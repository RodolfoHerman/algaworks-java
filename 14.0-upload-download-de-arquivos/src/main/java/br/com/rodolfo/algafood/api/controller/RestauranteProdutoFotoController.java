package br.com.rodolfo.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rodolfo.algafood.api.assembler.FotoProdutoModelAssembler;
import br.com.rodolfo.algafood.api.model.FotoProdutoModel;
import br.com.rodolfo.algafood.api.model.input.FotoProdutoInput;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.FotoProduto;
import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.service.CadastroProdutoService;
import br.com.rodolfo.algafood.domain.service.CatalagoFotoProdutoService;
import br.com.rodolfo.algafood.domain.service.FotoStorageService;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/produtos/{produto-id}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalagoFotoProdutoService catalagoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId,
        @Valid FotoProdutoInput fotoProdutoInput
    ) throws IOException {
        Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        return fotoProdutoModelAssembler.toModel(
            catalagoFotoProdutoService.salvar(foto, arquivo.getInputStream()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscar(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId
    ) {
        return fotoProdutoModelAssembler.toModel(
            catalagoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId));
    }

    @GetMapping
    public ResponseEntity<?> servirFoto(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId,
        @RequestHeader(name = "accept") String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = catalagoFotoProdutoService
                .buscarOuFalhar(produtoId, restauranteId);

            MediaType mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificaCompatibilidadeMediaType(mediaType, mediaTypesAceitas);

            var fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if(fotoRecuperada.temUrl()) {
                return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                .build();
            }

            return ResponseEntity.ok()
                .contentType(mediaType)
                .body(new InputStreamResource(fotoRecuperada.getInputStream()));

        } catch (EntidadeNaoEncontradaException ex) {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId)
    {
        catalagoFotoProdutoService.excluir(produtoId, restauranteId);
    }

    private void verificaCompatibilidadeMediaType(
        MediaType mediaType,
        List<MediaType> mediaTypesAceitas
    ) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
            .anyMatch(mediaType::isCompatibleWith);

        if(!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}
