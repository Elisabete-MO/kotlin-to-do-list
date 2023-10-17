# :notebook_with_decorative_cover: Boas-vindas ao repositório do projeto To Do List! :heavy_check_mark:

:siren: **ATENÇÃO!**: esse projeto ainda não foi finalizado :construction: <br>

<h3 align="center"><strong> API Rest para gerenciamento de tarefas </strong></h3>
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

Essa é uma aplicação que gerencia tarefas, onde, através de uma API Rest, é 
possível criar, editar, excluir e listar tarefas.

<h3> :bookmark_tabs: O usuário será capaz de: </h3>

- [ ] Fazer login; <br>
- [ ] Adicionar, remover e editar uma tarefa;<br>
- [ ] Marcar e desmarcar uma tarefa como concluída;<br>
- [ ] Adicionar, remover e editar uma categoria;<br>
- [ ] Visualizar uma lista com as tarefas cadastradas;<br>
- [ ] Filtrar as tarefas por status, categoria ou data de criação;<br>

<h3> Objetivo: </h3>
O principal propósito deste projeto é aplicar os padrões de projeto MVC em 
uma aplicação Kotlin e Spring Boot com front-end desenvolvido em Angular. A 
intenção é criar uma API Rest totalmente documentada com o auxílio do 
Swagger e testá-la usando JUnit e Mockito. A API será responsável por 
gerenciar as tarefas de um usuário, integrando o sistema com um banco de 
dados relacional em memória sem esquecer de aplicar os conceitos de boas 
práticas em desenvolvimento de software no atendimento aos seguintes requisitos:

<details>
    <summary>
        <strong> :memo: Requisitos </strong>
    </summary>

- [x] Desenhar diagrama de classes da aplicação com Mermaid; <br>

<details>
O diagrama de classes abaixo ilustra a estrutura do projeto, destacando as 
principais entidades e suas relações. Ele fornece uma visão geral da arquitetura do sistema e como as classes interagem umas com as outras.
Neste diagrama, é possível observar as principais classes envolvidas no 
projeto incluindo `User` e `Task`. A relação entre essas classes é 
representada pelas setas, indicando como elas se conectam.
Este diagrama serve como um guia visual útil para entender a estrutura do sistema e as classes envolvidas nas operações.

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

- [x] Desenvolver a API em Kotlin e Spring; <br>
- [x] Documentar a API com o Swagger; <br>
- [ ] Estruturar as classes no banco de dados; <br>
  - [x] Classe User; <br>
  - [x] Classe Task; <br>
  - [x] Criar as relações entre as tabelas; <br>
  - [x] Criar as queries para inserir os dados; <br>
  - [x] Criar a query para consultar as tarefas por id de usuário; <br>
  - [ ] Criar a query para consultar tarefas por status; <br>
  - [ ] Criar a query para consultar tarefas por categoria; <br>
  - [ ] Criar a query para consultar tarefas por data de criação; <br>
  - [x] Criar as queries para deletar os dados; <br>
  - [x] Criar as queries para atualizar os dados; <br>
  - [x] Intriduzir o processo de versionamento de banco de dados via FlyWay;
    <br>
- [ ] Introduzir processo de autenticação de usuários com o Spring Security;
  <br>
- [ ] Desenvolver o front-end em Angular; <br>
- [ ] Fazer o deploy da aplicação no Railway.

</details>
</details>

:siren: Todos esses conhecimentos foram adquiridos e/ou aprimorados durante o 
Code Update TQI - Backend com Kotlin e Java, promovido pela Digital Innovation One.

<h3> :pencil: Instruções de Uso</h3>

<p> :x: No Terminal/Console:</p>

<h6> :writing_hand: Visando facilitar a demostração da aplicação,
recomendo a execução do projeto através da IDE do IntelliJ IDEA. </h6>

<ol>
	<li>Faça um clone do projeto na sua máquina: <code>git clone git@github.com:Elisabete-MO/kotlin-to-do-list.git</code></li>
    <li>Abra o projeto no IntelliJ IDEA;</li>
    <li>Entre na pasta raiz do projeto: <code>cd </code></li>
	<li>Execute o comando: <code>mvn install</code></li>
    <li>Execute o comando: <code>mvn spring-boot:run</code></li>
    <li>Abra o navegador e digite: <code>http://localhost:8080/swagger-ui.html</code></li>
    <li>Para acessar o banco de dados, digite: <code>http://localhost:8080/h2-console</code></li>
</ol>

<details>
    <summary>
        <strong> :calendar: Histórico </strong>
    </summary>

Esse projeto foi desenvolvido primeiramente em HTML, CSS e javascript para
atender aos requisitos de avaliação do módulo de front-end do curso de
desenvolvimento web da Trybe. Agora, venho aplicar os conhecimentos adquiridos em Kotlin e Spring Boot para desenvolver uma API e,
posteriormente, irei utilizar o Angular para remodelar o front-end.
</details>