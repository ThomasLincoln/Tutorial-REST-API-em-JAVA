package io.thomaslincoln.restapi.services.Impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.thomaslincoln.restapi.models.Usuario;
import io.thomaslincoln.restapi.repositories.UsuarioRepository;
import io.thomaslincoln.restapi.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String apelido) throws UsernameNotFoundException {
    Usuario usuario = this.usuarioRepository.findByApelido(apelido);
    if (Objects.isNull(usuario)) {
      throw new UsernameNotFoundException(apelido);
    }
    return new UserSpringSecurity(
        usuario.getId(),
        usuario.getApelido(),
        usuario.getSenha(),
        usuario.getProfiles());
  }
}
