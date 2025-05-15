
# Forum Hub

Projeto desenvolvido para estudo de Java e Spring Security, simulando um fórum de discussão com autenticação, autorização e envio de e-mails para verificação de conta.

## Funcionalidades

- Cadastro de usuários com verificação de e-mail
- Login e refresh de token JWT
- Controle de acesso por perfis (Estudante, Instrutor, Moderador, Admin)
- Cadastro, listagem e gerenciamento de tópicos e respostas
- Adição de perfis a usuários (admin)
- Envio de e-mails assíncronos para verificação de conta

## Arquitetura

- **Spring Boot**: Framework principal da aplicação
- **Spring Security**: Gerenciamento de autenticação e autorização
- **JWT**: Tokens para autenticação stateless
- **JPA/Hibernate**: Persistência de dados
- **JavaMailSender**: Envio de e-mails
- **Camadas**:
  - `controller`: Endpoints REST
  - `domain`: Entidades e regras de negócio
  - `infra`: Infraestrutura (segurança, e-mail, exceções)

## Endpoints principais

- `POST /registrar`: Cadastro de novo usuário
- `GET /verificar-conta?codigo=...`: Verificação de e-mail
- `POST /login`: Autenticação e obtenção de token JWT
- `GET /cursos`: Listagem de cursos (público)
- `GET /topicos`: Listagem de tópicos (público)
- `POST /topicos`: Criação de tópico (Estudante)
- `PATCH /adicionar-perfil/{userId}`: Adição de perfil (Admin)

## Perfis e Hierarquia

- **ESTUDANTE**: Pode criar, editar e excluir seus tópicos
- **INSTRUTOR**: Permissões de estudante + outras específicas
- **MODERADOR**: Pode moderar tópicos e respostas
- **ADMIN**: Controle total, pode adicionar perfis

A hierarquia é definida em `ConfiguracoesDeSeguranca.java` e permite que perfis superiores herdem permissões dos inferiores.

## Segurança

- Autenticação via JWT
- Stateless (sem sessão)
- Filtros de segurança personalizados
- Endpoints públicos: `/login`, `/registrar`, `/verificar-conta`, `/cursos`, `/topicos`

## Envio de E-mail

Ao cadastrar, o usuário recebe um e-mail com link de verificação. Só após a verificação poderá acessar o sistema.

## Como rodar

1. Clone o repositório
2. Configure o banco de dados em `application.properties`
3. Configure as credenciais de e-mail
4. Execute a aplicação (`mvn spring-boot:run` ou via IDE)
5. Acesse `http://localhost:8080`

## Observações

- O projeto é para fins didáticos e pode ser expandido conforme necessidade.
- Para produção, ajuste as configurações de segurança e e-mail.

