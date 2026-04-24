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

## 📡 Endpoints da API

Abaixo estão as rotas principais disponíveis. A documentação interativa completa pode ser acessada via **Swagger UI** em `http://localhost:8080/swagger-ui.html` com a aplicação rodando.

### 🔐 Autenticação e Usuários (`UserController` & `AuthController`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Realiza login e retorna o Token JWT. |
| `POST` | `/atttoken` | Atualiza o Access Token utilizando um Refresh Token. |
| `POST` | `/users` | Cadastra um novo usuário no sistema. |
| `PUT` | `/users` | Atualiza os dados cadastrais do usuário logado. |
| `DELETE` | `/users` | Remove permanentemente a conta do usuário logado. |
| `PUT` | `/users/change-password` | Altera a senha do usuário logado (requer senha antiga). |
| `POST` | `/users/forgot-password` | Solicita o envio de token para recuperação de senha por e-mail. |
| `POST` | `/users/reset-password-token` | Redefine a senha utilizando o token de segurança enviado. |

### 🔗 Gerenciamento de Links (`LinkController`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/links/shorten` | Encurta uma nova URL e a persiste no banco de dados. |
| `GET` | `/links/{code}` | Redireciona o acesso para a URL original correspondente. |
| `DELETE` | `/links/{id}` | Remove um link encurtado pelo ID. |

### 🖼️ Utilidades (`QrCodeController`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/qrcodes` | Gera um QR Code para a URL informada no payload. |

---


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
- [x] Fluxo de recuperação de senha (Forgot Password).

### 📈 Inteligência e Métricas
- [ ] Implementar coleta e exibição de estatísticas de utilização dos links (cliques, origem, data).

---
**Desenvolvido por Luiz** – Full Stack Developer.
