package br.com.rodolfo.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Estado> buscar(@PathVariable("estado-id") Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);

        if(estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado salvar(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estado-id}")
    public ResponseEntity<Estado> atualizar(
        @PathVariable("estado-id") Long id,
        @RequestBody Estado estado
    ) {
        Optional<Estado> estadoSalvo = estadoRepository.findById(id);

        if(estadoSalvo.isPresent()) {
            BeanUtils.copyProperties(estado, estadoSalvo.get(), "id");

            Estado estadoNovo = cadastroEstadoService.salvar(estadoSalvo.get());

            return ResponseEntity.ok(estadoNovo);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{estado-id}")
    public ResponseEntity<?> remover(@PathVariable("estado-id") Long id) {
        try {
            cadastroEstadoService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
