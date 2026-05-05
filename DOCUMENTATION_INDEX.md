# Documentação do RestManager - Índice

Bem-vindo à documentação do projeto **RestManager**! Esta é uma aplicação Spring Boot para gerenciamento de usuários através de REST API.

## 📚 Documentação Completa

### 1. 🚀 [README.md](README.md) - Comece Aqui!
**Leitura Obrigatória - Guia Principal do Projeto**

Contém:
- ✅ Visão geral do projeto
- ✅ Arquitetura em camadas
- ✅ Tecnologias utilizadas
- ✅ Pré-requisitos de instalação
- ✅ Como executar localmente
- ✅ Como executar com Docker Compose
- ✅ Estrutura completa do projeto
- ✅ Explicação detalhada de cada componente (Controllers, Services, Repositories, Mappers, DTOs, VOs)
- ✅ Solução de problemas comuns

**Quando ler:**
- Primeiro acesso ao projeto
- Antes de começar a executar
- Para entender a arquitetura geral

---

### 2. 🔧 [API_REFERENCE.md](API_REFERENCE.md) - Referência Rápida dos Endpoints
**Guia de Endpoints - Mantenha à Mão!**

Contém:
- ✅ Lista completa de todos os endpoints (7 endpoints)
- ✅ Exemplos de requisição com cURL
- ✅ Estruturas de DTOs e VOs
- ✅ Códigos de retorno esperados
- ✅ Exemplos de respostas de sucesso e erro
- ✅ Validações aplicadas em cada endpoint
- ✅ Testes rápidos para testar a API

**Quando ler:**
- Desenvolvendo novo endpoint
- Testando a API manualmente
- Consultando parâmetros de uma requisição

---

### 3. 💻 [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) - Guia Prático de Desenvolvimento
**Como Desenvolver Novas Features**

Contém:
- ✅ Setup inicial do ambiente
- ✅ Workflow de desenvolvimento
- ✅ Como adicionar novos endpoints (passo a passo)
- ✅ Como adicionar novos mappers MapStruct
- ✅ Como testar a aplicação (cURL, Postman, JUnit, Docker)
- ✅ Boas práticas de desenvolvimento
- ✅ Padrões de nomenclatura
- ✅ Checklist para novas features
- ✅ Debug com IntelliJ IDEA
- ✅ Troubleshooting comum

**Quando ler:**
- Antes de começar nova feature
- Quando adicionar novo endpoint
- Para aprender boas práticas

---

### 4. 🐛 [MAPSTRUCT_ERROR_SOLUTION.md](MAPSTRUCT_ERROR_SOLUTION.md) - Solução do Erro de Injeção de Dependência
**Entendendo e Resolvendo Erros do MapStruct**

Contém:
- ✅ Análise do erro "Bean not found"
- ✅ Causa raiz do problema
- ✅ Solução em 3 opções diferentes
- ✅ Como verificar se MapStruct foi gerado corretamente
- ✅ Exemplos de arquivo gerado
- ✅ Fluxograma de diagnóstico
- ✅ Dicas de prevenção
- ✅ Explicação profunda sobre MapStruct
- ✅ Ciclo de vida do MapStruct

**Quando ler:**
- Se encontrar erro relacionado a injeção de dependência
- Para entender como MapStruct funciona
- Para aprender a resolver problemas similares

---

## 🎯 Guia de Leitura Recomendado

### 👶 Primeira Vez (Setup Inicial)
```
1. README.md - Visão geral e setup
   ↓
2. Docker → docker-compose up -d
   ↓
3. API_REFERENCE.md → Testar endpoints com cURL
   ↓
4. Explore a aplicação em http://localhost:8099/swagger-ui.html
```

### 👨‍💻 Desenvolvedor (Novo Endpoint)
```
1. DEVELOPMENT_GUIDE.md → Secção "Adicionando Novos Endpoints"
   ↓
2. Implementar Controller → Service → Repository
   ↓
3. API_REFERENCE.md → Testar novo endpoint
   ↓
4. Commit & Push
```

### 🔧 Troubleshooting (Erro)
```
1. README.md → Secção "Solução de Problemas"
   ↓
2. Se erro for sobre MapStruct → MAPSTRUCT_ERROR_SOLUTION.md
   ↓
3. DEVELOPMENT_GUIDE.md → Secção "Troubleshooting Comum"
```

