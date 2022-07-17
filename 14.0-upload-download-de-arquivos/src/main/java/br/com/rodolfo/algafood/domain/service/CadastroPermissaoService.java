package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.PermissaoNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Permissao;
import br.com.rodolfo.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long id) {
        return permissaoRepository.findById(id)
            .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }
}
