package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estado-id}")
    public Estado buscar(@PathVariable("estado-id") Long id) {
        return cadastroEstadoService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody @Valid Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estado-id}")
    public Estado atualizar(
        @PathVariable("estado-id") Long id,
        @RequestBody @Valid Estado estado
    ) {
        Estado estadoSalvo = cadastroEstadoService.buscarOuFalhar(id);

        BeanUtils.copyProperties(estado, estadoSalvo, "id");

        return cadastroEstadoService.salvar(estadoSalvo);
    }

    @DeleteMapping("/{estado-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("estado-id") Long id) {
        cadastroEstadoService.excluir(id);
    }
}
