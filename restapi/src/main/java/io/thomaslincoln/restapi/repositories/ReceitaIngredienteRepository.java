package io.thomaslincoln.restapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import io.thomaslincoln.restapi.models.ReceitaIngrediente;

public interface ReceitaIngredienteRepository extends JpaRepository<ReceitaIngrediente, Long> {

}
