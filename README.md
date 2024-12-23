# ![Logo-template](img/logo.png)
# Recurso Java: Reto técnico Sermaluc

>El objetivo de este documento es proveer información relacionada del Proyecto que ha sido implementado sobre Java 21, SpringBoot 3, Spring Security, API First, Swagger, H2 y JPA.

### 1.  Funcionalidad
>Este proyecto Java tiene como objetivo registrar un nuevo usuario, previamente pasando por un logueo de credenciales. Se tienen registrados 2 usuarios en la base de datos en memoria H2, los cuales son: Admin User y Gerente User.

### 2.  Preparación de ambiente
>Este **proyecto** brinda diversas capacidades a los aplicativos para poder recuperar datos de manera segura y fácil mediante los siguientes pasos:

#### 2.1. H2:
>Programa que es necesario para el funcionamiento y conexión de la aplicación.

##### 2.1.1 Credenciales

| # | Propiedad     |                             Valor |
| :----|:-------------:|----------------------------------:|
|1| database |                            testdb |
|2| username |                          sermaluc |
|3| password |                          reto_cbi |
|5| URL | http://localhost:8080/h2-console/ |

### 3.  Versiones
>En este apartado se detalla un historial de cambios o *changelog* por cada version implementada del recurso.

+ Versión 1.0.0: Esta versión permite enviar y devolver campos.

### 4.  Diagrama
>El siguiente diagrama muestra la estructura de la aplicación.

![Diagrama](img/diagrama.jpg)

### 5.  Pasos de funcionamiento
>Se detallará los pasos a seguir para poder ejecutar la aplicación.

#### 5.2. Pre requisitos
- Se debe tener instalado [Java 21](https://jdk.java.net/archive/) y [Maven 3.9.9](https://maven.apache.org/download.cgi)
- Se debe tener [Postman](https://www.postman.com/downloads/)
- Se debe tener un IDE como [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows)
- Se debe tener abierta la página [Swagger Editor](https://editor.swagger.io/) para poder ver el contrato en Swagger _API-BCI.yaml_

#### 5.3. Iniciar de la aplicación
1. Importar proyecto descargado desde el repositorio de GitHub.
2. Ejecutar la aplicación desde el IDE, usando el JDK 21.
3. Abrir Postman, importar la collection "_Sermaluc.postman_collection.json_".
4. Ejecutar el endpoint **Login** de tipo **POST** para obtener el token y actualizar el último ingreso. 
   - En el body, ingresar las credenciales de Gerente User:
       ```json
       {
           "username": "gerency@sermaluc.pe",
           "password": "deLl#53"
       }
       ```
   - En el response, copiar el **token** devuelto:
       ```json
       {
           "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXJlbmN5QHNlcm1hbHVjLnBlIiwiaWF0IjoxNzM0OTU3NTAxLCJleHAiOjE3MzQ5NjExMDF9.tcjh-eqXGd0d3tYLB-7_Je0PsIrlKWGBfqBkT1YHQTU"
       }
       ```
5. Ejecutar el endpoint **BCI** de tipo **POST** para registrar un nuevo usuario.
   - En el header, ingresar el **token** obtenido en el paso anterior:
     ```bash
     "Authorization: Bearer <your_token>"
   - En el body, ingresar los datos del nuevo usuario:
       ```json
       {
            "name": "Andy",
            "email": "juan@rodriguez.org",
            "password": "deLl%1245",
            "phones": [
                {
                    "number": "1234567",
                    "cityCode": "1",
                    "countryCode": "57"
                }
            ]
      }
       ```
6. Para actualizar la expresión regular de la contraseña, se debe modificar el archivo **application.properties** en la ruta _src/main/resources_:
   ```bash
   password.pattern=^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$
   ```