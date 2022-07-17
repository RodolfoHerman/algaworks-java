package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.ProdutoInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.ProdutoModelAssembler;
import br.com.rodolfo.algafood.api.model.ProdutoModel;
import br.com.rodolfo.algafood.api.model.input.ProdutoInput;
import br.com.rodolfo.algafood.domain.models.Produto;
import br.com.rodolfo.algafood.domain.models.Restaurante;
import br.com.rodolfo.algafood.domain.repository.ProdutoRepository;
import br.com.rodolfo.algafood.domain.service.CadastroProdutoService;
import br.com.rodolfo.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restaurante-id}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(
        @PathVariable("restaurante-id") Long id,
        @RequestParam(required = false, name = "incluirAtivos") boolean incluirAtivos
    ) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        List<Produto> produtos = null;

        if(incluirAtivos) {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findTodosByRestaurante(restaurante);
        }

        return produtoModelAssembler.toCollection(produtos);
    }

    @GetMapping("/{produto-id}")
    public ProdutoModel buscar(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId
    ) {
        return produtoModelAssembler.toModel(cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProdutoModel adicionar(
        @PathVariable("restaurante-id") Long restauranteId,
        @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
    }

    @PutMapping("/{produto-id}")
    public ProdutoModel atualizar(
        @PathVariable("restaurante-id") Long restauranteId,
        @PathVariable("produto-id") Long produtoId,
        @RequestBody @Valid ProdutoInput produtoInput
    ) {
        Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produto);

        return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
    }
}
