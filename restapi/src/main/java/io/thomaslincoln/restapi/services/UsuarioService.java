package io.thomaslincoln.restapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.thomaslincoln.restapi.models.Usuario;
import io.thomaslincoln.restapi.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Transactional
  public Usuario create(Usuario obj) {
    obj.setId(null);
    obj = this.usuarioRepository.save(obj);
    return obj;
  }

  public Usuario findById(Long id) {
    Optional<Usuario> usuario = this.usuarioRepository.findById(id);
    return usuario.orElseThrow(() -> new RuntimeException(
        "Usuário não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
  }

  public Usuario update(Usuario obj){
    Usuario novoUsuario = findById(obj.getId());
    novoUsuario.setSenha(obj.getSenha());
    return this.usuarioRepository.save(novoUsuario);
  }

  public void deleteById(Long id){
    findById(id);
    try {
      this.usuarioRepository.deleteById(id);
    } catch (Exception e){
      throw new RuntimeException("Não é possível apagar o usuário");
    }
  }
}
