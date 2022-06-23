package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rodolfo.algafood.domain.exception.GrupoNaoEncontradoException;
import br.com.rodolfo.algafood.domain.models.Grupo;
import br.com.rodolfo.algafood.domain.models.Permissao;
import br.com.rodolfo.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException ex) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(
                String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);

        grupo.removerPermissao(permissao);
    }

    public Grupo buscarOuFalhar(Long id) {
        return grupoRepository.findById(id)
            .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}
