FEITV

Uma simulação de plataforma de streaming desenvolvida em Java como projeto acadêmico, inspirada no YouTube. Permite que usuários cadastrem contas, naveguem por filmes e séries, curtam conteúdos e gerenciem suas listas de reprodução. Integrando a um banco de dados PostgreSQL.

---

Sobre o projeto

O projeto tem o objetivo de colocar em prática conceitos de orientação a objetos, banco de dados relacional e desenvolvimento de interfaces gráficas em Java. A ideia é criar algo parecido com o YouTube, onde o usuário pode explorar um catálogo de videos, salvar favoritos e acompanhar o progresso das séries que está assistindo.

---

Funcionalidades

- **Cadastro e login de usuário** — crie sua conta e acesse a plataforma
- **Tela principal estilo YouTube** — grid de cards com thumbnail, título e duração
- **Busca por nome** — encontre filmes e séries pelo título, com histórico de pesquisas
- **Curtir e descurtir vídeos** — marque o que você gostou
- **Lista de reprodução** — adicione e remova vídeos da sua lista pessoal
- **Informações detalhadas** — tela específica para filmes (diretor, gênero) e séries (episódio atual, estado)
- **Acompanhamento de séries** — marque como "Watching", "Finished" ou "Paused" e avance episódios
- **Gerenciamento de conta** — edite nome e senha ou exclua sua conta

---

Tecnologias utilizadas

- **Java** com **Swing** para a interface gráfica
- **PostgreSQL** como banco de dados
- **JDBC** para comunicação com o banco
- **NetBeans IDE** como ambiente de desenvolvimento
- Padrão de arquitetura **MVC** (Model - View - Controller)

---

Banco de dados

O projeto utiliza as seguintes tabelas:

| Tabela | Descrição |
|---|---|
| `tbusers` | Usuários cadastrados |
| `tbvideos` | Dados base de todos os vídeos |
| `tbmovies` | Dados específicos de filmes |
| `tbseries` | Dados específicos de séries |
| `tblist` | Lista de reprodução do usuário |
| `tblikes` | Vídeos curtidos por cada usuário |
| `tbsearch_history` | Histórico de pesquisas |

---

Estrutura do projeto

```
src/
├── controller/         # Lógica de negócio
│   ├── ControlLoginPanel.java
│   ├── ControlSignInPanel.java
│   ├── ControlAccount.java
│   ├── ControlModifyAccount.java
│   └── ControlExcludeAccount.java
│
├── dao/                # Acesso ao banco de dados
│   ├── Connect.java
│   ├── UserDAO.java
│   ├── VideoDAO.java
│   ├── MovieDAO.java
│   ├── SerieDAO.java
│   └── SearchHistoryDAO.java
│
├── model/              # Classes de domínio
│   ├── Video.java          (abstrata)
│   ├── Movie.java          (herda de Video)
│   ├── Serie.java          (herda de Video, implementa State)
│   ├── State.java          (interface)
│   ├── User.java
│   ├── ListReproduction.java
│   └── Main.java
│
├── view/               # Interfaces gráficas
│   ├── LoginPanel.java
│   ├── SignInPanel.java
│   ├── MenuPanel.java
│   ├── MovieInfoPanel.java
│   ├── ProfilePanel.java
│   ├── ModifyAccountPanel.java
│   └── ExcludeAccountPanel.java
│
└── resources/          # Recursos estáticos
    └── default_thumbnail.png
```

---

Arquitetura

O projeto segue o padrão **MVC**:

- **Model** — classes que representam os dados (`Video`, `Movie`, `Serie`, `User`, etc.)
- **View** — telas Swing que o usuário vê e interage
- **Controller** — classes que recebem ações da View, processam e atualizam o Model via DAO

```
View → Controller → DAO → PostgreSQL
```

O modelo de herança segue um diagrama de classes definido no projeto:

```
Video (abstrata)
  ├── Movie
  └── Serie → implementa State (interface)

User → possui → ListReproduction → composta por → Video
```

---

Como rodar

Pré-requisitos

- Java JDK 11 ou superior
- PostgreSQL instalado e rodando
- NetBeans IDE (recomendado)
- Driver JDBC do PostgreSQL adicionado ao projeto

Configuração do banco

1. Crie um banco chamado `usuarios` no PostgreSQL
2. Execute os scripts de criação das tabelas:

```sql
CREATE TABLE tbusers (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING,
    password CHARACTER VARYING,
    gender CHARACTER VARYING,
    age INTEGER
);

CREATE TABLE tbvideos (
    id SERIAL PRIMARY KEY,
    title CHARACTER VARYING(255) NOT NULL,
    thumb TEXT,
    duration INTEGER,
    data_up TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description CHARACTER VARYING(500)
);

CREATE TABLE tbmovies (
    id INTEGER PRIMARY KEY REFERENCES tbvideos(id),
    director CHARACTER VARYING(100),
    genre CHARACTER VARYING(50)
);

CREATE TABLE tbseries (
    id INTEGER PRIMARY KEY REFERENCES tbvideos(id),
    episode INTEGER DEFAULT 1,
    total_episodes INTEGER,
    current_state CHARACTER VARYING(50)
);

CREATE TABLE tblist (
    id SERIAL PRIMARY KEY,
    id_user INTEGER REFERENCES tbusers(id),
    id_video INTEGER REFERENCES tbvideos(id),
    data_add TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tblikes (
    id_user INTEGER REFERENCES tbusers(id),
    id_video INTEGER REFERENCES tbvideos(id),
    PRIMARY KEY (id_user, id_video)
);

CREATE TABLE tbsearch_history (
    id SERIAL PRIMARY KEY,
    id_user INTEGER REFERENCES tbusers(id),
    search_term CHARACTER VARYING(255) NOT NULL,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

3. Ajuste a string de conexão em `dao/Connect.java` com suas credenciais:

```java
DriverManager.getConnection(
    "jdbc:postgresql://localhost:5433/usuarios",
    "seu_usuario", "sua_senha"
);
```

Rodando o projeto

1. Abra o projeto no NetBeans
2. Certifique-se que o PostgreSQL está rodando
3. Execute o projeto pelo `Main.java`

---

Fluxo de navegação

```
Main
 └── LoginPanel
       ├── SignInPanel (cadastro)
       └── MenuPanel (tela principal)
             ├── MovieInfoPanel (ao clicar em um filme)
             ├── SerieInfoPanel (ao clicar em uma série)
             └── ProfilePanel
                   └── ModifyAccountPanel
```
