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
- <b>PostgreSQL + Docker:</b> banco de dados persistido via container Docker

  ### Containers e Ambiente
- <b>Docker:</b> orquestração de containers para banco de dados e ambiente isolado
