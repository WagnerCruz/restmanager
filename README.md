
# RestManager - Gerenciador de Usuários REST API

Uma aplicação Spring Boot robusta para gerenciamento de usuários através de uma REST API, com persistência em banco de dados PostgreSQL e mapeamento automático de objetos utilizando MapStruct.

## 📋 Sumário

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Executando a Aplicação](#executando-a-aplicação)
- [Endpoints da API](#endpoints-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Solução de Problemas](#solução-de-problemas)

---

## 🎯 Visão Geral

O **RestManager** é um sistema de gerenciamento de usuários que fornece uma completa REST API para operações CRUD (Create, Read, Update, Delete) sobre usuários. A aplicação utiliza boas práticas de desenvolvimento com Spring Boot, como:

- **Injeção de Dependência** via Spring IoC
- **Validação de Dados** com Jakarta Validation
- **Mapeamento de Objetos** automatizado com MapStruct
- **Tratamento de Erros** consistente
- **Padrão DTO/VO** para transferência de dados
- **Design Pattern Repository** para acesso a dados

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida:

```
┌─────────────────────────────────────────────────────┐
│              Controllers (REST API)                  │
│         UsuarioController                            │
├─────────────────────────────────────────────────────┤
│              Services (Lógica de Negócio)            │
│         UsuarioService                              │
├─────────────────────────────────────────────────────┤
│    Repositories (Acesso a Dados)                     │
│    UsuarioRepository Interface & Implementation      │
├─────────────────────────────────────────────────────┤
│         Mappers (Transformação de Objetos)          │
│         UsuarioMapper (MapStruct)                    │
├─────────────────────────────────────────────────────┤
│    Database (PostgreSQL)                             │
│    Tabela: usuarios                                  │
└─────────────────────────────────────────────────────┘
```

### Camadas da Aplicação:

#### 1. **Controllers** 
Responsáveis por receber as requisições HTTP e retornar respostas.
- `UsuarioController`: Expõe os endpoints REST para gerenciar usuários

#### 2. **Services**
Contêm a lógica de negócio, validações e orquestração.
- `UsuarioService`: Implementa as operações de usuário com validações

#### 3. **Repositories**
Realizam o acesso aos dados através da abstração do banco de dados.
- `UsuarioRepository`: Interface para operações de banco de dados
- `UsuarioRepositoryImpl`: Implementação customizada usando Spring Data JDBC

#### 4. **Mappers (MapStruct)**
Automatizam a conversão entre diferentes representações dos dados.
- `UsuarioMapper`: Interface com mapeamentos automáticos gerados

#### 5. **Entities & DTOs/VOs**
Representam os dados em diferentes contextos.
- `Usuario`: Entidade JPA/JDBC que representa o usuário no banco
- `UsuarioCriarDTO`: DTO para criação de novo usuário
- `UsuarioAtualizarDTO`: DTO para atualização de usuário
- `UsuarioSenhaDTO`: DTO para atualização de senha
- `UsuarioVO`: Value Object para retorno de consultas

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Propósito |
|---|---|---|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 4.0.5 | Framework web |
| **Spring Data JDBC** | 4.0.4 | Acesso a dados |
| **PostgreSQL** | 15 | Banco de dados relacional |
| **MapStruct** | 1.6.3 | Mapeamento automático de objetos |
| **Lombok** | 1.18.44 | Redução de boilerplate |
| **Jakarta Validation** | 3.1.1 | Validação de dados |
| **OpenAPI/Swagger** | 2.6.0 | Documentação de API |
| **Docker** | - | Containerização |

---

## 📦 Pré-requisitos

### Necessário:
- **Java 21+** instalado
- **Maven 3.8+** instalado
- **PostgreSQL 15+** (se executar localmente)
- **Docker & Docker Compose** (se executar via containers)

### Verificar Instalações:

```bash
java -version
mvn -version
docker --version
docker-compose --version
```

---

## 🚀 Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd restmanager
```

### 2. Configurar Variáveis de Ambiente

Copie o arquivo `.env.example` (se existir) ou crie um arquivo `.env`:

```bash
# Banco de Dados
DB_PORT=5432
DB_USER=admin
DB_PASSWORD=b15tr0
DB_NAME=restrntgerencia

# Aplicação
API_PORT=8099
API_NAME=restmanager
```

### 3. Compilar o Projeto

```bash
mvn clean compile
```

**Nota sobre MapStruct:** O MapStruct utiliza o Java Annotation Processing para gerar automaticamente as implementações dos mappers em tempo de compilação. Ao executar `mvn compile`, as classes geradas são criadas em `target/generated-sources/annotations/`.

---

## 🎮 Executando a Aplicação

### Opção 1: Execução Local (com Banco de Dados Externo)

#### Pré-requisito:
- PostgreSQL iniciado e acessível

#### Passos:

1. **Build da Aplicação:**
```bash
mvn clean package -DskipTests
```

2. **Executar a Aplicação:**
```bash
java -jar target/app.jar
```

3. **Acessar a API:**
```bash
http://localhost:8099/v1/usuarios
```

4. **Documentação OpenAPI/Swagger:**
```bash
http://localhost:8099/swagger-ui.html
```

---

### Opção 2: Execução com Docker Compose (Recomendado)

#### Passos:

1. **Construir a Imagem Docker:**
```bash
docker-compose build
```

2. **Iniciar os Serviços:**
```bash
docker-compose up -d
```

   Este comando irá:
   - Criar um banco de dados PostgreSQL em um container
   - Compilar e executar a aplicação em outro container
   - Configurar a rede entre os containers

3. **Verificar os Logs:**
```bash
docker-compose logs -f back-end
```

4. **Acessar a API:**
```bash
http://localhost:8099/v1/usuarios
```

5. **Parar os Serviços:**
```bash
docker-compose down
```

6. **Limpar Volumes (Remover Dados do Banco):**
```bash
docker-compose down -v
```

---

### Opção 3: Desenvolvimento com Maven

```bash
mvn spring-boot:run
```

---

## 📡 Endpoints da API

### Listar Todos os Usuários

```http
GET /v1/usuarios
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
  }
]
```

---

### Buscar Usuário por Nome

```http
POST /v1/usuarios/nome
Content-Type: application/json

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

