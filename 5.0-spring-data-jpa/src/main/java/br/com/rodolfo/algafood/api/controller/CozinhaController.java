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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Cozinha;
import br.com.rodolfo.algafood.domain.repository.CozinhaRepository;
import br.com.rodolfo.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping("/{cozinha-id}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinha-id") Long id) {
        Cozinha cozinha = cozinhaRepository.buscar(id);

        if(Objects.nonNull(cozinha)) {
            return ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha salvar(@RequestBody Cozinha cozinha) {
        return cadastroCozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinha-id}")
    public ResponseEntity<Cozinha> atualizar(
        @PathVariable("cozinha-id") Long id,
        @RequestBody Cozinha cozinha
    ) {
        Cozinha cozinhaSalva = cozinhaRepository.buscar(id);

        if(Objects.nonNull(cozinhaSalva)) {
            BeanUtils.copyProperties(cozinha, cozinhaSalva, "id");

            cozinhaSalva = cadastroCozinhaService.salvar(cozinhaSalva);

            return ResponseEntity.ok(cozinhaSalva);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinha-id}")
    public ResponseEntity<Cozinha> remover(@PathVariable("cozinha-id") Long id) {
        try {
            cadastroCozinhaService.excluir(id);
            return ResponseEntity.noContent().build();
    
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
