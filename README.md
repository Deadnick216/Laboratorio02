# Laboratorio 02: Chequeo de Conformidad Arquitectural

**Asignatura**: Patrones de Software y Programación  
**Fecha de entrega**: 28-05-2025
**Integrantes**: Nicolas Vicentelo y Sebastian Reyes

---

## Descripción

Este proyecto es una herramienta de análisis estático basada en ArchUnit que verifica si un sistema Java cumple con una arquitectura en capas:

1. **UI** (presentación)  
2. **Service** (lógica de negocio)  
3. **DAO** (persistencia)

Las reglas de dependencia son:

- UI → solo puede acceder a Service.  
- Service → solo puede acceder a DAO.  
- DAO → no puede acceder ni a Service ni a UI.  
- No se permiten dependencias cruzadas no planeadas.

La herramienta reporta para cada violación:  
- la clase afectada  
- la línea  
- el detalle de la dependencia no permitida

---

## Requisitos

- Java 1.8+  
- Maven 3.6+

---

## Compilar y empaquetar

```bash
git clone <tu-repo>
cd Laboratorio02

mvn clean package
java -jar target/Laboratorio02-1.0-SNAPSHOT.jar target/classes
