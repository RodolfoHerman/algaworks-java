package br.com.rodolfo.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

    private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com o código %d";
    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        
        Estado estado = cadastroEstadoService.buscarOuFalhar(idEstado);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format(MSG_CIDADE_NAO_ENCONTRADA, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_CIDADE_EM_USO, id)
            );
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format(MSG_CIDADE_NAO_ENCONTRADA, "cidade", id)));
    }
}
