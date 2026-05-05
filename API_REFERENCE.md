# Referência Rápida de Endpoints - RestManager API

## 🔗 Base URL

```
http://localhost:8099
```

## 📋 Sumário de Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/v1/usuarios` | Listar todos os usuários |
| `POST` | `/v1/usuarios` | Criar novo usuário |
| `PUT` | `/v1/usuarios` | Atualizar usuário |
| `DELETE` | `/v1/usuarios` | Deletar usuário |
| `POST` | `/v1/usuarios/nome` | Buscar usuário por nome |
| `POST` | `/v1/usuarios/login` | Validar login e senha |
| `PUT` | `/v1/usuarios/senha` | Atualizar senha do usuário |

---

## 🔍 Endpoints em Detalhes

### 1️⃣ GET /v1/usuarios - Listar Todos

Retorna lista paginada de todos os usuários cadastrados.

**Requisição:**
```bash
curl -X GET "http://localhost:8099/v1/usuarios" \
  -H "Content-Type: application/json"
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "email": "joao@example.com",
    "login": "joao_silva",
    "endereco": "Rua A, 100",
    "numero": 100,
    "flagProprietario": "true",
    "dataAtualizacao": "2026-05-04T10:30:00"
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "cpf": "987.654.321-00",
    "email": "maria@example.com",
    "login": "maria_santos",
    "endereco": "Rua B, 200",
    "numero": 200,
    "flagProprietario": "false",
    "dataAtualizacao": "2026-05-04T11:45:00"
  }
]
```

**Códigos de Retorno:**
- `200` - OK
- `500` - Erro interno do servidor

---

### 2️⃣ POST /v1/usuarios - Criar Novo Usuário

Cria um novo usuário no sistema com validações.

**Requisição:**
```bash
curl -X POST "http://localhost:8099/v1/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 3,
    "nome": "Pedro Costa",
    "cpf": "111.222.333-44",
    "email": "pedro@example.com",
    "login": "pedro_costa",
    "senha": "senha_segura_123",
    "endereco": "Rua C, 300",
    "numero": 300,
    "flagProprietario": "true"
  }'
```

**Body (UsuarioCriarDTO):**
```json
{
  "id": 3,
  "nome": "Pedro Costa",
  "cpf": "111.222.333-44",
  "email": "pedro@example.com",
  "login": "pedro_costa",
  "senha": "senha_segura_123",
  "endereco": "Rua C, 300",
  "numero": 300,
  "flagProprietario": "true"
}
```

**Validações:**
- ✅ `id`: Obrigatório (Integer)
- ✅ `nome`: Obrigatório (String)
- ✅ `cpf`: Obrigatório, formato válido (String)
- ✅ `email`: Formato validado (String)
- ✅ `login`: Obrigatório (String)
- ✅ `senha`: Obrigatória (String)
- ✅ CPF deve ser unique
- ✅ Email deve ser unique
- ✅ Login deve ser unique

**Resposta (201 Created):**
```
(sem conteúdo - apenas status 201)
```

**Códigos de Retorno:**
- `201` - Created (Sucesso)
- `400` - Bad Request (Validação falhou)
- `409` - Conflict (Usuário já existe com CPF/Email/Login)
- `500` - Erro interno do servidor

**Exemplos de Erro (400):**
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "CPF informado é inválido"
}
```

---

### 3️⃣ POST /v1/usuarios/nome - Buscar por Nome

Busca um usuário específico pelo nome.

**Requisição:**
```bash
curl -X POST "http://localhost:8099/v1/usuarios/nome" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva"
  }'
```

**Body (UsuarioBuscarDTO):**
```json
{
  "nome": "João Silva"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "email": "joao@example.com",
  "login": "joao_silva",
  "endereco": "Rua A, 100",
  "numero": 100,
  "flagProprietario": "true",
  "dataAtualizacao": "2026-05-04T10:30:00"
}
```

**Códigos de Retorno:**
- `200` - OK (Usuário encontrado)
- `404` - Not Found (Usuário não encontrado)
- `500` - Erro interno do servidor

---

### 4️⃣ PUT /v1/usuarios - Atualizar Usuário

Atualiza os dados de um usuário existente.

**Requisição:**
```bash
curl -X PUT "http://localhost:8099/v1/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "João Silva Atualizado",
    "cpf": "123.456.789-00",
    "email": "joao_novo@example.com",
    "login": "joao_silva_novo",
    "endereco": "Rua A, 150",
    "numero": 150,
    "flagProprietario": "true"
  }'
```

**Body (UsuarioAtualizarDTO):**
```json
{
  "id": 1,
  "nome": "João Silva Atualizado",
  "cpf": "123.456.789-00",
  "email": "joao_novo@example.com",
  "login": "joao_silva_novo",
  "endereco": "Rua A, 150",
  "numero": 150,
  "flagProprietario": "true"
}
```

**Validações:**
- ✅ ID debe existir no banco
- ✅ CPF/Email/Login não podem ser duplicados (exceto o próprio usuário)
- ✅ Valida novo email (se diferente)

**Resposta (200 OK):**
```
(sem conteúdo - apenas status 200)
```

**Códigos de Retorno:**
- `200` - OK (Atualizado com sucesso)
- `400` - Bad Request (Validação falhou)
- `404` - Not Found (Usuário não encontrado)
- `409` - Conflict (CPF/Email/Login duplicado)
- `500` - Erro interno do servidor

---

### 5️⃣ PUT /v1/usuarios/senha - Atualizar Senha

Atualiza a senha de um usuário.

**Requisição:**
```bash
curl -X PUT "http://localhost:8099/v1/usuarios/senha" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "cpf": "123.456.789-00",
    "login": "joao_silva",
    "senha": "nova_senha_123"
  }'
