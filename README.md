# Microservicios de Productos e Inventario

Este proyecto demuestra una arquitectura de microservicios simple utilizando Java, Spring Boot y Docker. Los microservicios se comunican entre sí vía HTTP siguiendo el estilo JSON API, e incluyen autenticación básica mediante API Keys.

---

## Arquitectura del Proyecto

El sistema consta de dos microservicios:

### Microservicio 1: `servicio-productos`
- Gestiona los productos con los campos: `id`, `nombre`, `precio`.
- Permite: crear, leer, actualizar, eliminar y listar productos (CRUD completo).

### Microservicio 2: `servicio-inventario`
- Gestiona inventario asociado a productos mediante `producto_id` y `cantidad`.
- Permite consultar disponibilidad y actualizar inventario tras una venta.
- Llama al microservicio de productos para obtener los datos del producto vía HTTP.
- Imprime un mensaje de evento simple en consola cuando se actualiza el inventario.

---

## Instrucciones de Instalación y Ejecución

### 1. Prerrequisitos

- Java 21
- Maven
- Docker y Docker Compose
- PostgreSQL corriendo localmente (con las credenciales del `application.properties`)

### 2. Clonar el repositorio

```bash
git clone https://github.com/joseAlejandroRinconBolanos/prueba-tecnica-microservicios.git
cd prueba-tecnica-microservicios

## Decisiones Técnicas y Justificaciones
Spring Boot: Por su robustez, rapidez y facilidad para crear microservicios REST.

Java 21: Versión LTS moderna y estable.

Docker: Para aislar los servicios y facilitar despliegues.

API Key entre servicios: Implementación básica de seguridad sin depender de OAuth.

Peticiones HTTP entre servicios (no Feign): Simula un entorno real de comunicación por red.

Persistencia con PostgreSQL: Base de datos robusta y ampliamente usada en producción, además de conocimiento y facilidad personal.