package io.thomaslincoln.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.thomaslincoln.restapi.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  @Transactional(readOnly = true)
  Usuario findByApelido(String apelido);
}
