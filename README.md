# API proyecto particulares

## Integrantes:
- Centurión, Rocío
- Coloca, Fiorella
- Di Nubila, Lucas
- Isaurralde, Rocío

## Tecnología 
- Java 11
- Maven 3.8.2
- Spring Boot 2.5.4

## Instalación

1. Clonar el repositorio

    `git clone https://github.com/RoMaIsau/Proyecto-particulares-api.git`

2. Instalar las dependencias

   `mvn clean install`

## Creación de Base De Datos

1. Instalar SQL Server 2019
2. Crear base de datos **ParticularesDB**

   `CREATE DATABASE ParticularesDB`
3. Crear usuario y password (Solo ambiente local)

   `CREATE LOGIN particulares WITH PASSWORD = 'particulares';`

   `CREATE USER particulares FOR LOGIN particulares;`

4. Hacer owner de la base al usuario

   `exec sp_addrolemember 'db_owner', 'particulares'`

## Integración con Mercado Pago

### Usuarios de prueba:

*Usuario Vendedor*

```json
{
    "id": 1018847803,
    "nickname": "TESTMNGVSNQH",
    "password": "qatest9262",
    "site_status": "active",
    "email": "test_user_7735484@testuser.com"
}
```

*Usuario Comprador (Alumno)*

```json
{
   "id": 1018849804,
   "nickname": "TETE8505840",
   "password": "qatest4940",
   "site_status": "active",
   "email": "test_user_42026298@testuser.com"
}
```

> Nota: Estos usuarios caducan a los 60 días a partir del 14/11/2021

## Levantar API

`mvn spring-boot:run`

## Ejecutar pruebas unitarias

`mvn test`

## Generación de documentación a partir de las pruebas de los controladores

`mvn package`

El HTML generado **/target/generated-docs/api-documentacion.html**

## Deploy en Azure

Precondiciones

* Instalar Azure Cli
* Instalar Docker

1. Loguearse en Azure

   `az login`

2. Loguearse en el Azure Container Registry del proyecto y pushear la imagen usando el plugin de jib

   `az acr login -n particularesapi && mvn compile jib:build`

> 28/11/2021 Se agotaron los créditos. Se busca otra alternativa