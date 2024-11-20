package io.thomaslincoln.restapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Ingrediente.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ingrediente {
  public static final String TABLE_NAME = "Ingrediente";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NotBlank
  @Column(name = "nome", length = 60, nullable = false)
  @Size(min = 3, max = 60)
  private String nome;

  @NotNull
  @Column(name = "quantidade", nullable = false)
  private Integer quantidade;

  @NotBlank
  @Column(name = "unidade", length = 10, nullable = false)
  private String unidade;
}
