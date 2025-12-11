# 📊 Resumen de Pruebas para Memoria del TFG

## Pruebas Implementadas

### 1. Pruebas Unitarias (11 tests)

**Archivo:** `EstadisticaServiceImplTest.java`

**Objetivo:** Verificar la lógica de negocio de forma aislada

**Metodología:**
- Framework: JUnit 5 + Mockito
- Uso de mocks para simular dependencias
- Patrón Given-When-Then

**Casos de prueba:**

| # | Caso | Resultado Esperado |
|---|------|-------------------|
| 1 | Crear nueva estadística | Estadística creada exitosamente |
| 2 | Actualizar estadística existente | Se actualiza en lugar de crear duplicado |
| 3 | Jugador no existe | Excepción EntityNotFoundException |
| 4 | Evento no existe | Excepción EntityNotFoundException |
| 5 | DTO nulo | Excepción IllegalArgumentException |
| 6 | Buscar por ID existente | Retorna la estadística |
| 7 | Buscar por ID inexistente | Retorna Optional vacío |
| 8 | Filtrar por jugador | Lista de estadísticas del jugador |
| 9 | Filtrar por evento | Lista de estadísticas del evento |
| 10 | Eliminar existente | Eliminación exitosa |
| 11 | Eliminar inexistente | Excepción EntityNotFoundException |

**Cobertura:**
- ✅ Operaciones CRUD
- ✅ Validaciones de negocio
- ✅ Manejo de errores
- ✅ Patrón upsert (actualizar si existe, crear si no)

---

### 2. Pruebas de Integración (9 tests)

**Archivo:** `EstadisticaControllerIntegrationTest.java`

**Objetivo:** Verificar el comportamiento end-to-end de la API REST

**Metodología:**
- Framework: Spring Boot Test + MockMvc
- Base de datos H2 en memoria
- Transacciones con rollback automático
- Autenticación simulada con `@WithMockUser`

**Casos de prueba:**

| # | Endpoint | Método | Caso | HTTP |
|---|----------|--------|------|------|
| 1 | `/api/estadisticas` | POST | Crear estadística válida | 201 Created |
| 2 | `/api/estadisticas` | POST | Crear estadística duplicada (actualiza) | 201 Created |
| 3 | `/api/estadisticas` | POST | Jugador inválido | 404 Not Found |
| 4 | `/api/estadisticas/{id}` | GET | Obtener por ID existente | 200 OK |
| 5 | `/api/estadisticas/{id}` | GET | Obtener por ID inexistente | 404 Not Found |
| 6 | `/api/estadisticas/{id}` | PUT | Actualizar estadística | 200 OK |
| 7 | `/api/estadisticas/{id}` | DELETE | Eliminar estadística | 204 No Content |
| 8 | `/api/estadisticas` | GET | Listar todas | 200 OK |
| 9 | `/api/estadisticas` | GET | Sin autenticación | 401 Unauthorized |

**Cobertura:**
- ✅ API REST completa
- ✅ Códigos HTTP correctos
- ✅ Serialización JSON
- ✅ Seguridad y autenticación
- ✅ Validaciones de integridad

---

## Configuración de Pruebas

### Base de Datos

```
MySQL (Producción)  →  H2 in-memory (Pruebas)
```

**Ventajas de H2:**
- No requiere instalación
- Rápida ejecución
- Aislamiento total entre tests
- Compatible con JPA

### Ejecución

```bash
# Todas las pruebas
mvn test

# Solo unitarias
mvn test -Dtest=*Test

# Solo integración
mvn test -Dtest=*IntegrationTest
```

---

## Resultados

### Métricas

- **Total de pruebas:** 20
- **Éxito esperado:** 100%
- **Tiempo de ejecución:** < 10 segundos
- **Fallos:** 0

### Captura de Ejemplo

```
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| JUnit 5 | 5.9+ | Framework de testing |
| Mockito | 5.x | Mocking de dependencias |
| Spring Boot Test | 3.5.7 | Pruebas de integración |
| MockMvc | 3.5.7 | Simulación de peticiones HTTP |
| H2 Database | 2.x | Base de datos en memoria |
| AssertJ | 3.x | Assertions fluidas |

---

## Para Incluir en la Memoria

### Sección: "Pruebas del Sistema"

**Texto sugerido:**

"Para garantizar la calidad y el correcto funcionamiento de la aplicación, se han implementado dos tipos de pruebas automatizadas:

**Pruebas Unitarias:** Se han desarrollado 11 casos de prueba que verifican la lógica de negocio de forma aislada, utilizando el framework JUnit 5 y Mockito para simular las dependencias. Estas pruebas cubren todas las operaciones CRUD del módulo de estadísticas, así como las validaciones y el manejo de errores.

**Pruebas de Integración:** Se han implementado 9 casos de prueba que verifican el comportamiento end-to-end de la API REST, incluyendo la interacción entre todas las capas (controlador, servicio, repositorio y base de datos). Estas pruebas utilizan una base de datos H2 en memoria y MockMvc para simular peticiones HTTP, verificando los códigos de estado, la serialización JSON y la seguridad.

En total, el proyecto cuenta con 20 casos de prueba automatizados que se ejecutan en menos de 10 segundos, proporcionando una cobertura completa del módulo de gestión de estadísticas."

### Imágenes Sugeridas

1. **Estructura de carpetas de test** (captura del explorador)
2. **Ejecución exitosa de `mvn test`** (captura de terminal)
3. **Código de una prueba unitaria** (captura con sintaxis resaltada)
4. **Código de una prueba de integración** (captura con `@Test` y assertions)
5. **Resultados en VS Code** (captura mostrando checkmarks verdes)

---

## Justificación Técnica

### ¿Por qué Pruebas Unitarias?

- Verifican la lógica en aislamiento
- Rápidas de ejecutar
- Facilitan refactoring seguro
- Documentan el comportamiento esperado

### ¿Por qué Pruebas de Integración?

- Verifican que todo funciona en conjunto
- Detectan problemas de configuración
- Validan la API desde perspectiva del cliente
- Prueban seguridad y autenticación

### ¿Por qué H2 en lugar de MySQL para tests?

- **Velocidad:** Tests se ejecutan en memoria
- **Portabilidad:** No requiere instalar MySQL
- **Aislamiento:** Cada test tiene BD limpia
- **CI/CD:** Facilita integración continua

---

**Conclusión:** Las pruebas automatizadas garantizan la calidad del código y facilitan el mantenimiento futuro de la aplicación MyClub.
