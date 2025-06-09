# Java George Project

Este é um projeto Spring Boot que utiliza Java 17, Spring Data JPA, Spring MVC, e H2 Database.

## Requisitos

- Java 17 ou superior
- Maven

## Executando o Projeto

```bash
./mvnw spring-boot:run
```

O servidor será iniciado na porta 8080 com o contexto `/api`.

## H2 Database

Este projeto utiliza o H2 Database, um banco de dados em memória para desenvolvimento e testes.

### Acessando o Console H2

1. Inicie a aplicação
2. Abra o navegador e acesse: http://localhost:8080/api/h2-console
3. Configure a conexão com as seguintes informações:
   - JDBC URL: `jdbc:h2:mem:javageorgedb`
   - Username: `sa`
   - Password: (deixe em branco)
   - Driver Class: `org.h2.Driver`
4. Clique em "Connect" para acessar o console de administração do banco de dados

![H2 Console Login](https://raw.githubusercontent.com/h2database/h2database/master/h2/src/docsrc/images/console-2.png)

### Configurações do H2

As configurações do H2 Database estão definidas no arquivo `src/main/resources/application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:javageorgedb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Enable H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
```

## Swagger/OpenAPI Documentation

Este projeto utiliza SpringDoc OpenAPI (anteriormente Swagger) para documentação da API.

### Acessando a Documentação da API

1. Inicie a aplicação
2. Acesse a interface Swagger UI em: http://localhost:8080/api/swagger-ui/index.html
3. A definição OpenAPI está disponível em: http://localhost:8080/api/v3/api-docs

### Formatos disponíveis da documentação

- **Swagger UI**: http://localhost:8080/api/swagger-ui/index.html
- **JSON**: http://localhost:8080/api/v3/api-docs
- **YAML**: http://localhost:8080/api/v3/api-docs.yaml

## Endpoints da API

A API possui endpoints para gerenciamento de alunos, professores e disciplinas:

### Alunos
- `GET /api/alunos` - Lista todos os alunos
- `GET /api/alunos/{id}` - Busca um aluno por ID
- `GET /api/alunos/matricula/{matricula}` - Busca um aluno pela matrícula única
- `POST /api/alunos` - Cadastra um novo aluno
- `PUT /api/alunos/{id}` - Atualiza um aluno existente
- `DELETE /api/alunos/{id}` - Remove um aluno

### Professores
- `GET /api/professores` - Lista todos os professores
- `GET /api/professores/{id}` - Busca um professor por ID
- `GET /api/professores/especialidade/{especialidade}` - Lista professores por especialidade
- `POST /api/professores` - Cadastra um novo professor
- `PUT /api/professores/{id}` - Atualiza um professor existente
- `DELETE /api/professores/{id}` - Remove um professor

### Disciplinas
- `GET /api/disciplinas` - Lista todas as disciplinas
- `GET /api/disciplinas/{id}` - Busca uma disciplina por ID
- `GET /api/disciplinas/nome/{nome}` - Busca uma disciplina pelo nome
- `POST /api/disciplinas` - Cadastra uma nova disciplina
- `PUT /api/disciplinas/{id}` - Atualiza uma disciplina existente
- `DELETE /api/disciplinas/{id}` - Remove uma disciplina
- `POST /api/disciplinas/{disciplinaId}/prerequisitos/{preRequisitoId}` - Adiciona um pré-requisito
- `DELETE /api/disciplinas/{disciplinaId}/prerequisitos/{preRequisitoId}` - Remove um pré-requisito

## Características do Projeto

- Spring Boot 3.2.5
- Spring Data JPA para persistência de dados
- H2 Database para armazenamento em memória
- SpringDoc OpenAPI para documentação da API
- Lombok para redução de código boilerplate
- Jakarta Validation para validação de dados

## Problemas Comuns

### Porta 8080 já em uso

Se a porta 8080 já estiver em uso, você pode matá-la usando (Mac):

```bash
sudo lsof -i :8080   # Encontra o PID do processo usando a porta 8080
kill -9 [PID]        # Substitua [PID] pelo número encontrado acima
```

Alternativamente, você pode alterar a porta no arquivo `application.properties`:

```properties
server.port=8081     # Mude para qualquer porta disponível
```
