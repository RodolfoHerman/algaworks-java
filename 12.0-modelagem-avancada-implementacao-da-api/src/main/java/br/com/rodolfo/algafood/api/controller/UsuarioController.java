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

import br.com.rodolfo.algafood.api.assembler.UsuarioAssembler;
import br.com.rodolfo.algafood.api.assembler.UsuarioInputDisassembler;
import br.com.rodolfo.algafood.api.model.UsuarioModel;
import br.com.rodolfo.algafood.api.model.input.SenhaInput;
import br.com.rodolfo.algafood.api.model.input.UsuarioComSenhaInput;
import br.com.rodolfo.algafood.api.model.input.UsuarioInput;
import br.com.rodolfo.algafood.domain.models.Usuario;
import br.com.rodolfo.algafood.domain.repository.UsuarioRepository;
import br.com.rodolfo.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioAssembler.toCollection(usuarioRepository.findAll());
    }

    @GetMapping("/{usuario-id}")
    public UsuarioModel buscar(@PathVariable("usuario-id") Long id) {
        return usuarioAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioComSenhaInput);

        return usuarioAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    @PutMapping("/{usuario-id}")
    public UsuarioModel atualizar(
        @PathVariable("usuario-id") Long id,
        @RequestBody @Valid UsuarioInput usuarioInput
    ) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(id);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        return usuarioAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    @DeleteMapping("/{usuario-id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable("usuario-id") Long id) {
        cadastroUsuarioService.excluir(id);
    }

    @PutMapping("/{usuario-id}/senha")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void atualizarSenha(
        @PathVariable("usuario-id") Long id,
        @RequestBody @Valid SenhaInput senhaInput
    ) {
        cadastroUsuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
