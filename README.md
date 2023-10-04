# :: Boas-vindas ao repositório do projeto To Do List! ::

<p align="center">API Rest para gerenciamento de tarefas</p>
<p align="center">
     <a alt="Java">
        <img src="https://img.shields.io/badge/Java-v17-5382A1.svg" />
    </a>
    <a alt="Kotlin">
        <img src="https://img.shields.io/badge/Kotlin-v1.8.22-purple.svg" />
    </a>
    <a alt="Spring Boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-v3.1.
14-lightgreen.svg" />
    </a>
    <a alt="Maven">
        <img src="https://img.shields.io/badge/Maven-v4.0.0-red.svg" />
    </a>
    <a alt="H2 ">
        <img src="https://img.shields.io/badge/H2-v2.2.224-darkblue.svg" />
    </a>
    <a alt="Flyway">
        <img src="https://img.shields.io/badge/Flyway-v9.22.2-darkred.svg">
    </a>
</p>

<h3>Descrição do Projeto</h3>

#### Diagrama de classes da aplicação <br>

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


[//]: # ()
[//]: # (<h3>Instrução de Uso</h3>)

[//]: # (<p>No Terminal/Console:</p>)

[//]: # (<ol>)

[//]: # (	<li>Faça um clone do projeto na sua máquina: <code>git clone git@github.com:cami-la/credit-application-system.git</code></li>)

[//]: # (	<li>Entre na pasta raiz do projeto: <code>cd </code></li> )

[//]: # (	<li>Execute o comando: <code>./gradlew bootrun</code></li>)

[//]: # (</ol>)

[//]: # (<h6>** Visando facilitar a demostração da aplicação, recomendo instalar apenas o IntelliJ IDEA e executar o projeto através da IDE **</h6>)

[//]: # ()
[//]: # ()
[//]: # (<a href="https://drive.google.com/file/d/1wxwioDHS1sKFPq4G7b24tVZb-XMnoj-l/view?usp=share_link"> 🚀 Collection Sacola API - Postman</a><br>)

[//]: # ()
[//]: # ()
[//]: # (<h3>Autor</h3>)

[//]: # ()
[//]: # (<a href="https://www.linkedin.com/in/cami-la/">)

[//]: # ( <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/64323124?v=4" width="100px;" alt=""/>)

[//]: # ( <br />)

[//]: # ( <sub><b>Camila Cavalcante</b></sub></a> <a href="https://www.instagram.com/camimi_la/" title="Instagram"></a>)

[//]: # ()
[//]: # (Feito com ❤️ por Cami-la 👋🏽 Entre em contato!)

[//]: # ()
[//]: # ([![Linkedin Badge]&#40;https://img.shields.io/badge/-Camila-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/cami-la/&#41;]&#40;https://www.linkedin.com/in/cami-la/&#41;)

[//]: # ([![Gmail Badge]&#40;https://img.shields.io/badge/-camiladsantoscavalcante@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:camiladsantoscavalcante@gmail.com&#41;]&#40;mailto:camiladsantoscavalcante@gmail.com&#41;)

[//]: # (<hr>)

[//]: # (<h3>Contribuindo</h3>)

[//]: # ()
[//]: # (Este repositório foi criado para fins de estudo, então contribua com ele.<br>)

[//]: # (Se te ajudei de alguma forma, ficarei feliz em saber. Caso você conheça alguém que se identifique com o conteúdo, não)

[//]: # (deixe de compatilhar.)

[//]: # ()
[//]: # (Se possível:)

[//]: # ()
[//]: # (⭐️ Star o projeto)

[//]: # ()
[//]: # (🐛 Encontrar e relatar issues)
