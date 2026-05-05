# Guia de Desenvolvimento - RestManager

Este documento fornece um guia prático para desenvolvimento e manutenção do projeto RestManager.

## 📋 Índice

1. [Setup Inicial](#setup-inicial)
2. [Workflow de Desenvolvimento](#workflow-de-desenvolvimento)
3. [Adicionando Novos Endpoints](#adicionando-novos-endpoints)
4. [Adicionando Novos Mappers](#adicionando-novos-mappers)
5. [Testando a Aplicação](#testando-a-aplicação)
6. [Boas Práticas](#boas-práticas)

---

## 🚀 Setup Inicial

### 1. Clonar e Configurar

```bash
# Clonar repositório
git clone <url-do-repositorio>
cd restmanager

# Criar arquivo .env na raiz
cat > .env << 'EOF'
DB_PORT=5432
DB_USER=admin
DB_PASSWORD=b15tr0
DB_NAME=restrntgerencia
API_PORT=8099
API_NAME=restmanager
EOF

# Dar permissão de execução aos scripts
chmod +x mvnw
```

### 2. Instalação de Dependências

```bash
# Download de todas as dependências Maven
mvn dependency:resolve

# Compilar projeto
mvn clean compile
```

### 3. Configurar IDE (IntelliJ IDEA)

```
1. File → Open → Selecionar pasta restmanager
2. Deixar IDE indexar os arquivos
3. Se necessário, marcar o folder "target/generated-sources/annotations" como Sources Root
4. Bilder → Rebuild Project
5. Run → Run 'RestmanagerApplication'
```

---

## 💻 Workflow de Desenvolvimento

### Fluxo Típico de Desenvolvimento

```
┌─────────────────┐
│ Criar branch    │ git checkout -b feature/nome
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ Fazer alterações no código          │
│ - Services                          │
│ - Controllers                       │
│ - Entities/DTOs                     │
└────────┬────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ Compilar com MapStruct              │
│ mvn clean compile                   │
└────────┬────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ Testar localmente                    │
│ mvn spring-boot:run                  │
│ ou                                   │
│ java -jar target/app.jar             │
└────────┬─────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ Fazer commit                         │
│ git add -A                           │
│ git commit -m "Feature: ..."         │
└────────┬─────────────────────────────┘
         │
         ▼
┌──────────────────────────────────────┐
│ Push e Pull Request                  │
│ git push origin feature/nome         │
│ Criar PR no GitHub                   │
└──────────────────────────────────────┘
```

### Executar em Modo Desenvolvimento

**Terminal 1 - Banco de Dados:**
```bash
docker-compose up db_postgresql -d
```

**Terminal 2 - Aplicação:**
```bash
mvn spring-boot:run
```

Ou no IntelliJ IDEA:
```
Run → Run 'RestmanagerApplication'
ou
Shift + F10 (Windows/Linux)
Ctrl + R (macOS)
```

---

## ➕ Adicionando Novos Endpoints

### Exemplo: Adicionar Endpoint para Buscar por CPF

#### 1. Atualizar o Repository (Interface)

```java
// File: repositories/UsuarioRepository.java
public interface UsuarioRepository {
    // ... outros métodos ...
    
    // NOVO: Buscar por CPF
    Optional<Usuario> buscarPorCpf(String cpf);
}
```

#### 2. Implementar no Repository

```java
// File: repositories/impl/UsuarioRepositoryImpl.java
@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {
    
    // ... código existente ...
    
    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        try {
            Usuario usuario = jdbcTemplate.queryForObject(
                sql, 
                new UsuarioRowMapper(), 
                cpf
            );
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
```

#### 3. Adicionar Método no Service

```java
// File: services/UsuarioService.java
@Service
public class UsuarioService {
    // ... código existente ...
    
    public UsuarioVO findByCpf(String cpf) {
        Optional<Usuario> usuario = this.usuarioRepository.buscarPorCpf(cpf);
        return usuario.map(usuarioMapper::usuarioToUsuarioVO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
```

#### 4. Criar DTO para a Requisição

```java
// File: dtos/UsuarioBuscaCpfDTO.java
import java.io.Serializable;

public record UsuarioBuscaCpfDTO(
        @CPF(message = "CPF informado é inválido")
        @NotNull(message = "CPF é obrigatório")
        String cpf
) implements Serializable {
}
```

#### 5. Adicionar Endpoint no Controller

```java
// File: controllers/UsuarioController.java
@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
    
    // ... código existente ...
    
    @PostMapping("/cpf")
    public ResponseEntity<UsuarioVO> getUsuarioByCpf(@Valid @RequestBody UsuarioBuscaCpfDTO cpfDTO) {
        LOGGER.info("Buscando usuário por CPF: {}", cpfDTO.cpf());
        UsuarioVO usuarioVO = usuarioService.findByCpf(cpfDTO.cpf());
        LOGGER.info("Usuário encontrado com sucesso");
        return ResponseEntity.ok(usuarioVO);
    }
}
```

#### 6. Compilar e Testar

```bash
# Compilar
mvn clean compile

# Testar o novo endpoint
curl -X POST http://localhost:8099/v1/usuarios/cpf \
  -H "Content-Type: application/json" \
  -d '{"cpf": "123.456.789-00"}'
```

---

## 🗺️ Adicionando Novos Mappers

### Exemplo: Adicionar Novo VO (UsuarioSimplificadoVO)

#### 1. Criar Nova Classe VO

```java
// File: vo/UsuarioSimplificadoVO.java
public record UsuarioSimplificadoVO(
        Long id,
        String nome,
        String email
) {
}
```

#### 2. Adicionar Método no Mapper

```java
// File: mapper/UsuarioMapper.java
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    
    // ... métodos existentes ...
    
    // NOVO: Mapear para VO simplificado
    UsuarioSimplificadoVO usuarioToUsuarioSimplificadoVO(Usuario usuario);
}
```

#### 3. Compilar

```bash
mvn clean compile
# MapStruct gera automaticamente em target/generated-sources/annotations/
```

#### 4. Usar no Service

```java
// File: services/UsuarioService.java
public UsuarioSimplificadoVO findByNomeSimplificado(String nome) {
    Optional<Usuario> usuario = this.usuarioRepository.buscarPorNome(nome);
    return usuario.map(usuarioMapper::usuarioToUsuarioSimplificadoVO)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
}
```

---

## 🧪 Testando a Aplicação

### 1. Testes Manuais com cURL

```bash
# Listar todos os usuários
curl -X GET http://localhost:8099/v1/usuarios

# Criar usuário
curl -X POST http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "email": "joao@example.com",
    "login": "joao_silva",
    "senha": "senha123",
    "endereco": "Rua A, 100",
    "numero": 100,
    "flagProprietario": "true"
  }'

# Atualizar usuário
curl -X PUT http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "nome": "João Silva Atualizado",
    "cpf": "123.456.789-00",
    "email": "joao_novo@example.com",
    "login": "joao_silva_novo",
    "endereco": "Rua B, 200",
    "numero": 200,
    "flagProprietario": "true"
  }'

# Deletar usuário
curl -X DELETE http://localhost:8099/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"cpf": "123.456.789-00"}'
```

### 2. Testes com Postman

1. Importar arquivo: `Fase 1 - Tech Challenge - RestManager.postman_collection.json`
2. Configurar variáveis de ambiente
3. Executar as requisições

### 3. Testes com JUnit

```java
// File: src/test/java/.../UsuarioControllerTest.java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsuarioControllerTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void testGetAllUsuarios() {
        String url = "http://localhost:" + port + "/v1/usuarios";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    
    @Test
    void testCreateUsuario() {
        String url = "http://localhost:" + port + "/v1/usuarios";
        UsuarioCriarDTO dto = new UsuarioCriarDTO(
            1, "Test User", "123.456.789-00", 
            "test@example.com", "testuser", "password"
        );
        
        ResponseEntity<Void> response = restTemplate.postForEntity(url, dto, Void.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
```

### 4. Testes de Integração com Docker

```bash
# Build e teste com Docker
docker-compose build

# Levantar stack completa
docker-compose up -d

# Testar endpoints
curl http://localhost:8099/v1/usuarios

# Ver logs
docker-compose logs -f back-end

# Derrubar stack
docker-compose down
```

---

## ✅ Boas Práticas

### 1. Validação de Dados

Sempre use anotações de validação em DTOs:

```java
public record UsuarioCriarDTO(
    @NotNull(message = "ID é obrigatório")
    Integer id,
    
    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode ser vazio")
    String nome,
    
    @Email(message = "Email inválido")
    String email,
    
    @CPF(message = "CPF inválido")
    String cpf
) {
}
```

### 2. Tratamento de Exceções

Use exceções customizadas:

```java
// services/exceptions/
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

// handlers/
@RestControllerAdvice
public class ExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

### 3. Logging

Use SLF4J consistentemente:

```java
@Service
public class UsuarioService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);
    
    public void criarUsuario(UsuarioCriarDTO dto) {
        LOGGER.info("Iniciando criação de usuário: {}", dto.login());
        try {
            // ... lógica ...
            LOGGER.info("Usuário criado com sucesso: {}", dto.login());
        } catch (Exception e) {
            LOGGER.error("Erro ao criar usuário: {}", dto.login(), e);
            throw e;
        }
    }
}
```

### 4. Estrutura de Commits

```
feat: adicionar novo endpoint para buscar por CPF
fix: corrigir validação de email
refactor: reorganizar lógica de validação
docs: atualizar README com novos endpoints
test: adicionar testes para UsuarioController
```

### 5. Padrão de Nomenclatura

| Elemento | Padrão | Exemplo |
|----------|--------|---------|
| **Classes** | PascalCase | `UsuarioService`, `UsuarioMapper` |
| **Métodos** | camelCase | `criarUsuario()`, `buscarPorId()` |
| **Constantes** | UPPER_SNAKE_CASE | `MAX_SIZE`, `DB_PORT` |
| **DTOs** | `{Domain}+{Operation}DTO` | `UsuarioCriarDTO` |
| **VOs** | `{Domain}VO` | `UsuarioVO` |
| **Exceptions** | `{Domain}+{Type}Exception` | `ResourceNotFoundException` |

### 6. Dependências Seguras

Manter arquivo `.dependabot.yml`:

```yaml
version: 2
updates:
  - package-ecosystem: maven
    directory: "/"
    schedule:
      interval: "weekly"
    pull-requests:
      prefix: "deps:"
```

---

## 📝 Checklist para Nova Feature

- [ ] Criar branch: `git checkout -b feature/descricao`
- [ ] Escrever testes automatizados
- [ ] Implementar Feature (Controller → Service → Repository)
- [ ] Adicionar validações em DTOs
- [ ] Adicionar logs apropriados
- [ ] Compilar: `mvn clean compile`
- [ ] Testar localmente: `mvn spring-boot:run`
- [ ] Testar em Docker: `docker-compose up -d`
- [ ] Fazer commit: `git commit -m "feat: descricao"`
- [ ] Push: `git push origin feature/descricao`
- [ ] Criar Pull Request
- [ ] Revisão de código
- [ ] Merge na branch main/develop

---

## 🔍 Debugging com IntelliJ IDEA

### Adicionar Breakpoint

1. Clicar na linha de código
2. Pressionar `Ctrl+F8` (Windows/Linux) ou `Cmd+F8` (macOS)
3. Ponto vermelho aparece na linha

### Executar em Debug Mode

1. `Shift+F9` (Windows/Linux) ou `Ctrl+D` (macOS)
2. Navegadores aparecem quando atingir breakpoint
3. `F8` para executar linha
4. `F7` para entrar em método

### Inspecionar Variáveis

- Colocar mouse sobre variável
- Ou usar painel "Variables" no debug

---

## 🚨 Troubleshooting Comum

### Maven: "Bad artifact coordinates"
```bash
mvn clean install
```

### Porta já em uso
```bash
# Encontrar processo usando porta 8099
lsof -i :8099

# Matar processo
kill -9 <PID>
```

### Erro de Banco de Dados
```bash
# Conectar ao banco e verificar tabela
docker exec -it restmanager-postgres-db psql -U admin -d restrntgerencia -c "\dt"
```

### Cache do IDE
```bash
# IntelliJ
File → Invalidate Caches → Invalidate and Restart

# Eclipse
Project → Clean All
```

---

## 📚 Recursos Úteis

- [Spring Boot Guides](https://spring.io/guides)
- [MapStruct Documentation](https://mapstruct.org/)
- [Jakarta Validation](https://jakarta.ee/specifications/validation/)
- [Git Workflow](https://git-scm.com/book/en/v2)
- [RESTful API Best Practices](https://restfulapi.net/)

---

**Última atualização:** Maio de 2026