### 🎓 Aprendizado (Entender Tudo)
```
1. README.md (Completo)
   ↓
2. MAPSTRUCT_ERROR_SOLUTION.md (Entender MapStruct)
   ↓
3. DEVELOPMENT_GUIDE.md (Prática)
   ↓
4. API_REFERENCE.md (Reference)
```

---

## 📊 Estrutura de Documentação

```
restmanager/
├── README.md                          # 📌 PRINCIPAL - Comece aqui
├── API_REFERENCE.md                   # 📡 Endpoints e DTOs
├── DEVELOPMENT_GUIDE.md               # 💻 Como desenvolver
├── MAPSTRUCT_ERROR_SOLUTION.md        # 🐛 Solução de erros
├── DOCUMENTATION_INDEX.md             # 📚 Este arquivo
├── pom.xml                            # Maven config
├── docker-compose.yml                 # Docker config
└── src/
    └── main/java/com/raidstack/restmanager/
        ├── RestmanagerApplication.java
        ├── controllers/
        ├── services/
        ├── repositories/
        ├── mapper/
        ├── entity/
        ├── dtos/
        └── vo/
```

---

## 🚀 Comandos Essenciais

### Compilar Projeto
```bash
mvn clean compile
```

### Executar Localmente
```bash
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package -DskipTests
```

### Executar com Docker
```bash
docker-compose build
docker-compose up -d
```

### Ver Logs
```bash
docker-compose logs -f back-end
```

### Parar Docker
```bash
docker-compose down
```

---

## 🛠️ Stack Tecnológico

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.5 | Framework web |
| Spring Data JDBC | 4.0.4 | ORM |
| PostgreSQL | 15 | Banco de dados |
| MapStruct | 1.6.3 | Mapeamento de objetos |
| Lombok | 1.18.44 | Redução boilerplate |
| Jakarta Validation | 3.1.1 | Validação |
| OpenAPI/Swagger | 2.6.0 | Documentação |
| Docker | Latest | Containerização |

---

## 🔗 URLs Importantes

| Recurso | URL |
|---------|-----|
| API REST | http://localhost:8099/v1/usuarios |
| Swagger UI | http://localhost:8099/swagger-ui.html |
| OpenAPI Spec | http://localhost:8099/v3/api-docs |
| Health Check | http://localhost:8099/actuator/health |

---

## ❓ Perguntas Frequentes

### Como parar de receber "Bean not found"?
→ Ver [MAPSTRUCT_ERROR_SOLUTION.md](MAPSTRUCT_ERROR_SOLUTION.md)

### Como adicionar novo endpoint?
→ Ver [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) seção "Adicionando Novos Endpoints"

### Como testar a API?
→ Ver [API_REFERENCE.md](API_REFERENCE.md) ou [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) seção "Testando a Aplicação"

### Como executar com Docker?
→ Ver [README.md](README.md) seção "Executando a Aplicação"

### Como debugar?
→ Ver [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) seção "Debugging com IntelliJ IDEA"

### Qual é a porta da aplicação?
→ Porta padrão: `8099` (configurável em `.env`)

### Como conectar ao banco de dados?
→ Usar container: `docker exec -it restmanager-postgres-db psql -U admin -d restrntgerencia`

---

## 📞 Suporte

Se tiver dúvidas:

1. **Consultar documentação** - Comece pelo README.md
2. **Buscar em API_REFERENCE.md** - Para perguntas sobre endpoints
3. **Ler DEVELOPMENT_GUIDE.md** - Para questões técnicas
4. **Revisar MAPSTRUCT_ERROR_SOLUTION.md** - Para erros de compilação

---

## 📝 Histórico de Atualizações

| Data | Versão | Mudanças |
|------|--------|----------|
| 2026-05-04 | 1.0 | Documentação completa criada |

---

## 🎓 Recursos Externos

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [MapStruct Documentation](https://mapstruct.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [Git Workflow Best Practices](https://git-scm.com/book/en/v2)

---

**Última atualização:** Maio de 2026

*Para começar, leia [README.md](README.md) →*


