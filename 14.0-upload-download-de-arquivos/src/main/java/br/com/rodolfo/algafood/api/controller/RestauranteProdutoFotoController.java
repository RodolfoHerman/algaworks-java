package br.com.rodolfo.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rodolfo.algafood.api.assembler.FotoProdutoModelAssembler;
import br.com.rodolfo.algafood.api.model.FotoProdutoModel;
import br.com.rodolfo.algafood.api.model.input.FotoProdutoInput;
import br.com.rodolfo.algafood.domain.models.FotoProduto;
import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.service.CadastroProdutoService;
import br.com.rodolfo.algafood.domain.service.CatalagoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/produtos/{produto-id}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CatalagoFotoProdutoService catalagoFotoProdutoService;

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
}
