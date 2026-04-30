# BrevityLink API 🔗

O **BrevityLink** é uma API de encurtamento de URLs desenvolvida com **Java 21** e **Spring Boot 3**. O projeto foi desenhado para ser 
resiliente e de fácil deploy, utilizando **PostgreSQL** para persistência e **Docker** para uma orquestração simplificada de todo o 
ambiente de desenvolvimento e produção.

---

## 🚀 Tecnologias e Ferramentas

* **Java 21**
* **Spring Boot 3**
* **Maven**
* **PostgreSQL**
* **Flyway**
* **Docker**
* **Spring Security & JWT**
* **Sqids**

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
    DB_POSTGRES_USER_DOCKER=user
    DB_POSTGRES_PASSWORD_DOCKER=sua_senha
    PORT_DOCKER=5432
    APP_URL=http://localhost:8080
    JWT_SECRET= chave_JWT
    ALPHABET_SECRET= alfabeto_secreto
    MAIL_USER= email
    MAIL_PASSWORD= senha_app
    ```

3.  **Suba os serviços:**
    O comando abaixo irá realizar o *Multi-stage build* da aplicação, baixar as imagens necessárias e iniciar a API e o banco de dados:
    ```bash
    docker-compose up -d --build
    ```
---

## 📡 Endpoints da API

Abaixo estão as rotas principais disponíveis. A documentação interativa completa pode ser acessada via **Swagger UI** em `url/swagger-ui.html` com a aplicação rodando.

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
| `POST` | `/links` | Encurta uma nova URL e a persiste no banco de dados. |
| `GET` | `/links/{code}` | Redireciona o acesso para a URL original correspondente. |
| `GET` | `/links` | Retorna os links do usuário autenticado de forma paginada. |
| `DELETE` | `/links/{id}` | Remove um link encurtado pelo ID. |

### 🖼️ Utilidades (`QrCodeController`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/qrcodes` | Gera um QR Code para a URL informada no payload. |

---

### 🐳 Perfil `docker`

Ativado automaticamente via `docker-compose`.

* Utiliza variáveis de ambiente definidas no `docker-compose.yml`
* Conecta a API ao serviço de banco de dados

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

## 🚧 Status do Front-end
Atualmente, a API está 100% funcional e documentada via Swagger. O front-end está em fase de planejamento e desenvolvimento, com o objetivo de oferecer uma interface amigável para:
* Gestão de links e usuários.
* Fluxo visual de recuperação de senha (UI/UX para os tokens de reset).
* Dashboard de estatísticas.
---
**Desenvolvido por Luiz** – Full Stack Developer.
