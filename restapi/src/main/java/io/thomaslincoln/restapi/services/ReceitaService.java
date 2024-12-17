package io.thomaslincoln.restapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.thomaslincoln.restapi.models.Ingrediente;
import io.thomaslincoln.restapi.models.Receita;
import io.thomaslincoln.restapi.models.ReceitaIngrediente;
import io.thomaslincoln.restapi.repositories.IngredienteRepository;
import io.thomaslincoln.restapi.repositories.ReceitaIngredienteRepository;
import io.thomaslincoln.restapi.repositories.ReceitaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReceitaService {

  @Autowired
  private ReceitaRepository receitaRepository;

  @Autowired
  private IngredienteRepository ingredienteRepository;

  @Autowired
  private ReceitaIngredienteRepository receitaIngredienteRepository;

  @Transactional
  public Receita create(Receita obj) {
    obj.setId(null);
    obj = this.receitaRepository.save(obj);
    return obj;
  }

  @Transactional
  public void addIngredientesToReceita(Long receitaId, List<ReceitaIngrediente> ingredientes) {
    this.findById(receitaId);

    for (ReceitaIngrediente receitaIngrediente : ingredientes) {
      Ingrediente ingrediente = ingredienteRepository.findById(receitaIngrediente.getIngrediente())
          .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado!"));

      receitaIngrediente.setReceita(receitaId); 
      receitaIngrediente.setIngrediente(ingrediente.getId());
      this.receitaIngredienteRepository.save(receitaIngrediente);
    }
  }

  public Receita findById(Long id) {
    Optional<Receita> receita = this.receitaRepository.findById(id);
    return receita.orElseThrow(() -> new RuntimeException(
        "Receita não encontrada! Id: " + id + ", Tipo: " + Receita.class.getName()));
  }

  public Receita update(Receita obj) {
    Receita novaReceita = findById(obj.getId());
    novaReceita.setNome(obj.getNome());
    novaReceita.setDescricao(obj.getDescricao());
    novaReceita.setTempoDePreparo(obj.getTempoDePreparo());
    novaReceita.setModoDePreparo(obj.getModoDePreparo());
    novaReceita.setDificuldade(obj.getDificuldade());
    return this.receitaRepository.save(novaReceita);
  }

  public void deleteById(Long id) {
    findById(id);
    try {
      this.receitaRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Não é possível apagar a receita.");
    }
  }

  public List<Receita> findAllByUserId(Long userId) {
    return receitaRepository.findByUsuarioId(userId);
  }
}
