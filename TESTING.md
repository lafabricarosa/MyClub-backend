# 🧪 Documentación de Pruebas - MyClub API

Este documento describe las pruebas implementadas en el proyecto MyClub para el TFG.

## 📋 Índice

1. [Tipos de Pruebas](#tipos-de-pruebas)
2. [Estructura de Pruebas](#estructura-de-pruebas)
3. [Configuración](#configuración)
4. [Ejecución de Pruebas](#ejecución-de-pruebas)
5. [Cobertura de Pruebas](#cobertura-de-pruebas)

---

## 🎯 Tipos de Pruebas

### 1. Pruebas Unitarias

Las **pruebas unitarias** verifican el comportamiento de componentes individuales de forma aislada, usando mocks para las dependencias.

**Características:**
- Rápidas de ejecutar
- No requieren base de datos
- Usan Mockito para simular dependencias
- Verifican la lógica de negocio

**Ejemplo:** `EstadisticaServiceImplTest.java`

```java
@ExtendWith(MockitoExtension.class)
class EstadisticaServiceImplTest {
    @Mock
    private EstadisticaRepository estadisticaRepository;

    @InjectMocks
    private EstadisticaServiceImpl estadisticaService;

    @Test
    void testSave_CuandoEsNuevaEstadistica_DebeCrearla() {
        // Prueba aislada del servicio
    }
}
```

### 2. Pruebas de Integración

Las **pruebas de integración** verifican el comportamiento end-to-end de la aplicación, incluyendo la interacción entre múltiples capas.

**Características:**
- Prueban el stack completo: Controlador → Servicio → Repositorio → BD
- Usan base de datos H2 en memoria
- Simulan peticiones HTTP reales
- Verifican seguridad y autenticación

**Ejemplo:** `EstadisticaControllerIntegrationTest.java`

```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class EstadisticaControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@test.com", roles = "ENTRENADOR")
    void testCrearEstadistica_DebeRetornar201() throws Exception {
        // Prueba completa de la API
    }
}
```

---

## 📁 Estructura de Pruebas

```
src/test/java/com/gestiondeportiva/api/
├── services/
│   └── EstadisticaServiceImplTest.java          # Pruebas unitarias del servicio
├── controllers/
│   └── EstadisticaControllerIntegrationTest.java # Pruebas de integración del API
└── ApiApplicationTests.java                      # Prueba de contexto básico

src/test/resources/
└── application-test.properties                   # Configuración para pruebas
```

---

## ⚙️ Configuración

### Base de Datos para Pruebas

Las pruebas utilizan **H2 Database** (base de datos en memoria) en lugar de MySQL:

```properties
# application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

**Ventajas:**
- ✅ No requiere instalación de MySQL
- ✅ Rápida (todo en memoria)
- ✅ Se recrea en cada ejecución (tests aislados)
- ✅ Compatible con JPA/Hibernate

### Dependencias

```xml
<!-- H2 para pruebas -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Boot Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🚀 Ejecución de Pruebas

### Ejecutar todas las pruebas

```bash
mvn test
```

### Ejecutar solo pruebas unitarias

```bash
mvn test -Dtest=*Test
```

### Ejecutar solo pruebas de integración

```bash
mvn test -Dtest=*IntegrationTest
```

### Ejecutar una clase específica

```bash
mvn test -Dtest=EstadisticaServiceImplTest
```

### Ejecutar un test específico

```bash
mvn test -Dtest=EstadisticaServiceImplTest#testSave_CuandoEsNuevaEstadistica_DebeCrearla
```

### Desde VS Code

1. Abre el archivo de test
2. Click en el icono ▶️ junto al método de test
3. O usa `Ctrl+Shift+P` → "Java: Run Tests"

---

## 📊 Cobertura de Pruebas

### Casos de Prueba Implementados

#### EstadisticaServiceImplTest (Unitarias)

| Test | Descripción | Objetivo |
|------|-------------|----------|
| `testSave_CuandoEsNuevaEstadistica_DebeCrearla` | Crear nueva estadística | Verificar creación exitosa |
| `testSave_CuandoYaExiste_DebeActualizarla` | Actualizar estadística existente | Verificar patrón upsert |
| `testSave_CuandoJugadorNoExiste_DebeLanzarExcepcion` | Jugador inválido | Verificar validación |
| `testSave_CuandoEventoNoExiste_DebeLanzarExcepcion` | Evento inválido | Verificar validación |
| `testSave_CuandoDTOEsNulo_DebeLanzarExcepcion` | DTO nulo | Verificar validación |
| `testFindById_CuandoExiste_DebeRetornarla` | Buscar por ID existente | Verificar búsqueda |
| `testFindById_CuandoNoExiste_DebeRetornarVacio` | Buscar por ID inexistente | Verificar manejo de ausencia |
| `testFindByJugadorId_DebeRetornarLista` | Filtrar por jugador | Verificar filtrado |
| `testFindByEventoId_DebeRetornarLista` | Filtrar por evento | Verificar filtrado |
| `testDeleteById_CuandoExiste_DebeEliminarla` | Eliminar existente | Verificar eliminación |
| `testDeleteById_CuandoNoExiste_DebeLanzarExcepcion` | Eliminar inexistente | Verificar validación |

**Total: 11 pruebas unitarias**

#### EstadisticaControllerIntegrationTest (Integración)

| Test | Descripción | Endpoint | Objetivo |
|------|-------------|----------|----------|
| `testCrearEstadistica_DebeRetornar201` | Crear estadística | `POST /api/estadisticas` | Verificar creación HTTP 201 |
| `testCrearEstadisticaDuplicada_DebeActualizarla` | Crear duplicada | `POST /api/estadisticas` | Verificar actualización automática |
| `testCrearEstadisticaConJugadorInvalido_DebeRetornar404` | Jugador inválido | `POST /api/estadisticas` | Verificar HTTP 404 |
| `testObtenerEstadisticaPorId_DebeRetornarEstadistica` | Obtener por ID | `GET /api/estadisticas/{id}` | Verificar lectura HTTP 200 |
| `testObtenerEstadisticaInexistente_DebeRetornar404` | ID inexistente | `GET /api/estadisticas/{id}` | Verificar HTTP 404 |
| `testActualizarEstadistica_DebeRetornar200` | Actualizar | `PUT /api/estadisticas/{id}` | Verificar actualización HTTP 200 |
| `testEliminarEstadistica_DebeRetornar204` | Eliminar | `DELETE /api/estadisticas/{id}` | Verificar eliminación HTTP 204 |
| `testListarTodasLasEstadisticas_DebeRetornarLista` | Listar todas | `GET /api/estadisticas` | Verificar listado |
| `testAccesoSinAutenticacion_DebeRetornar401` | Sin autenticación | `GET /api/estadisticas` | Verificar seguridad HTTP 401 |

**Total: 9 pruebas de integración**

### Funcionalidades Cubiertas

✅ **CRUD Completo**
- Crear (POST)
- Leer (GET)
- Actualizar (PUT)
- Eliminar (DELETE)

✅ **Validaciones**
- Entidades relacionadas (Jugador, Evento)
- Datos requeridos
- Restricciones de integridad

✅ **Seguridad**
- Autenticación JWT
- Autorización por roles

✅ **Casos Especiales**
- Patrón upsert (actualizar si existe, crear si no)
- Manejo de duplicados
- Respuestas HTTP correctas

---

## 📈 Métricas de Calidad

### Resultados Esperados

Todas las pruebas deben pasar:

```
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
```

### Criterios de Aceptación

- ✅ 100% de las pruebas pasan
- ✅ Sin errores de compilación
- ✅ Sin warnings críticos
- ✅ Cobertura > 80% en servicios críticos

---

## 🔍 Ejemplo de Salida

```bash
$ mvn test

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.gestiondeportiva.api.services.EstadisticaServiceImplTest
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Running com.gestiondeportiva.api.controllers.EstadisticaControllerIntegrationTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

---

## 📝 Buenas Prácticas Implementadas

### Nomenclatura

- ✅ **Formato Given-When-Then**: Estructura clara de las pruebas
- ✅ **Nombres descriptivos**: `testSave_CuandoEsNuevaEstadistica_DebeCrearla`
- ✅ **Verbos claros**: `Debe`, `Cuando`, `Si`

### Organización

- ✅ **Separación por tipo**: Unitarias vs Integración
- ✅ **Aislamiento**: Cada test es independiente
- ✅ **Setup consistente**: `@BeforeEach` para inicialización

### Cobertura

- ✅ **Happy paths**: Casos de éxito
- ✅ **Edge cases**: Casos límite
- ✅ **Error paths**: Manejo de errores

---

## 🎓 Para el TFG

### Documentación para la Memoria

Este proyecto incluye:

1. **Pruebas Unitarias** (11 tests)
   - Verifican lógica de negocio aislada
   - Usan mocks para dependencias
   - Rápidas y deterministas

2. **Pruebas de Integración** (9 tests)
   - Verifican comportamiento end-to-end
   - Prueban API REST completa
   - Incluyen seguridad y validaciones

3. **Total: 20 casos de prueba**
   - Cobertura de CRUD completo
   - Validaciones exhaustivas
   - Manejo de errores

### Capturas de Pantalla Sugeridas

1. Ejecución exitosa de `mvn test`
2. Tests pasando en VS Code
3. Estructura de carpetas de test
4. Ejemplo de un test con breakpoint (debugging)

---

## 🚨 Troubleshooting

### Error: "No tests found"

Asegúrate de que los archivos terminan en `Test.java` o `IntegrationTest.java`

### Error: "Could not create H2 database"

Verifica que H2 está en el `pom.xml` con `<scope>test</scope>`

### Tests fallan por timeouts

Aumenta el timeout en `application-test.properties`:
```properties
spring.test.mockmvc.timeout=10000
```

---

**Autor:** Proyecto MyClub - TFG
**Fecha:** Diciembre 2024
**Framework:** Spring Boot 3.5.7 + JUnit 5 + Mockito
