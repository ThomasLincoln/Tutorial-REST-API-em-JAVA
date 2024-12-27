const API_BASE = "http://localhost:8080";

// Funções de Usuários
async function fetchUsuarios() {
  const response = await fetch(`${API_BASE}/usuario`);
  const usuarios = await response.json();
  const usuarioList = document.getElementById("usuario-list");
  usuarioList.innerHTML = "";

  usuarios.forEach((usuario) => {
    const li = document.createElement("li");
    li.textContent = `${usuario.nome} - ${usuario.email}`;
    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Deletar";
    deleteButton.onclick = () => deleteUsuario(usuario.id);
    li.appendChild(deleteButton);
    usuarioList.appendChild(li);
  });
}

async function createUsuario(event) {
  event.preventDefault();
  const nome = document.getElementById("usuario-nome").value;
  const email = document.getElementById("usuario-email").value;

  await fetch(`${API_BASE}/usuario`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, email }),
  });

  fetchUsuarios();
}

async function deleteUsuario(id) {
  await fetch(`${API_BASE}/usuario/${id}`, { method: "DELETE" });
  fetchUsuarios();
}

// Funções de Receitas
async function fetchReceitas() {
  const response = await fetch(`${API_BASE}/receita`);
  const receitas = await response.json();
  const receitaList = document.getElementById("receita-list");
  receitaList.innerHTML = "";

  receitas.forEach((receita) => {
    const li = document.createElement("li");
    li.textContent = receita.nome;
    receitaList.appendChild(li);
  });
}

async function createReceita(event) {
  event.preventDefault();
  const nome = document.getElementById("receita-nome").value;

  await fetch(`${API_BASE}/receita`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome }),
  });

  fetchReceitas();
}

// Funções de Ingredientes
async function fetchIngredientes() {
  const response = await fetch(`${API_BASE}/ingrediente`);
  const ingredientes = await response.json();
  const ingredienteList = document.getElementById("ingrediente-list");
  ingredienteList.innerHTML = "";

  ingredientes.forEach((ingrediente) => {
    const li = document.createElement("li");
    li.textContent = ingrediente.nome;
    ingredienteList.appendChild(li);
  });
}

async function createIngrediente(event) {
  event.preventDefault();
  const nome = document.getElementById("ingrediente-nome").value;

  await fetch(`${API_BASE}/ingrediente`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome }),
  });

  fetchIngredientes();
}

// Inicializar eventos
document.getElementById("usuario-form").addEventListener("submit", createUsuario);
document.getElementById("receita-form").addEventListener("submit", createReceita);
document.getElementById("ingrediente-form").addEventListener("submit", createIngrediente);

// Carregar dados iniciais
fetchUsuarios();
fetchReceitas();
fetchIngredientes();
