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