---

### Criar Novo Usuário

```http
POST /v1/usuarios
Content-Type: application/json

{
  "id": 2,
  "nome": "Maria Santos",
  "cpf": "987.654.321-00",
  "email": "maria@example.com",
  "login": "maria_santos",
  "senha": "senha123",
  "endereco": "Rua B, 200",
  "numero": 200,
  "flagProprietario": "false"
}
```

**Validações Aplicadas:**
- `id`: Obrigatório
- `nome`: Obrigatório
- `cpf`: Obrigatório, deve ser um CPF válido
- `email`: Deve ser um email válido
- `login`: Obrigatório
- `senha`: Obrigatória

**Resposta (201 Created):**
```
Sem conteúdo (Body vazio)
```

---

### Atualizar Usuário

```http
PUT /v1/usuarios
Content-Type: application/json

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

**Resposta (200 OK):**
```
Sem conteúdo (Body vazio)
```

---

### Atualizar Senha do Usuário

```http
PUT /v1/usuarios/senha
Content-Type: application/json

{
  "id": 1,
  "cpf": "123.456.789-00",
  "login": "joao_silva",
  "senha": "nova_senha_123"
}
```

**Resposta (200 OK):**
```
Sem conteúdo (Body vazio)
```

---

### Validar Login e Senha

```http
POST /v1/usuarios/login
Content-Type: application/json

{
  "id": 1,
  "cpf": "123.456.789-00",
  "login": "joao_silva",
  "senha": "senha123"
}
```

**Resposta (204 No Content):**
```
Sem conteúdo (Body vazio)
```

---

### Deletar Usuário

```http
DELETE /v1/usuarios
Content-Type: application/json

