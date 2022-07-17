package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.CidadeInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.CidadeModelAssembler;
import br.com.rodolfo.algafood.api.model.CidadeModel;
import br.com.rodolfo.algafood.api.model.input.CidadeInput;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.exception.NegocioException;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.repository.CidadeRepository;
import br.com.rodolfo.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler.toCollection(cidadeRepository.findAll());
    }

    @GetMapping("/{cidade-id}")
    public CidadeModel buscar(@PathVariable("cidade-id") Long id) {
        return cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidade-id}")
    public CidadeModel atualizar(
        @PathVariable("cidade-id") Long id,
        @RequestBody @Valid CidadeInput cidadeInput
    ) {
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(id);

        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidade);

        try {
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cidade-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("cidade-id") Long id) {
        cadastroCidadeService.excluir(id);
    }
}
