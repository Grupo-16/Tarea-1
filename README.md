## Nombre
---
Gerson Marihuan | 201973122-k
Francisco Aspe  | 201873013-0 

## Descripcion
--- 

Este es un proyecto de servicio de mensajes en consola en el que los usuarios solo tienen la capacidad de enviar mensajes al servidor. Estos mensajes son cifrados en el lado del cliente y en el lado del servidor son descifrados.

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
o simplemente ejecutar el archivo build.bat

## Como usar
---

Una vez hecha la instalacion el archivo .jar generado se puede encontrar en la carpeta target. Luego para correr el servidor debes ejecutar el siguiente comando en una consola abierta en el root del proyecto:

```
java -cp target/main-0.1.0.jar hello.Main server
```
Una vez el servidor este corriendo, deberas ejecutar el siguiente comando en una consola aparte correr el cliente:

```
java -cp target/main-0.1.0.jar hello.Main client
```
**Advertencia: Para que todo funcione bien se necesita correr primero el servidor y luego el cliente**


## Como contribuir
---

Para contribuir basta con crear una issue o un pull request.


## Licencia

Este proyecto esta bajo la licencia [MIT License](https://github.com/Grupo-16/Tarea-1/blob/main/LICENSE)