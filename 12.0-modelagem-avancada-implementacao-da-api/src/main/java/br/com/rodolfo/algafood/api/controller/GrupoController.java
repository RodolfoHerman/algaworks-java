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

import br.com.rodolfo.algafood.api.assembler.GrupoInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.GrupoModelAssembler;
import br.com.rodolfo.algafood.api.model.GrupoModel;
import br.com.rodolfo.algafood.api.model.input.GrupoInput;
import br.com.rodolfo.algafood.domain.models.Grupo;
import br.com.rodolfo.algafood.domain.repository.GrupoRepository;
import br.com.rodolfo.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return grupoModelAssembler.toCollection(grupoRepository.findAll());
    }

    @GetMapping("/{grupo-id}")
    public GrupoModel buscar(@PathVariable("grupo-id") Long id) {
        return grupoModelAssembler.toGrupoModel(cadastroGrupoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        return grupoModelAssembler.toGrupoModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupo-id}")
    public GrupoModel atualizar(
        @PathVariable("grupo-id") Long id,
        @RequestBody @Valid GrupoInput grupoInput
    ) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(id);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupo);

        return grupoModelAssembler.toGrupoModel(cadastroGrupoService.salvar(grupo));
    }

    @DeleteMapping("/{grupo-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("grupo-id") Long id) {
        cadastroGrupoService.excluir(id);
    }
}
