package io.thomaslincoln.restapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.thomaslincoln.restapi.models.Usuario;
import io.thomaslincoln.restapi.services.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/usario")
@Validated
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  // Get
  @GetMapping("/{id}")
  public ResponseEntity<Usuario> getById(@PathVariable Long id) {
    Usuario obj = usuarioService.findById(id);
    return ResponseEntity.ok().body(obj);
  }

  // Post
  @PostMapping()
  public ResponseEntity<Void> create(@Valid @RequestBody Usuario entity) {
    this.usuarioService.create(entity);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  // Update
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id,@Valid @RequestBody Usuario entity) {
      entity.setId(id);
      this.usuarioService.update(entity);
      return ResponseEntity.noContent().build();
  }

  // Delete
  @DeleteMapping("/id")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    this.usuarioService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
