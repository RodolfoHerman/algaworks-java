package br.com.rodolfo.algafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.rodolfo.algafood.domain.models.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
