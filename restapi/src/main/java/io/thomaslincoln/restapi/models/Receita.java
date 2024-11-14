package io.thomaslincoln.restapi.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Receita.TABLE_NAME)
public class Receita {
  public static final String TABLE_NAME = "Receita";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NotBlank
  @Column(name = "nome", length = 60, nullable = false)
  @Size(min = 3, max = 60)
  private String nome;

  @Column(name="descricao", length = 255)
  private String descricao;

  @Column(name = "tempo_preparo")
  private Integer tempoDePreparo;

  @NotBlank
  @Column(name = "modo_preparo", nullable = false)
  private String modoDePreparo;

  @Column(name = "dificuldade", length = 10, nullable = false)
  private String dificuldade;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @NotNull
  @Column(name = "ingredientes", nullable = false)
  private List<Ingrediente> ingrediente;
}