{
  "cpf": "123.456.789-00"
}
```

**Resposta (200 OK):**
```
Sem conteúdo (Body vazio)
```

---

## 📁 Estrutura do Projeto

```
restmanager/
├── src/
│   ├── main/
│   │   ├── java/com/raidstack/restmanager/
│   │   │   ├── RestmanagerApplication.java         # Classe principal
│   │   │   ├── controllers/
│   │   │   │   ├── UsuarioController.java          # Endpoints REST
│   │   │   │   └── handlers/                       # Tratadores de erros
│   │   │   ├── services/
│   │   │   │   ├── UsuarioService.java             # Lógica de negócio
│   │   │   │   └── exceptions/                     # Exceções customizadas
│   │   │   ├── repositories/
│   │   │   │   ├── UsuarioRepository.java          # Interface
│   │   │   │   └── impl/
│   │   │   │       └── UsuarioRepositoryImpl.java  # Implementação
│   │   │   ├── mapper/
│   │   │   │   └── UsuarioMapper.java              # MapStruct Mapper
│   │   │   ├── entity/
│   │   │   │   └── Usuario.java                    # Entidade JPA/JDBC
│   │   │   ├── dtos/
│   │   │   │   ├── UsuarioCriarDTO.java
│   │   │   │   ├── UsuarioAtualizarDTO.java
│   │   │   │   ├── UsuarioSenhaDTO.java
│   │   │   │   └── UsuarioBuscarDTO.java
│   │   │   └── vo/
│   │   │       └── UsuarioVO.java                  # Value Object
│   │   └── resources/
│   │       ├── application.properties              # Configurações
│   │       ├── scripts/
│   │       │   └── (scripts SQL)
│   │       └── static/
│   └── test/
│       └── java/com/raidstack/restmanager/
│           └── LocatechApplicationTests.java       # Testes
├── docker-compose.yml                              # Configuração Docker
├── Dockerfile                                      # Imagem Docker
├── pom.xml                                         # Maven POM
├── .env                                            # Variáveis de ambiente
└── README.md                                       # Este arquivo
```

---

## 🔧 Componentes Detalhados

### Entity - Usuario

A classe `Usuario` representa a entidade persistida no banco de dados:

```java
@Table("usuarios")
public class Usuario {
    @Id
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private int numero;
    private boolean flagProprietario;
    private LocalDateTime dataAtualizacao;
}
```

### DTOs (Data Transfer Objects)

DTOs são utilizados para transferência de dados entre a API e a aplicação:

- **UsuarioCriarDTO**: Recebe dados para criação de novo usuário
- **UsuarioAtualizarDTO**: Recebe dados para atualização de usuário
- **UsuarioSenhaDTO**: Recebe dados para atualização de senha e validação de login
- **UsuarioBuscarDTO**: Recebe parâmetro de busca

### VOs (Value Objects)

Value Objects são utilizados para retornar dados para o cliente:

- **UsuarioVO**: Retorna dados do usuário sem informações sensíveis (ausência de senha)

### Service - UsuarioService

Implementa toda a lógica de negócio:

```java
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    
    // Operações CRUD e validações
}
```

**Principais Métodos:**
- `findById(long id)`: Buscar usuário por ID
- `findByLogin(String login)`: Buscar usuário por login
- `findByNome(String nome)`: Buscar usuário por nome
- `findAll()`: Listar todos os usuários
- `criarUsuario(UsuarioCriarDTO)`: Criar novo usuário
- `atualizarUsuario(UsuarioAtualizarDTO)`: Atualizar usuário
- `deletarUsuario(UsuarioAtualizarDTO)`: Deletar usuário
- `atualizarSenhaUsuario(UsuarioSenhaDTO)`: Atualizar senha
- `validarLoginUsuario(UsuarioSenhaDTO)`: Validar login e senha

### Repository - UsuarioRepository

Interface que define as operações de acesso a dados:

```java
public interface UsuarioRepository {
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorLogin(String login);
    Optional<Usuario> buscarPorNome(String nome);
    List<Usuario> buscarTodos(int size, int offset);
    Integer criarUsuario(Usuario usuario);
    Integer atualizarUsuario(Usuario usuario);
    // ... outros métodos
}
```

### Mapper - UsuarioMapper (MapStruct)

O **MapStruct** é uma ferramenta de geração de código que cria automaticamente implementações de interfaces mappers em tempo de compilação.

```java
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioAtualizarDTO usuarioToUsuarioDTO(Usuario usuario);
    Usuario usuarioDTOToUsuario(UsuarioAtualizarDTO usuarioDTO);
    Usuario usuarioCriarDTOToUsuario(UsuarioCriarDTO usuarioDTO);
    UsuarioVO usuarioToUsuarioVO(Usuario usuario);
}
```

**MapStruct vantagens:**
- ✅ Tipo-seguro com verificação em tempo de compilação
- ✅ Sem reflexão em tempo de execução (performático)
- ✅ Gera código simples e legível
- ✅ Suporta customizações
- ✅ Integração transparente com Spring (`componentModel = "spring"`)

**Como funciona:**
1. Durante `mvn compile`, o processador de anotação MapStruct lê a interface
2. Gera uma implementação em `target/generated-sources/annotations/`
3. A classe gerada é anotada com `@Component` (devido a `componentModel = "spring"`)
4. Spring injeta automaticamente no `UsuarioService`

### Controller - UsuarioController

Expõe os endpoints REST:

```java
@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    
    @GetMapping
    public ResponseEntity<List<UsuarioVO>> getUsuarios() { ... }
    
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioCriarDTO usuarioDTO) { ... }
    
    // ... outros endpoints
}
```

---

## ⚙️ Configurações Importantes

### application.properties

```properties
# Nome e porta da aplicação
spring.application.name=${API_NAME_DEFAULT:restmanager}
server.port=${API_PORT_DEFAULT:8099}

