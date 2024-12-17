package io.thomaslincoln.restapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.thomaslincoln.restapi.models.Ingrediente;
import io.thomaslincoln.restapi.services.IngredienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ingrediente")
@Validated
public class IngredienteController {

  @Autowired
  private IngredienteService ingredienteService;

  // Obter Ingrediente por ID
  @GetMapping("/{id}")
  public ResponseEntity<Ingrediente> getById(@PathVariable Long id) {
    Ingrediente ingrediente = ingredienteService.findById(id);
    return ResponseEntity.ok().body(ingrediente);
  }

  // Criar novo Ingrediente
  @PostMapping()
  public ResponseEntity<Void> create(@Valid @RequestBody Ingrediente ingrediente) {
    Ingrediente novoIngrediente = ingredienteService.create(ingrediente);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(novoIngrediente.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

    // Atualizar Ingrediente
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Ingrediente ingrediente) {
    ingrediente.setId(id);
    ingredienteService.update(ingrediente);
    return ResponseEntity.noContent().build();
  }

  // Deletar Ingrediente
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    ingredienteService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
