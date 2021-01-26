# Action Negotiator

*Description:*

- Action negotiator it's an application developted as a challenge using Spring Boot, a Java Framework.

*Entities:*

- `Account` represents the investing account.
- `Company` represents the company on which the stocks will be sold.
- `InvestmentRule` represents a set of rules that will be followed in order for an account to invest in a company automatically.
- `Stock` represents companies stocks that have been bought by an accoount.
- `Transaction` represents business transactions, containing logs of stocks bought and sold.

*Mapped relationships:*

- An **account** can have multiple **investment rules**.
- An **account** can have multiple **stocks**.
- A **investment rule** belongs to an **account** and a **company**.
- A **stock** belongs to an **account** and a **company**.
- Multiple **transactions** can belong to the same **account** and **company**.

*Architecture:*

- Controller - Receives the requests, builds the objects, directs it to the proper Service.
- Service - Prepares the objects following the business rules, and send it to the repoistory interface.
- Repository - Interface on which the appplication acesses the database.
- Model - Entities modeling;
- Enums - Enums modeling;
- Exception - Custom exceptions;
- Util - Code to be used by the entire project;

*Frameworks:*

- Backend: Only SpringBoot was used, as requests by the challenge.
- Frontend: Bootstrap in order to make the application a little more presentable, although not much attention was spent on it.

# Prerequisites

- PostgreSQL 10.3. Download link: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- Maven. Download link: http://ftp.unicamp.br/pub/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
- Git. Download link: https://git-scm.com/download/win

# Downloading the repository

Open the Git Bash inside the directory on which you want to download the repository, and execute the following command:
```
$ git clone https://github.com/luis4129/action-negotiator.git
```

# Setting the application.properties file

The file is located at `action-negotiator/src/main/resources/application.properties`.

On the Postgres setttings, it was used the default port `5432`, user `postgres` and password `root`. If your Postgres setttings are different in any way, there will be a need to update the file on the lines of code below:

```
spring.datasource.username=**postgres**
spring.datasource.password=**root**
spring.datasource.url=jdbc:postgresql://localhost:**5432**/action_negotiator_db
```

Create a database with the name "action_negotiator_db". If you want to use another name, just update the file on the line of code below:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/**action_negotiator_db**
```

When the application ir ran, it port `4129` will be used, since it's a pretty non standard port, and shouldn't be any conflict. If necessary, you can just change the following line:

```
server.port=**4129**
```

# Building the .jar

If the previous Git Bash is still open, execute the following statement to enter the cloned repository directory:

```
$ cd action-negotiator
```

Otherwisde, just open another Git Bash at the cloned repository directory.

After that, just execute the following command to generate the `.jar` file:

```
$ mvn package
```

# Running the .jar

Run the following statement to enter the `.jar` directory:

```
$ cd target
```

Now, all that's left is to run the `.jar` file with the statement below:

```
$ java -jar actionNegotiator-0.0.1-SNAPSHOT.jar
```

# Done!
Just open the URL below, it should be up and running:
http://localhost:4129/



