package br.com.rodolfo.algafood.domain.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.rodolfo.algafood.domain.exception.EntidadeEmUsoException;
import br.com.rodolfo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.rodolfo.algafood.domain.models.Cidade;
import br.com.rodolfo.algafood.domain.models.Estado;
import br.com.rodolfo.algafood.domain.repository.CidadeRepository;
import br.com.rodolfo.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long idEstado = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(idEstado);

        if(Objects.isNull(estado)) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de estado com código %d", idEstado)
            );
        }

        cidade.setEstado(estado);

        return cidadeRepository.salvar(cidade);
    }

    public void excluir(long id) {
        try {
            cidadeRepository.remover(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cidade com o código %d", id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format("Cidade de código %d não pode ser removida, pois está em uso", id)
            );
        }
    }
}
