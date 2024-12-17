package io.thomaslincoln.restapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.thomaslincoln.restapi.models.Ingrediente;
import io.thomaslincoln.restapi.repositories.IngredienteRepository;
import jakarta.transaction.Transactional;

@Service
public class IngredienteService {

  @Autowired
  private IngredienteRepository ingredienteRepository;

  @Transactional
  public Ingrediente create(Ingrediente obj) {
    obj.setId(null);
    obj = this.ingredienteRepository.save(obj);
    return obj;
  }

  public Ingrediente findById(Long id) {
    Optional<Ingrediente> ingrediente = this.ingredienteRepository.findById(id);
    return ingrediente.orElseThrow(() -> new RuntimeException(
        "Ingrediente não encontrado! Id: " + id + ", Tipo: " + Ingrediente.class.getName()));
  }

  public Ingrediente update(Ingrediente obj) {
    Ingrediente novoIngrediente = findById(obj.getId());
    novoIngrediente.setNome(obj.getNome());
    novoIngrediente.setUnidade(obj.getUnidade());
    return this.ingredienteRepository.save(novoIngrediente);
  }

  public void deleteById(Long id) {
    findById(id);
    try {
      this.ingredienteRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Não é possível apagar o ingrediente.");
    }
  }
}
