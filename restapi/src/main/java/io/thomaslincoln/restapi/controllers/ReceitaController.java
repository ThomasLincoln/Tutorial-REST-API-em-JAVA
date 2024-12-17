package io.thomaslincoln.restapi.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.thomaslincoln.restapi.models.Receita;
import io.thomaslincoln.restapi.models.ReceitaIngrediente;
import io.thomaslincoln.restapi.services.ReceitaService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/receita")
@Validated
public class ReceitaController {

  @Autowired
  private ReceitaService receitaService;

  // Create
  @PostMapping()
  public ResponseEntity<Void> createReceita(@Valid @RequestBody Receita entity) {
    this.receitaService.create(entity);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(entity.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PostMapping("/{receitaId}/ingredientes")
  public ResponseEntity<Void> adicionarIngrediente(@PathVariable Long receitaId,
    @RequestBody List<ReceitaIngrediente> ingredientes) {
      receitaService.addIngredientesToReceita(receitaId, ingredientes);
      return ResponseEntity.noContent().build();
  }
  

  // Read
  @GetMapping("/{id}")
  public ResponseEntity<Receita> getById(@PathVariable Long id) {
    Receita obj = receitaService.findById(id);
    return ResponseEntity.ok().body(obj);
  }

  // Update
  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id,@Valid @RequestBody Receita entity) {
      entity.setId(id);
      this.receitaService.update(entity);
      return ResponseEntity.noContent().build();
  }
  
  // Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    this.receitaService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/usuario/{usuarioId}")
  public ResponseEntity<List<Receita>> findAllByUserId(@PathVariable Long userId) {
    List<Receita> receitas = this.receitaService.findAllByUserId(userId);
      return ResponseEntity.ok().body(receitas);
  }
  
}