# Conexão com banco de dados PostgreSQL
spring.datasource.url=jdbc:postgresql://${POSTGRES_DB_HOST:localhost}:${POSTGRES_DB_PORT:5432}/${POSTGRES_DB_NAME:restrntgerencia}
spring.datasource.username=${POSTGRES_DB_USER:admin}
spring.datasource.password=${POSTGRES_DB_PASSWORD:b15tr0}
spring.jpa.hibernate.ddl-auto=update
```

### MapStruct no pom.xml

```xml
<properties>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
</properties>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
</dependency>

<!-- No build, configurar o processador de anotação -->
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${org.mapstruct.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

---

## 🐛 Solução de Problemas

### Erro: "Bean of type 'UsuarioMapper' could not be found"

**Causa:** O MapStruct não gerou a implementação corretamente.

**Solução:**
```bash
# Limpar o projeto completamente
mvn clean

# Recompilar
mvn clean compile

# Fazer o build novamente
mvn clean package -DskipTests
```

### Erro: "Conexão recusada com PostgreSQL"

**Causa:** PostgreSQL não está rodando ou as credenciais estão erradas.

**Solução (se usando Docker):**
```bash
docker-compose down -v
docker-compose up -d
```

**Solução (local):**
```bash
# Verificar se PostgreSQL está rodando
sudo systemctl status postgresql

# Se não estiver, iniciar
sudo systemctl start postgresql

# Atualizar as variáveis de ambiente em .env ou application.properties
```

### Erro: "Unmapped target properties"

**Causa:** MapStruct aguarda que todas as propriedades sejam mapeadas.

**Solução:** Configure explicitamente ignorar propriedades não mapeadas:
```java
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "dataAtualizacao", ignore = true)
    Usuario usuarioCriarDTOToUsuario(UsuarioCriarDTO usuarioDTO);
}
```

### Porta 8099 já em uso

**Causa:** Outra aplicação está usando a porta.

**Solução:**
```bash
# Alterar porta em .env ou application.properties
API_PORT=8100

# Ou matar o processo
lsof -i :8099
kill -9 <PID>
```

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MapStruct Documentation](https://mapstruct.org/)
- [Jakarta Validation](https://jakarta.ee/specifications/validation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)

---

## 📝 Licença

Este projeto é parte do Tech Challenge da FIAP.

---

## 👥 Autor

Desenvolvido como parte do programa de Pós-graduação da FIAP.

---

**Última atualização:** Maio de 2026

