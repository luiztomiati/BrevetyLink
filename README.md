# BrevityLink API 🔗

O **BrevityLink** é uma API de encurtamento de URLs desenvolvida com **Java 21** e **Spring Boot 3**. O projeto foi desenhado para ser 
resiliente e de fácil deploy, utilizando **PostgreSQL** para persistência e **Docker** para uma orquestração simplificada de todo o 
ambiente de desenvolvimento e produção.

---

## 🚀 Tecnologias e Ferramentas

* **Java 21** (JDK)
* **Spring Boot 3** (Framework)
* **Maven** (Gerenciamento de dependências)
* **PostgreSQL** (Banco de dados relacional)
* **Flyway** (Migrações de banco de dados)
* **Docker & Docker Compose** (Containerização)
* **Spring Security & JWT** (Segurança e Autenticação)

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Docker.
* Git.

### Instalação e Execução via Docker

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/luiztomiati/BrevetyLink.git
    cd brevitylink
    ```

2.  **Configure as variáveis de ambiente:**
    Crie um arquivo `.env` na raiz do projeto:
    ```env
    DB_POSTGRES_DOCKER=brevitylink_db
    DB_POSTGRES_USER_DOCKER=postgres
    DB_POSTGRES_PASSWORD_DOCKER=sua_senha
    PORT_DOCKER=5432
    ```

3.  **Suba os serviços:**
    O comando abaixo irá realizar o *Multi-stage build* da aplicação, baixar as imagens necessárias e iniciar a API e o banco de dados:
    ```bash
    docker-compose up -d --build
    ```

A API estará disponível em: `http://localhost:8080`

---

## 📡 Endpoints da API (`LinkController`)

Abaixo estão as rotas principais disponíveis para interação com a API:

| Método    | Endpoint         | Descrição                                      | Exemplo de Payload / Path                                        |
| :-------  | :--------------- | :--------------------------------------------- | :--------------------------------------------------------------- |
| `POST`    | `/shorten`       | Encurta uma nova URL e a persiste no banco.    | `{"urlOriginal": "https://google.com"}`                          |
| `GET`     | `/{code}`        | Redireciona o usuário para a URL original.     | `/a1B2c3`                                                        |
| `DELETE`  | `/delete/{id}`   | Remove um link permanentemente do sistema.     | `/delete/1`                                                      |
| `POST`    | `/qrcodes`       | Gera Qrcode.                                   | `{"url": "https://google.com"}`                                  |
| `POST`    | `/auth/login`    | Realiza login com geração de token JWT.        | `{"email": "email@email.com","password":Senha@1234}`             |
| `POST`    | `/atttoken`      | Gera um novo par de tokens.                    | `{"refreshToken": "eyJhbGciOiJIUzI1..."}`                        |
| `POST`    | `/create/user`   | Cadastra um novo usuário no sistema.           | `{"name": "t", "email": "t@email.com", "password": "Senha@123"}` |
| `PUT`     | `/edit/user/{id}`| Atualiza os dados cadastrais de um usuário.    | `/edit/user/550e8400-e29b-41d4-a716-446655440000`                |
| `PUT`     | `/reset/password/user/{id}`| Altera a senha de um usuário específico. | `{"oldPassword": "...", "newPassword": "..."}`               |
| `DELETE`  | `/delete/user/{id}`| Remove um usuário permanentemente do sistema.| `/delete/user/550e8400-e29b-41d4-a716-446655440000`              |



## 🏗️ Estrutura de Configuração

O projeto utiliza **Spring Profiles** para alternar entre ambientes de forma transparente:

* **Perfil `dev`:** Configurado para desenvolvimento local (IDE). Lê as configurações do arquivo `application-dev.properties` (não versionado por segurança).
* **Perfil `docker`:** Ativado automaticamente via `docker-compose`. Utiliza variáveis de ambiente para conectar a API ao container do banco de dados `db`.

---

## 🧪 Próximos Passos (Roadmap)

### 🛡️ Qualidade e Robustez
- [X] Implementar validação de URLs no `@PostMapping` com Bean Validation (`@Valid`).
- [ ] Adicionar tratamento de exceções global com `@RestControllerAdvice`.
- [ ] Integrar testes automatizados (E2E) com **Cypress** ou **Playwright**.

### 🔐 Autenticação e Segurança
- [X] Implementar cadastro de usuários.
- [X] Implementar autenticação stateless utilizando **Spring Security** e **JWT**.

### 📈 Inteligência e Métricas
- [ ] Implementar coleta e exibição de estatísticas de utilização dos links (cliques, origem, data).

---
**Desenvolvido por Luiz** – Full Stack Developer.
