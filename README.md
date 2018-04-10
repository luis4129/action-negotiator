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

# Pré-requisitos

- PostgreSQL 10.3. Link para download: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- Maven. Link para download: http://ftp.unicamp.br/pub/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
- Git. Link para downlaod: https://git-scm.com/download/win

# Baixando o repositório

Abra o Git Bash no diretório que deseja baixar o repositório e digite o comando abaixo:
- $ git clone https://github.com/luis4129/action-negotiator.git

# Configurando o arquivo application.properties

Para contexto, o arquivo está localizado em action-negotiator/src/main/resources/application.properties.

Na configuração do Postgres, foi considerado a porta padrão "5432", o usuário "postgres" e a senha "root". Caso o Postgres esteja configurado de alguma forma diferente na máquina a ser executada, será necessário ajustar o arquivo application.properties própriamente, nas linhas abaixo:

- spring.datasource.username=**postgres**
- spring.datasource.password=**root**
- spring.datasource.url=jdbc:postgresql://localhost:**5432**/action_negotiator_db

Criar um banco de dados com o nome "action_negotiator_db", ou caso prefira outro nome, basta ajustar a linha abaixo:
- spring.datasource.url=jdbc:postgresql://localhost:5432/**action_negotiator_db**

Ao executar a aplicação, a mesma será executada na porta 4129, visto que é uma porta pouco utilizada. Caso necessário o uso de outra porta, basta ajustar a linha abaixo:
- server.port=**4129**

# Gerando o JAR

Caso ainda estiver com o Git Bash utilizado anteriormente, execute o comando abaixo para entrar no diretório do repositório clonado:
- $ cd action-negotiator

Caso contrário, basta abrir outro Git Bash no diretório do repositório clonado.

Após isso, basta executar o comando abaixo para gerar o JAR do repositório:
- $ mvn package

# Executando o JAR

Agora só falta executar, execute o comando abaixo para entrar no diretório do JAR criado:
- $ cd target

Agora basta executar o JAR, com o comando abaixo:
- $ java -jar actionNegotiator-0.0.1-SNAPSHOT.jar

# Pronto!
Agora é só abrir a URL abaixo:
http://localhost:4129/



