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

import br.com.rodolfo.algafood.api.assembler.EstadoInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.EstadoModelAssembler;
import br.com.rodolfo.algafood.api.model.EstadoModel;
import br.com.rodolfo.algafood.api.model.input.EstadoInput;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.EstadoRepository;
import br.com.rodolfo.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return estadoModelAssembler.toCollection(estadoRepository.findAll());
    }

    @GetMapping("/{estado-id}")
    public EstadoModel buscar(@PathVariable("estado-id") Long id) {
        return estadoModelAssembler.toModel(cadastroEstadoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estado));
    }

    @PutMapping("/{estado-id}")
    public EstadoModel atualizar(
        @PathVariable("estado-id") Long id,
        @RequestBody @Valid EstadoInput estadoInput
    ) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(id);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estado);

        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estado));
    }

    @DeleteMapping("/{estado-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estado-id") Long id) {
        cadastroEstadoService.excluir(id);
    }
}
