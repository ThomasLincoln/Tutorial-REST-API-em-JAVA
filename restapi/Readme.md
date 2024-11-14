# Desafios

## Aula 2

- [x] Criar o Model do usuário
    - [x] Id (Long)
    - [x] Username (String)
    - [x] Nome (String)
    - [x] Senha (String)
    - [x] Email (String)

## Aula 3
- [x]  Criar o Model da Receita
    - [x]  Id (Long)
    - [x]  Nome (String)
    - [x]  Descrição (String)
    - [x]  Tempo de Preparo (Integer) - Tempo em minutos
    - [x]  Modo de Preparo (String) – texto explicativo sobre os passos do preparo.
    - [x]  Dificuldade (String) - inicialmente como `String`, podendo ser convertida para `enum` (`FACIL`, `MEDIO`, `DIFICIL`) posteriormente.
    - [x]  Usuário Usuario
    - [x]  Ingredientes List<Ingrediente>

- [x]  Criar o Model do Ingrediente
    - [x]  Id (Long)
    - [x]  Nome (String)
    - [x]  Quantidade (Inteiro) - representando a quantidade necessária do ingrediente em unidades inteiras.
    - [x]  Unidade (String) - a unidade de medida, como `g`, `ml`, etc., para simplificação inicial, podendo ser convertida em `enum` mais tarde.