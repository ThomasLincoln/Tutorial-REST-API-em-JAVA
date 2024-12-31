async function getUser() {
  const userId = document.getElementById("userId").value;
  const resultDiv = document.getElementById("userResult");

  if (!userId) {
    resultDiv.innerHTML = "Por favor insira o ID!";
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/usuario/${userId}`);
    if (!response.ok) {
      throw new Error(`Erro: ${response.status} ${response.statusText}`)
    }
    const user = await response.json();
    resultDiv.innerHTML = `
      <strong>Usuário Encontrado:</strong><br>
      ID: ${user.id}<br>
      Nome: ${user.nome}<br>
      Email: ${user.email}<br>
    `
  } catch (error) {
    resultDiv.innerHTML = `Erro ao buscar o usuário: ${error.message}`;
  }
}

async function getReceita() {
  const receitaId = document.getElementById("receitaId").value;
  const resultDiv = document.getElementById("receitaResult");

  if (!receitaId) {
    resultDiv.innerHTML = "Por favor insira o ID!";
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/receita/${receitaId}`);
    let ingredientesList = "";

    if (!response.ok) {
      throw new Error(`Erro: ${response.status} ${response.statusText}`)
    }
    const receita = await response.json();

    if (receita.ingredientes && receita.ingredientes.length > 0) {
      receita.ingredientes.forEach((item) => {
        ingredientesList += `
          <li>
          Ingrediente Id: ${item.ingrediente}, 
          Quantidade: ${item.quantidade},
          Unidade: ${item.unidade}
          </li>
        `
      })
    } else {
      ingredientesList = "<li>Sem ingredientes cadastrados.</li>";
    }

    resultDiv.innerHTML = `
      <strong>Receita Encontrado:</strong><br>
      ID: ${receita.id}<br>
      Nome: ${receita.nome}<br>
      Descrição: ${receita.descricao}<br>
      Tempo de Preparo: ${receita.tempoDePreparo} minutos<br>
      Modo de Preparo: ${receita.modoDePreparo}<br>
      Dificuldade: ${receita.dificuldade}<br>
      <strong>Ingredientes:</strong>
      <ul class="ingredients">
        ${ingredientesList}
      </ul>
    `
  } catch (error) {
    resultDiv.innerHTML = `Erro ao buscar a receita: ${error.message}`;
  }
}

async function getIngrediente() {
  const ingredienteId = document.getElementById("ingredienteId").value;
  const resultDiv = document.getElementById("ingredienteResult");

  if (!ingredienteId) {
    resultDiv.innerHTML = "Por favor, insira um ID.";
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/ingrediente/${ingredienteId}`
    );
    if (!response.ok) {
      throw new Error(`Erro: ${response.status} ${response.statusText}`);
    }

    const ingrediente = await response.json();
    resultDiv.innerHTML = `
  <strong>Ingrediente Encontrado:</strong><br>
  ID: ${ingrediente.id}<br>
  Nome: ${ingrediente.nome}<br>
  Unidade: ${ingrediente.unidade}<br>
`;
  } catch (error) {
    resultDiv.innerHTML = `Erro ao buscar o ingrediente: ${error.message}`;
  }
}

async function getReceitasByUser() {
  const userId = document.getElementById("findUserId").value;
  const resultDiv = document.getElementById("userReceitasResult");

  if (!userId) {
    resultDiv.innerHTML = "Por favor, insira um ID.";
    return;
  }

  try {
    const response = await fetch(
      `http://localhost:8080/receita/usuario/${userId}`
    );
    if (!response.ok) {
      throw new Error(`Erro: ${response.status} ${response.statusText}`);
    }

    const receitas = await response.json();
    if (receitas.length === 0) {
      resultDiv.innerHTML = "Nenhuma receita encontrada para este usuário.";
      return;
    }

    let receitasHtml = "";
    receitas.forEach((receita) => {
      let ingredientesList = "";
      if (receita.ingredientes && receita.ingredientes.length > 0) {
        receita.ingredientes.forEach((item) => {
          ingredientesList += `<li>Ingrediente ID: ${item.ingrediente}, Quantidade: ${item.quantidade} ${item.unidade}</li>`;
        });
      } else {
        ingredientesList = "<li>Sem ingredientes cadastrados.</li>";
      }

      receitasHtml += `
      <div>
          <strong>Receita:</strong><br>
          ID: ${receita.id}<br>
          Nome: ${receita.nome}<br>
          Descrição: ${receita.descricao}<br>
          Tempo de Preparo: ${receita.tempoDePreparo} minutos<br>
          Modo de Preparo: ${receita.modoDePreparo}<br>
          Dificuldade: ${receita.dificuldade}<br>
          <strong>Ingredientes:</strong>
          <ul class="ingredients">
              ${ingredientesList}
          </ul>
          <hr>
      </div>
  `;
    });

    resultDiv.innerHTML = receitasHtml;
  } catch (error) {
    resultDiv.innerHTML = `Erro ao buscar as receitas: ${error.message}`;
  }
}