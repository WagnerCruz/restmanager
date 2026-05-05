# Análise e Solução do Erro de Injeção de Dependência - MapStruct

## 🔴 Erro Encontrado

```
APPLICATION FAILED TO START
***************************

Description:
Parameter 1 of constructor in com.raidstack.restmanager.services.UsuarioService 
required a bean of type 'com.raidstack.restmanager.mapper.UsuarioMapper' 
that could not be found.

Action:
Consider defining a bean of type 'com.raidstack.restmanager.mapper.UsuarioMapper' 
in your configuration.
```

---

## 📌 Causa Raiz

O Spring Boot **não conseguiu injetar o `UsuarioMapper`** do MapStruct porque:

1. **O processador de anotação MapStruct não foi executado** durante a compilação
2. A implementação gerada (`UsuarioMapperImpl`) não foi criada
3. Spring tentou injetar a interface diretamente, o que é impossível

---

## 🔧 Solução

### Opção 1: Limpeza e Recompilação (Recomendado)

```bash
# 1. Limpar completamente o projeto
mvn clean

# 2. Recompilar para ativar os processadores de anotação
mvn compile

# 3. Verificar se a classe foi gerada
ls target/generated-sources/annotations/com/raidstack/restmanager/mapper/

# 4. Fazer o build final
mvn package -DskipTests
```

### Opção 2: Verificar Configuração do pom.xml

Certifique-se de que o `pom.xml` contém:

```xml
<!-- 1. Propriedade -->
<properties>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
</properties>

<!-- 2. Dependência --> 
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
</dependency>

<!-- 3. Plugin com Annotation Processor Path -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${org.mapstruct.version}</version>
            </path>
            <!-- Lombok DEVE vir antes de MapStruct -->
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### Opção 3: Configuração Manual do UsuarioMapper (Se nada acima funcionar)

Como último recurso, você pode adicionar a anotação `@Component` diretamente (não recomendado):

```java
package com.raidstack.restmanager.mapper;

import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

// ABORDAGEM NÃO RECOMENDADA - Use apenas como último recurso
@Component
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // ... métodos
}
```

**⚠️ Nota:** Essa abordagem não é recomendada pois `@Component` + `@Mapper` pode causar conflitos.

---

## 🔍 Como Verificar se MapStruct foi Gerado Corretamente

### 1. Verificar Arquivo Gerado

```bash
# Navegar até o diretório gerado
cd target/generated-sources/annotations/com/raidstack/restmanager/mapper/

# Listar arquivos
ls -la

# Visualizar o arquivo gerado
cat UsuarioMapperImpl.java
```

**Esperado:** Você deve ver um arquivo `UsuarioMapperImpl.java` com:
- Anotação `@Component` (ou `@Service`, etc.)
- Implementação de todos os métodos da interface
- Métodos de mapeamento entre objetos

### 2. Exemplo de Arquivo Gerado Corretamente

```java
// Arquivo gerado automaticamente pelo MapStruct
// target/generated-sources/annotations/.../UsuarioMapperImpl.java

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioAtualizarDTO usuarioToUsuarioDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioAtualizarDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getLogin(),
            usuario.getEndereco(),
            usuario.getNumero(),
            usuario.isFlagProprietario()
        );
    }

    // ... outros métodos
}
```

### 3. Verificar Logs de Compilação

```bash
# Build com output detalhado
mvn clean compile -X | grep -i "mapstruct\|annotation"

# Procurar por mensagens do MapStruct
mvn clean compile 2>&1 | grep -i "processor\|generated"
```

---

## 🚀 Processo de Atualização após Resolver o Erro

Após resolver o erro, execute:

```bash
# 1. Limpeza total
mvn clean

# 2. Recompilação
mvn compile

# 3. Teste em ambiente de desenvolvimento
mvn spring-boot:run

# 4. Build final
mvn package -DskipTests

# 5. Executar com Docker
docker-compose build
docker-compose up -d
```

---

## 📊 Fluxograma de Diagnóstico

```
┌─────────────────────────────────────────────┐
│ Erro: UsuarioMapper Bean não encontrado    │
└──────────────────┬──────────────────────────┘
                   │
         ┌─────────┴─────────┐
         │                   │
         ▼                   ▼
    mvn clean              IDE IDE 
    mvn compile            cache?
         │                   │
         ├───────┬───────────┤
         │       │           │
         ▼       ▼           ▼
      ✓ Erro   ✗ Erro      ✓ Erro
      resolvido Continua  resolvido
         │       │           │
         │       ▼           │
         │    Verificar      │
         │    pom.xml        │
         │       │           │
         │   ┌───┴───┐       │
         │   │       │       │
         │   ▼       ▼       │
         │ ✓Conf ✗Conf      │
         │ Correto Errado   │
         │   │       │       │
         │   │       ▼       │
         │   │    Atualizar │
         │   │    pom.xml   │
         │   │       │       │
         └───┴───┬───┴───────┘
                 │
                 ▼
         Aplicação funcionando! ✅
