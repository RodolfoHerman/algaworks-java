package br.com.rodolfo.algafood.api.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
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

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    @GetMapping("/{cidade-id}")
    public ResponseEntity<Cidade> buscar(@PathVariable("cidade-id") Long id) {
        Cidade cidade = cidadeRepository.buscar(id);

        if(Objects.nonNull(cidade)) {
            return ResponseEntity.ok(cidade);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        try {
            cidade = cadastroCidadeService.salvar(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cidade-id}")
    public ResponseEntity<?> atualizar(
        @PathVariable("cidade-id") Long id,
        @RequestBody Cidade cidade
    ) {
        Cidade cidadeSalva = cidadeRepository.buscar(id);

        if(Objects.nonNull(cidadeSalva)) {
            try {
                BeanUtils.copyProperties(cidade, cidadeSalva, "id");
                cidadeSalva = cadastroCidadeService.salvar(cidadeSalva);

                return ResponseEntity.ok(cidadeSalva);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidade-id}")
    public ResponseEntity<Cidade> remover(@PathVariable("cidade-id") Long id) {
        try {
            cadastroCidadeService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
