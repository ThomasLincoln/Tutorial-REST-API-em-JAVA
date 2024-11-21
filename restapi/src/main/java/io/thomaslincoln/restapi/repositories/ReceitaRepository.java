package io.thomaslincoln.restapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.thomaslincoln.restapi.models.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

}