```

**Body (UsuarioSenhaDTO):**
```json
{
  "id": 1,
  "cpf": "123.456.789-00",
  "login": "joao_silva",
  "senha": "nova_senha_123"
}
```

**Validações:**
- ✅ CPF deve existir no banco
- ✅ Login deve corresponder ao usuário com este CPF

**Resposta (200 OK):**
```
(sem conteúdo - apenas status 200)
```

**Códigos de Retorno:**
- `200` - OK (Senha atualizada)
- `400` - Bad Request (CPF/Login inválido)
- `404` - Not Found (Usuário não encontrado)
- `500` - Erro interno do servidor

---

### 6️⃣ POST /v1/usuarios/login - Validar Login

Valida as credenciais de login e senha do usuário.

**Requisição:**
```bash
curl -X POST "http://localhost:8099/v1/usuarios/login" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "cpf": "123.456.789-00",
    "login": "joao_silva",
    "senha": "senha_correta_123"
  }'
```

**Body (UsuarioSenhaDTO):**
```json
{
  "id": 1,
  "cpf": "123.456.789-00",
  "login": "joao_silva",
  "senha": "senha_correta_123"
}
```

**Validações:**
- ✅ CPF deve existir
- ✅ Login deve corresponder ao CPF
- ✅ Senha deve estar correta

**Resposta (204 No Content):**
```
(sem conteúdo - apenas status 204)
```

**Códigos de Retorno:**
- `204` - No Content (Credenciais válidas)
- `400` - Bad Request (Credenciais inválidas)
- `404` - Not Found (Usuário não encontrado)
- `500` - Erro interno do servidor

---

### 7️⃣ DELETE /v1/usuarios - Deletar Usuário

Deleta um usuário do sistema pelo CPF.

**Requisição:**
```bash
curl -X DELETE "http://localhost:8099/v1/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "123.456.789-00"
  }'
```

**Body (UsuarioAtualizarDTO):**
```json
{
  "cpf": "123.456.789-00"
}
```

**Validações:**
- ✅ CPF deve existir no banco

**Resposta (200 OK):**
```
(sem conteúdo - apenas status 200)
```

**Códigos de Retorno:**
- `200` - OK (Deletado com sucesso)
- `404` - Not Found (Usuário não encontrado)
- `500` - Erro interno do servidor

---

## 📊 Estrutura de DTOs e VOs

### UsuarioCriarDTO
```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string (formatted ou não)",
  "email": "string",
  "login": "string",
  "senha": "string",
  "endereco": "string (opcional)",
  "numero": 0,
  "flagProprietario": "true/false"
}
```

### UsuarioAtualizarDTO
```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "login": "string",
  "endereco": "string",
  "numero": 0,
  "flagProprietario": "true/false"
}
```

### UsuarioSenhaDTO
```json
{
  "id": 1,
  "cpf": "string",
  "login": "string",
  "senha": "string"
}
```

### UsuarioBuscarDTO
```json
{
  "nome": "string"
}
```

### UsuarioVO (Resposta)
```json
{
  "id": 1,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "login": "string",
  "endereco": "string",
  "numero": 0,
  "flagProprietario": "true/false",
  "dataAtualizacao": "2026-05-04T10:30:00"
}
```

---

## 🚨 Respostas de Erro Comuns

### 400 - Bad Request (Validação)
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "CPF informado é inválido"
}
```

### 404 - Not Found
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Usuário não encontrado"
}
```

### 409 - Conflict (Duplicado)
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Erro ao validar Usuário: já existe um usuário com essas credenciais de cadastro CPF, Email ou Login"
}
```

### 500 - Internal Server Error
```json
{
  "timestamp": "2026-05-04T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Erro ao gravar Usuário"
}
```

---

## 🔐 Headers Recomendados

```http
Content-Type: application/json
Accept: application/json
```

---

## ⏱️ Timeouts e Limites

| Recurso | Limite |
|---------|--------|
| Tamanho máximo do body | 1 MB |
| Timeout de conexão | 30 segundos |
| Timeout de requisição | 60 segundos |
| Paginação (limit máximo) | 100 registros |

---

## 🧪 Teste Rápido com cURL

```bash
# 1. Listar todos
curl http://localhost:8099/v1/usuarios

# 2. Criar usuário
curl -X POST http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"Test","cpf":"123.456.789-00","email":"test@test.com","login":"test","senha":"test123"}'

# 3. Atualizar
curl -X PUT http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"id":1,"nome":"Test Atualizado","cpf":"123.456.789-00","email":"test@test.com","login":"test","endereco":"Rua","numero":123,"flagProprietario":"false"}'

# 4. Buscar por nome
curl -X POST http://localhost:8099/v1/usuarios/nome \
  -H "Content-Type: application/json" \
  -d '{"nome":"Test"}'

# 5. Deletar
curl -X DELETE http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"cpf":"123.456.789-00"}'
```

---

## 📡 Swagger UI

Acesse a documentação interativa em:
```
http://localhost:8099/swagger-ui.html
```

Ou baixe a especificação OpenAPI:
```
http://localhost:8099/v3/api-docs
```

---

**Última atualização:** Maio de 2026


