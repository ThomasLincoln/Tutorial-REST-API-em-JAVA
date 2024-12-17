package io.thomaslincoln.restapi.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Receita.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Receita {
  public static final String TABLE_NAME = "receitas";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NotBlank
  @Column(name = "nome", length = 60, nullable = false)
  @Size(min = 3, max = 60)
  private String nome;

  @Column(name="descricao", length = 500)
  private String descricao;

  @Column(name = "tempo_preparo")
  private Integer tempoDePreparo;

  @Lob
  @NotBlank
  @Column(name = "modo_preparo", nullable = false)
  private String modoDePreparo;

  @Column(name = "dificuldade", length = 10, nullable = false)
  private String dificuldade;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
  private Usuario usuario;

  // Relacionamento
  @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReceitaIngrediente> ingredientes;
}