```

---

## 💡 Dicas de Prevenção

### 1. Use IDE com Suporte a MapStruct
- **IntelliJ IDEA** (com plugin MapStruct)
- **Eclipse** (com plugin MapStruct)
- **Visual Studio Code** (com extensões Python/Java)

### 2. Manter Dependências Atualizadas

```xml
<!-- Sempre manter versões alinhadas -->
<org.mapstruct.version>1.6.3</org.mapstruct.version>
<org.projectlombok.version>1.18.44</org.projectlombok.version>
```

### 3. Adicionar Validação ao Build

```bash
# Verificar se MapStruct gerou corretamente
if [ ! -f "target/generated-sources/annotations/com/raidstack/restmanager/mapper/UsuarioMapperImpl.java" ]; then
    echo "❌ ERRO: MapStruct não gerou a implementação!"
    exit 1
else
    echo "✅ MapStruct gerou corretamente"
fi
```

### 4. Cache do IDE

Se usar IDE:
- **IntelliJ IDEA:** File → Invalidate Caches → Invalidate and Restart
- **Eclipse:** Project → Clean → Build All
- **VS Code:** Restart Java Extension

---

## 🎓 Entendendo MapStruct Melhor

### O que é MapStruct?

MapStruct é um **processador de anotações Java** que automatiza o mapeamento entre objetos em tempo de **compilação** (não runtime).

### Ciclo de Vida MapStruct

```
┌──────────────────────────────────────────────────┐
│        Interface UsuarioMapper (seu código)      │
│  @Mapper(componentModel = "spring")              │
│  public interface UsuarioMapper {                │
│      UsuarioVO usuarioToVO(Usuario usuario);     │
│  }                                               │
└──────────────────┬───────────────────────────────┘
                   │
         ┌─────────▼──────────┐
         │ mvn compile        │
         │ (seu comando)      │
         └─────────┬──────────┘
                   │
         ┌─────────▼──────────────────────┐
         │ Annotation Processing Phase:   │
         │ - Ler arquivo .class           │
         │ - Analisar interface           │
         │ - Gerar implementação          │
         └─────────┬──────────────────────┘
                   │
         ┌─────────▼──────────────────────────────────┐
         │ UsuarioMapperImpl.java (gerado)            │
         │ @Component                                 │
         │ public class UsuarioMapperImpl implements  │
         │     UsuarioMapper {                        │
         │   @Override                                │
         │   public UsuarioVO usuarioToVO(...) {...}  │
         │ }                                          │
         │                                            │
         │ Localização:                              │
         │ target/generated-sources/annotations/...   │
         └─────────┬──────────────────────────────────┘
                   │
         ┌─────────▼──────────┐
         │ Compilação final   │
         │ (gerado incluído)  │
         └─────────┬──────────┘
                   │
         ┌─────────▼──────────────────────┐
         │ Spring Boot inicializa:        │
         │ Vê @Component no .class gerado │
         │ Cria bean automático ✅        │
         │ Injeta em UsuarioService       │
         └────────────────────────────────┘
```

### Vantagens do MapStruct

| Aspecto | Vantagem |
|--------|---------|
| **Performance** | Sem reflexão em tempo de execução |
| **Type-safety** | Erros detectados em compilação |
| **Debugar** | Código gerado é fácil de debugar |
| **Documentação** | Código gerado é legível |
| **Customização** | Suporta mapeamentos complexos |
| **IDE Support** | Autocompletar e navegação funciona |

---

## 🔗 Links Úteis

- [MapStruct Documentação Oficial](https://mapstruct.org/)
- [MapStruct Spring Integration](https://mapstruct.org/documentation/stable/reference/html/#configuration)
- [Lombok + MapStruct Compatibility](https://projectlombok.org/resources/lombok-mapstruct-binding)
- [Spring Boot Annotation Processing](https://spring.io/guides/tutorials/spring-boot-kotlin/)

---

**Última atualização:** Maio de 2026


