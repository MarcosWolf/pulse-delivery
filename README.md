# pulse-delivery

Sistema de gerenciamento de pedidos para delivery, desenvolvido com Spring Boot, JPA/Hibernate e PostgreSQL. Gerencie clientes, endereços, status de pedidos e histórico de entregas através de uma API REST simples e escalável.

## Sobre

O PulseDelivery é uma solução backend robusta para empresas e serviços de entrega que precisam organizar pedidos de forma eficiente. A aplicação oferece uma API REST construída com Spring Boot, permitindo integração com frontends web ou mobile e controle completo sobre pedidos e clientes.

## Principais Funcionalidades

- <b>Gestão de Pedidos:</b> crie, consulte e atualize pedidos com facilidade
- <b>Controle de Clientes:</b> registre informações de clientes e endereços de entrega
- <b>Status de Entrega:</b> acompanhe o status de cada pedido (CREATED, DELIVERED, etc.)
- <b>Registro de Datas:</b> controle de datas de criação dos pedidos
- <b>API REST Intuitiva:</b> endpoints claros e consistentes para fácil integração com frontends

## Tecnologias Utilizadas

### Core

- <b>Java:</b> linguagem de programação principal
- <b>Maven:</b> gerenciamento de dependências e build

### Backend

- <b>Spring Boot:</b> framework principal para a API REST
- <b>JPA/Hibernate:</b> persistência de dados com ORM
- <b>MapStruct:</b> mapeamento automático entre DTOs e entidades
- <b>PostgreSQL + Docker:</b> banco de dados persistido via container Docker
- <b>H2 Database:</b> usado em memória apenas para testes

### Testes

- <b>JUnit:</b> testes unitários e de integração
- <b>Mockito:</b> framework de mocking para testes isolados
- <b>REST Assured:</b> testes automatizados da API REST

### Containers e Ambiente
- <b>Docker:</b> orquestração de containers para banco de dados e ambiente isolado

## Executando a Aplicação

1. Clone o repositório:

```bash
git clone https://github.com/MarcosWolf/pulse-delivery.git
cd pulse-delivery
```

2. Certifique-se de que o Docker está rodando.
   
3. Suba o banco de dados via Docker Compose:
```bash
docker-compose up -d
```

4. Compile o projeto:
```bash
mvn clean install
```

5. Execute a aplicação:
```bash
mvn spring-boot:run
```

## Executando os Testes

Para rodar todos os testes:

```bash
mvn test
```

Para testes específicos:

```bash
mvn test -Dtest=NomeDaClasse
```

## Estrutura do Projeto

```text
pulsedelivery/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/marcoswolf/pulsedelivery/
│   │   │       ├── controller/       # Controladores REST
│   │   │       ├── dto/              # DTO
│   │   │       ├── mapper/           # Mappers
│   │   │       ├── model/            # Classes de domínio
│   │   │       ├── repository/       # Interfaces de acesso a dados
│   │   │       └── service/          # Lógica de negócio
│   │   └── resources/
│   └── test/
│       └── java/
│           └── br/marcoswolf/pulsedelivery/
│               ├── controller/     # Testes REST com RestAssured
│               ├── service/        # Testes unitários e de integração
│── docker-compose.yml
├── pom.xml
└── README.md
```

## API REST

A aplicação expõe uma API REST completa para integração com outros sistemas:

- `GET/PUT/POST /orders` – Gerenciamento de pedidos

### PostgreSQL (via Docker)

1. Certifique-se de que o Docker está rodando.
2. Acesse o Docker via terminal: `docker exec -it pulsedelivery-postgres bash`
3. Acesse o PostgreSQL: `psql -h localhost -p 5432 -U pd_user -d pulsedelivery`

- **Container:** pulsedelivery-postgres
- **Porta:** 5432
- **Banco:** pulsedelivery
- **Usuário:** pd_user
- **Senha:** wolf
