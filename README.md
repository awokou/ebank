# 🚀 e-bank – API & Frontend
## 🛠 Backend (API)

## Technologies utilisées

- **Java** 21
- **Maven** 3.8+
- **Spring Boot** 3.5+
- **Spring Web**
- **Spring Data JPA**
- **Spring Security + JWT(JSON Web Token)**
- **Lombok**
- **PostgreSQL**
- **H2** (base en mémoire pour tests)
- **Postman**
- **Docker / Docker Compose**

## Project

01) Cloner le projet

    	git clone https://github.com/awokou/ebank.git

02) Créer la base de données PostgreSQL

    	Exécutez le script SQL fourni pour créer la base de données ebank

03) Construire et lancer le projet avec Maven

    	- mvn clean install
      - mvn spring-boot:run

04) Configurer la base de données (Modifiez le fichier application.yml pour adapter vos paramètres PostgreSQL)
    	
        spring:
            datasource:
               url: jdbc:postgresql://localhost:<port>/<ecollect>
    	       username: <username>
    	       password: <password>
    
05) Remplacez <username> et <password> par vos identifiants PostgreSQL.

## Documentation API

- **Swagger UI :** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## JWT & Sécurité
L’API est sécurisée avec Spring Security et JSON Web Tokens (JWT). Pour obtenir un token : utilisez l’endpoint d’authentification.
Incluez le token dans le header Authorization: Bearer <token> pour accéder aux endpoints protégés.

## Deployment (Dockerize)
  01) Build the app: mvn clean package
  02) Build the Docker image: docker build -t api-ecollect .
  03) Run the container: docker run -p 8080:8080 api-ecollect

## Deploying with Docker Compose
  01) Run: docker-compose up --build
  02) Access: http://localhost:8080/swagger-ui/index.html
  03) Stop: docker-compose down

# 🎨 Frontend (Angular)

Projet généré avec [Angular CLI](https://github.com/angular/angular-cli) version 20.3.13.

![Angular](https://img.shields.io/badge/Angular-20.3.13-red?logo=angular)

## Lancer le frontend

```bash
npm start
```

Ouvrir dans le navigateur`http://localhost:4200/`.

## 🏗 Build production

```bash
ng build
```

