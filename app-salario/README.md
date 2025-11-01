# 💼 App Salário

Aplicação web desenvolvida com **Spring Boot**, **JSF** e **PostgreSQL**, que consolida e exibe remunerações de colaboradores.  
O sistema permite recalcular salários em segundo plano (usando threads controladas) e atualizar a listagem na interface JSF.

---

## 🚀 Tecnologias Principais

- **Java 17**
- **Spring Boot 3.2.5**
  - Web
  - Data JPA
- **JoinFaces 5.5.4** (integração JSF + Spring Boot)
- **Mojarra 4.0.2** (implementação JSF)
- **PostgreSQL** (banco de dados)
- **Lombok**
- **Weld CDI** (integração de escopos JSF)
- **Maven** (empacotamento e build)

---

## ⚙️ Estrutura e Configuração

### `pom.xml`
Configuração principal do projeto, com empacotamento `jar`, permitindo uso do **Tomcat nativo** do Spring Boot.  
A dependência `faces-spring-boot-starter` habilita o JSF automaticamente com base na detecção de `FacesServlet`.

### `web.xml`
Configura o **FacesServlet** para processar páginas `.xhtml`:

```xml
<servlet>
    <servlet-name>FacesServlet</servlet-name>
    <servlet-class>jakarta.faces.webapp.FacesServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>FacesServlet</servlet-name>
    <url-pattern>*.xhtml</url-pattern>
</servlet-mapping>
```

### `application.properties`
Define propriedades do banco e do contexto da aplicação:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/app_salario
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=false

server.servlet.context-path=/
server.port=8080
```

---

## 🧩 Estrutura de Pacotes

```
com.hugobarros.salarioapp
 ├── config/              → Filtro para configuração de autenticação
 ├── controller/          → Beans JSF
 ├── dto/                 → Objetos de transferência de dados
 ├── model/               → Entidades JPA
 ├── repository/          → Repositórios JPA
 ├── service/             → Lógica de negócio para cálculos assíncronos
 └── AppSalarioApplication.java
```

---

## 🖥️ Execução do Projeto

### 🔧 Build

Compile o projeto com Maven:

```bash
mvn clean package
```

> Gera o artefato `target/app-salario-1.0.0.jar`

---

### ▶️ Execução

Inicie o servidor embutido (Tomcat do Spring Boot):

```bash
mvn spring-boot:run
```

Acesse no navegador:
```
http://localhost:8080/index.xhtml
ou
http://localhost:8080/login.xhtml
```

---

## 📜 Funcionalidades Principais

- Autenticação simples via `AuthenticationFilter`.
- Exibição de remunerações.
- Recalcular salários em **thread separada** (background).

---

## 💾 Banco de Dados

Scripts SQL de estrutura e carga inicial estão na pasta:
```
\src\main\resources\sql
```

Podem ser aplicados diretamente via `psql` ou Flyway (futuro).

---

## 🧠 Observações Técnicas

- O uso de `JoinFaces` elimina necessidade de configurar `FacesServlet` manualmente no Spring Boot, mas o `web.xml` é mantido por compatibilidade.

---

## 👨‍💻 Autor

**Hugo Barros**  
Desenvolvedor Java  
📧 [vitor5030@gmail.com](mailto:vitor5030@gmail.com)  
🔗 [linkedin.com/in/hugobarros08](https://linkedin.com/in/hugobarros08)  
💻 [github.com/HugoBarros08](https://github.com/HugoBarros08)
