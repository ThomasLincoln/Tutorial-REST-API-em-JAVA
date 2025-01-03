package io.thomaslincoln.restapi.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.thomaslincoln.restapi.models.enums.ProfileEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = Usuario.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

  public static final String TABLE_NAME = "usuarios";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NotBlank
  @Column(name = "apelido", length = 30, nullable = false, unique = true)
  @Size(min = 6, max = 30)
  private String apelido;

  @NotBlank
  @Column(name = "nome", length = 60, nullable = false)
  @Size(min = 3, max = 60)
  private String nome;

  @JsonProperty(access = Access.WRITE_ONLY)
  @NotBlank
  @Column(name = "senha", length = 120, nullable = false)
  @Size(min = 6, max = 120)
  private String senha;

  @NotBlank
  @Column(name = "email", length = 30, nullable = false)
  @Size(min = 7, max = 30)
  private String email;

  @OneToMany(mappedBy = "usuario")
  @JsonIgnore
  private List<Receita> receitas = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @CollectionTable(name = "user_profile")
  @Column(name = "profile", nullable = false)
  private Set<Integer> profiles = new HashSet<>();

  public Set<ProfileEnum> getProfiles(){
    return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
  }

  public void addProfile(ProfileEnum profileEnum){
    this.profiles.add(profileEnum.getCode());
  }
}
