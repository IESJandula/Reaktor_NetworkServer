# 🚩 Reaktor NetworkServer
Este proyecto ha sido creado para el escaneo de equipos de una red.
Su principal funcionalidad es escanear las redes disponibles e intentar encontrar todos los equipos asociados a ella.

Se puede diferenciar entre diferentes tipos de objetos a escanear:
> Red: Aquel que contiene toda la información sobre una red completa, como su Ip de red y sus equipos asociados.

> Equipos : Son aquellos que existen en una Red, contienen sus direcciones ip , su mac asociada (De la tarjeta de red), tipo de quipo y puertos abierto.

> Puertos: Son aquellos que nos brindan el numero de puerto TCP o UDP existentes, contienen el número de puerto, el nombre del servicio al cual el puerto está asociado y mediante el tipo de puerto, podremos discriminar objetos equipos STANDARD o IMPRESORA.

# 📃 REQUISITOS 📃
Este proyecto se basa en ☕Java 17 y aunque luego será capaz de scanear todo tipo de equipos, solamente se puede ejecutar en 💻windows 10.

Se necesita tener Mysql instalado.

### 📇 REQUISITOS PRE-INSTALACIÓN 📇
Este proyecto utiliza la herramienta nmap , y hace uso interno de comandos como:
```bash
nmap -sn <ip>
```
⚠️Por lo tanto, es totalmente necesario instalar primero esta herramienta.⚠️

Crear la base de datos network_server vacía en Mysql, para que pueda arrancar el proyecto correctamente.

# 🔨 INSTALACIÓN 🔨
Solamente se necesita descargar el proyecto y compilarlo.

# 🔌 ENDPOINTS 🔌
### ⚠️¡Basados sobre el puerto 8080!⚠️
Existen varios endpoints:

### 📗 GET
Este responderá con un listado de objetos Red con todos sus equipos y cada equipo con su información propia (En formato JSON)
```bash
http://localhost:8080/net/get/all/data
```
### 📗 GET
Este nos redireccionará a una web donde podremos ver la documentación swagger.
```bash
http://localhost:8080/swagger-ui/index.html
```
### 📗 GET
Este nos proporcionará el documento yaml que podemos visualizar en la web del endpoint anterior
```bash
http://localhost:8080/api-docs.yaml
```
# BBDD
Como se ha comentado en apartados anteriores, se utiliza una bbdd de Mysql llamada network_server.
### MER
![NetworkDatabase](https://github.com/IESJandula/Reaktor_NetworkServer/assets/120125881/21639192-5e76-4c4b-a2d6-59cc0ed4b890)
Esta base de datos está compuesta por cuatro tablas, Red, Equipo, Recurso y Puerto.

>La tabla Red, está formada por su Id (Clave primaria), el nombre de la red, y la ruta de esta.
>Red tiene una relación de 1 a muchos con Equipo.

La tabla Equipo está formada por su Id (Clave primaria), el Sistema Operativo de ese equipo, su dirección Ip, su Mac, el tipo de equipo (Impresora o PC) y la Id de la Red (Clave foránea).

Equipo a su vez tiene una relación 1 a muchos con Recurso y Puerto.

La tabla recurso está formada por el Id del equipo (Clave primaria), un número autogenerado (Clave primaria) y el nombre del recurso.

la tabla Puerto está formada por el Id del equipo (Clave primaria), el número del puerto (Clave primaria) y el nombre del puerto.
