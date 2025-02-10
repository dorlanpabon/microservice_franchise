# Franchise Service

Este es un servicio desarrollado con **Spring Boot**, **Java 17**, **Gradle** y **MySQL** utilizando **R2DBC** para la conexión reactiva a la base de datos.

## Prerrequisitos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes programas:

- **Java 17** ([Descargar](https://adoptium.net/))
- **Gradle** ([Descargar](https://gradle.org/install/))
- **Docker** y **Docker Compose** (para ejecución con contenedores) ([Descargar](https://www.docker.com/))
- **MySQL** (si deseas ejecutar la base de datos localmente sin Docker)

## Configuración del Proyecto

### 1. Clonar el repositorio

Si aún no lo has hecho, clona este repositorio en tu máquina local:

```sh
git clone https://github.com/dorlanpabon/microservice_franchise.git
```

Luego, entra a la carpeta del proyecto:

```sh
cd franchise
```

### 2. Configurar la base de datos

El proyecto usa **MySQL con R2DBC**, por lo que debes asegurarte de que exista una base de datos con las credenciales adecuadas.

Si usas **MySQL localmente**, crea la base de datos con el siguiente comando:

```sql
CREATE DATABASE franchise;
```

Luego, configura las credenciales en `src/main/resources/application.yml`:

```yml
spring:
    r2dbc:
        url: r2dbc:mysql://localhost:3306/franchise
        username: root
        password: 
```

### 3. Construir y ejecutar la aplicación

Para compilar y ejecutar la aplicación localmente, usa Gradle:

```sh
./gradlew build
./gradlew bootRun
```

La API estará disponible en: http://localhost:8080

## Ejecución con Docker

Si prefieres ejecutar el proyecto con Docker, puedes hacerlo usando **Docker Compose**.

1. **Construir y levantar los contenedores:**

```sh
docker-compose up --build
```

Esto iniciará la aplicación junto con la base de datos MySQL.

2. **Verificar los contenedores en ejecución:**

```sh
docker ps
```

3. **Para detener los contenedores:**

```sh
docker-compose down
```

## Endpoints

El servicio expone diferentes endpoints, puedes probarlos con herramientas como **Postman** o **cURL**.

Para ver la documentación de la API, verifica si hay integración con Swagger en:

http://localhost:8080/swagger-ui.html (si está habilitado).

## Notas

- Asegúrate de que el puerto **3306** (para MySQL) y **8080** (para la API) estén disponibles.
- Si tienes problemas con permisos en **Linux/Mac**, usa el siguiente comando antes de ejecutar Gradle:

```sh
chmod +x gradlew
```

- Si hay cambios en `application.yml`, reinicia la aplicación para aplicarlos.