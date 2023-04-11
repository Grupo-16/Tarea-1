## Nombre
---
Gerson Marihuan | 201973122-k
Francisco Aspe  | 201873013-0 

## Descripcion
--- 

Este es un proyecto de servicio de mensajes en el que los usuarios solo tienen la capacidad de enviar mensajes al servidor. Estos mensajes son cifrados y el servidor se encarga de descifrarlos.


## Requisitos

Maven 3.8.6 o superior 
Java 11
Windows 10, 11

## Instalacion
---

Descarga el repositorio y dentro de la carpeta del repositorio, deberas escribir los siguientes comandos para crear el .jar:

```
mvn clean
mvn compile
mvn package
```

o simplemente ejecutar el archivo build.bat.
Luego para correr el server:

```
java -cp target/main-0.1.0.jar hello.Main server
```
Para correr el cliente:

```
java -cp target/main-0.1.0.jar hello.Main client
```

**Advertencia: Para que todo funcione bien se necesita correr primero el servidor y luego el cliente**


## Como usar
---



## Como contribuir
---

Para contribuir basta con hacer un pull request a la branch development.


## Licencia

Este proyecto esta bajo la licencia [MIT License](https://github.com/Grupo-16/Tarea-1/blob/main/LICENSE)