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

import br.com.rodolfo.algafood.api.assembler.FormaPagamentoAssembler;
import br.com.rodolfo.algafood.api.assembler.FormaPagamentoInputDisassembler;
import br.com.rodolfo.algafood.api.model.FormaPagamentoModel;
import br.com.rodolfo.algafood.api.model.input.FormaPagamentoInput;
import br.com.rodolfo.algafood.domain.models.FormaPagamento;
import br.com.rodolfo.algafood.domain.repository.FormaPagamentoRepository;
import br.com.rodolfo.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamanetoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return formaPagamentoAssembler.toCollection(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{forma-pagamento-id}")
    public FormaPagamentoModel buscar(@PathVariable("forma-pagamento-id") Long id) {
        return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{forma-pagamento-id}")
    public FormaPagamentoModel atualizar(
        @PathVariable("forma-pagamento-id") Long id,
        @RequestBody @Valid FormaPagamentoInput formaPagamentoInput
    ) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

        return formaPagamentoAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{forma-pagamento-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("forma-pagamento-id") Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }
}
