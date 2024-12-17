package io.thomaslincoln.restapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "receita_ingredientes")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaIngrediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Column(name = "receita_id", nullable = false)
  private Long receita;

  @Column(name = "ingrediente_id", nullable = false)
  private Long ingrediente;

  @NotNull
  @Column(name = "quantidade", nullable = false)
  private Double quantidade;

  @Column(name = "unidade", length = 50, nullable = false)
  private String unidade;
}
