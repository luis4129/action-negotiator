# Informaçoes sobre o projeto

*Descrição:*

- Negociador de ações é uma aplicação que foi desenvolvida como desafio, utilizando Spring Boot, um framework Java.

*Entidades:*

- Account (Conta) representa a conta que irá investir.
- Company (Empresa) representa a empresa terá as ações vendidas.
- InvestmentRule (Regra de Investimento) representa as regras que serão obedecidas para uma conta investir em uma empresa.
- Stock (Ação) representa ações de uma empresa que foram compradas por uma conta.
- Transaction (Transação) representa compras e vendas realizadas.

*Relacionamentos mapeados:*

- Uma **conta** possui uma ou mais **regras de investimento**.
- Uma **conta** possui uma ou mais **ações**.
- Uma **regra de investimento** pertence a uma **conta** e **empresa**.
- Uma **ação** pertence a uma **conta** e **empresa**.
- Uma ou mais **transações** pertencem a uma **conta** e **empresa**.

*Arquitetura utilizada:*

- Controller - Recebe a request, monta os objetos, direciona ao devido Service.
- Service - Realiza todas regras de backend, e encaminha para uma interface Repository
- Repository - Interface responsável pelo acesso aos dados no banco de dados.
- Model - Modelagem das entidades;
- Enums - Modelagem dos enums;
- Exception - Exceptions customizadas;
- Util - Código reutilizavel pelo resto da aplicação;

*Frameworks:*

- Para backend foi somente utilizado o Spring Boot, que foi o framework solicitado pelo desafio.
- Para frontend foi utilizado Bootstrap, só para o CRUD ficar mais apresentável, embora não foi dada muita atenção no frontend da aplicação.
