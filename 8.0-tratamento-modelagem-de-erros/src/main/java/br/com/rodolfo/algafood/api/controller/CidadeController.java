package br.com.rodolfo.algafood.api.controller;

import java.util.List;

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
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidade-id}")
    public Cidade buscar(@PathVariable("cidade-id") Long id) {
        return cadastroCidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Cidade salvar(@RequestBody Cidade cidade) {
        return cadastroCidadeService.salvar(cidade);
    }

    @PutMapping("/{cidade-id}")
    public Cidade atualizar(
        @PathVariable("cidade-id") Long id,
        @RequestBody Cidade cidade
    ) {
        Cidade cidadeSalva = cadastroCidadeService.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeSalva, "id");

        return cadastroCidadeService.salvar(cidadeSalva);
    }

    @DeleteMapping("/{cidade-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("cidade-id") Long id) {
        cadastroCidadeService.excluir(id);
    }
}
