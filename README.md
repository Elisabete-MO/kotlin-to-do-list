# :notebook_with_decorative_cover: Boas-vindas ao repositório do projeto To Do List! :writing_hand:

<p align="center">API Rest para gerenciamento de tarefas</p>
<p align="center">
     <a alt="Java" href="https://java.com" target="_blank">
        <img src="https://img.shields.io/badge/Java-v17.0.6-5382A1.svg" />
    </a>
    <a alt="Kotlin" href="https://kotlinlang.org" target="_blank">
        <img src="https://img.shields.io/badge/Kotlin-v1.8.22-purple.svg" />
    </a>
    <a alt="Spring Boot" href="https://spring.io/projects/spring-boot" target="_blank">
        <img src="https://img.shields.io/badge/SpringBoot-v3.1.14-lightgreen.svg" />
    </a>
    <a alt="Maven" href="https://maven.apache.org/" target="_blank">
        <img src="https://img.shields.io/badge/Maven-v4.0.0-red.svg" />
    </a>
    <a alt="H2 database" href="https://www.h2database.com/html/main.html"  target="_blank">
        <img src="https://img.shields.io/badge/H2-v2.1.214-darkblue.svg" />
    </a>
    <a alt="Flyway" href="https://flywaydb.org/" target="_blank">
        <img src="https://img.shields.io/badge/Flyway-v9.16.3-darkred.svg">
    </a>
</p>

<h3>Descrição do Projeto</h3>

<details>
  <summary><strong>Diagrama de classes</strong></summary>

```mermaid
classDiagram
class Task { 
    +id: string
    +date: date
    +title: string
    +description: string
    +status: Status
    +userId: number
    +save(task: Task) void
    +update(task: Task) Task
    +delete(id: number) void
    +findAll() List~Task~
    +findTaskByUserId(userId: number) List~Task~
}

class User {
    +id: number
    +username: string
    +email: string
    -password: string
    -image_url: string
    +save(user: User): void
    +update(user: User): User
    +delete(id: number): void
    +findAll(): List~User~
    +findByUsername(username: string): User 
    }

class Status { 
    <<enumeration>>
    OPEN
    IN_PROGRESS
    DONE 
    }
  	
  	
  User "1" *-- "n" Task
  Task "1" -- "1" Status
  ```
</details>

[//]: # (Documentação)

[//]: # ()
[//]: # (Flyway é uma dentre as várias ferramentas que se propõem a trazer )
[//]: # (:bookmark_tabs:)

[//]: # (:memo:)

[//]: # (:pencil:)

[//]: # (:calendar:)

[//]: # (:heavy_check_mark:)

[//]: # (:x:)
[//]: # (ordem e organização para os scripts SQL que são executados no banco de dados, funcionando como um controle de versão do mesmo.)

[//]: # ()
[//]: # (<h3>Instrução de Uso</h3>)

[//]: # ()
[//]: # (<p>No Terminal/Console:</p>)

[//]: # ()
[//]: # (<ol>)

[//]: # (	<li>Faça um clone do projeto na sua máquina: <code>git clone git@github.com:cami-la/credit-application-system.git</code></li>)

[//]: # (	<li>Entre na pasta raiz do projeto: <code>cd </code></li>)

[//]: # (	<li>Execute o comando: <code>./gradlew bootrun</code></li>)

[//]: # (</ol>)

[//]: # ()
[//]: # (<h6>** Visando facilitar a demostração da aplicação, recomendo instalar apenas o IntelliJ IDEA e executar o projeto através da IDE **</h6>)

[//]: # (<a href="https://drive.google.com/file/d/1wxwioDHS1sKFPq4G7b24tVZb-XMnoj-l/view?usp=share_link"> 🚀 Collection API - Postman</a><br>)
