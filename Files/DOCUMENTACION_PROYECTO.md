# Sistema de Batallas RPG

Un sistema completo de batallas por turnos desarrollado en Java que simula combates entre equipos de héroes y enemigos con mecánicas avanzadas de RPG.

## Descripción General

Este proyecto implementa un sistema de batallas RPG con las siguientes características principales:

- **Sistema de Personajes**: Clase abstracta base con herencia para Héroes y Enemigos
- **Sistema de Interfaces**: Comportamientos especializados (Agresivo, Sanador, Tanque)
- **Mecánicas Avanzadas**: Defensa combinada, provocación, curación y habilidades especiales
- **Batalla Manual**: Control completo del jugador sobre las acciones de cada héroe
- **Generación Aleatoria**: Enemigos con tipos y estadísticas aleatorias

## Arquitectura del Sistema

### Jerarquía de Clases

```
Personaje (abstracta)
├── Heroe (implements Sanador, Tanque)
└── Enemigo (implements Agresivo)
```

### Interfaces de Comportamiento

- **Agresivo**: Define comportamientos ofensivos (atacar, habilidades especiales)
- **Sanador**: Define comportamientos de soporte (curar, revivir, restaurar mana)
- **Tanque**: Define comportamientos defensivos (defender, provocar, aumentar defensa)

### Enumeraciones de Tipos

- **Tipo_Heroe**: MAGO, DRUIDA, GUERRERO, PALADIN
- **Tipo_Enemigo**: GOLEM, ORCO, TROLL, NOMUERTO, DRAGON

## Características Principales

### Mecánicas de Combate

1. **Defensa Combinada**: Los tanques pueden defender aliados, sumando sus defensas
2. **Sistema de Provocación**: Los enemigos provocados deben atacar al provocador
3. **Curación y Soporte**: Héroes pueden curar, revivir y restaurar mana
4. **Habilidades Especiales**: Cada tipo de personaje tiene habilidades únicas

### Sistema de Batalla Manual

- Control individual de cada héroe por turno
- Selección de objetivos para cada acción
- Menús interactivos con opciones claras
- Visualización del estado de todos los personajes

### Generación de Personajes

- **Héroes**: Creación interactiva por consola con validación de atributos
- **Enemigos**: Generación aleatoria de tipos y estadísticas dentro de rangos válidos
- **Validación**: Verificación automática de rangos de atributos por tipo

## Documentación de Clases

### Clase Abstracta Personaje

Clase base que define los atributos y comportamientos comunes de todos los personajes.

**Atributos Principales:**
- `nombre`: Identificador del personaje
- `hp`: Puntos de vida actuales
- `mp`: Puntos de mana actuales
- `ataque`: Poder ofensivo
- `defensa`: Capacidad defensiva
- `velocidad`: Rapidez de acción

**Mecánicas Implementadas:**
- Sistema de daño con reducción por defensa
- Gestión de estados (vivo/muerto, defendido, provocado)
- Selección automática de objetivos respetando provocación

### Clase Heroe

Extiende `Personaje` e implementa `Sanador` y `Tanque`.

**Funcionalidades:**
- Creación interactiva por consola
- Habilidades de curación y soporte
- Capacidades de tanque (defensa y provocación)
- Validación de atributos por tipo de héroe

### Clase Enemigo

Extiende `Personaje` e implementa `Agresivo`.

**Funcionalidades:**
- Generación aleatoria de tipos y estadísticas
- Comportamientos agresivos y habilidades especiales
- Ataques automáticos respetando provocación
- Visualización completa de estadísticas

### Clase Batalla

Gestiona los combates entre equipos de 5 héroes vs 5 enemigos.

**Responsabilidades:**
- Administración de equipos de combate
- Creación de personajes individuales o equipos completos
- Visualización del estado de la batalla
- Interfaz para la gestión de turnos

### Clase App

Aplicación principal con interfaz de usuario basada en menús.

**Características:**
- Menú principal interactivo
- Sistema de batalla manual completo
- Gestión de turnos individuales por héroe
- Selección de acciones y objetivos
- Visualización del estado actual de la batalla

## Tipos de Personajes

### Héroes

1. **MAGO**: Especialista en magia ofensiva
   - HP: 50-100, MP: 150-300
   - Ataque: 30-40, Defensa: 10-25
   - Alto daño mágico, baja defensa

2. **DRUIDA**: Heroe de soporte y curación
   - HP: 80-160, MP: 120-250
   - Ataque: 25-40, Defensa: 18-35
   - Habilidades de curación y control

3. **GUERRERO**: Combatiente cuerpo a cuerpo
   - HP: 180-300, MP: 10-60
   - Ataque: 35-55, Defensa: 20-35
   - Alta vida y ataque físico

4. **PALADIN**: Tanque con soporte
   - HP: 100-200, MP: 50-100
   - Ataque: 30-50, Defensa: 25-45
   - Defensa alta, habilidades de protección

### Enemigos

1. **GOLEM**: Tanque defensivo
   - HP: 200-400, MP: 0
   - Ataque: 40-60, Defensa: 30-50

2. **ORCO**: Guerrero balanceado
   - HP: 150-300, MP: 0
   - Ataque: 30-50, Defensa: 20-40

3. **TROLL**: Criatura resistente
   - HP: 180-350, MP: 0
   - Ataque: 35-55, Defensa: 25-45

4. **NOMUERTO**: Atacante ágil
   - HP: 100-250, MP: 0
   - Ataque: 25-45, Defensa: 15-35

5. **DRAGON**: Jefe final
   - HP: 300-600, MP: 0
   - Ataque: 50-80, Defensa: 40-60

## Uso del Sistema

### Compilación

```bash
javac -cp src src/dqs/main/App.java src/dqs/modelos/*.java
```

### Ejecución

```bash
java -cp src dqs.main.App
```

### Flujo de Uso

1. **Crear Equipos**: Generar héroes y enemigos
2. **Mostrar Equipos**: Verificar estadísticas y composición
3. **Iniciar Batalla**: Comenzar combate manual
4. **Control de Turnos**: Seleccionar acciones para cada héroe
5. **Gestión de Objetivos**: Elegir enemigos o aliados según la acción

### Acciones Disponibles en Batalla

- **Atacar**: Causar daño a un enemigo seleccionado
- **Defender**: Proteger a un aliado combinando defensas
- **Provocar**: Forzar a un enemigo a atacar al héroe
- **Curar**: Restaurar HP de un aliado
- **Restaurar Mana**: Recuperar MP de un aliado
- **Eliminar Efectos**: Remover estados negativos
- **Revivir**: Restaurar a un aliado caído

## Características Técnicas

### Validaciones Implementadas

- Rangos de atributos por tipo de personaje
- Verificación de objetivos válidos para cada acción
- Gestión automática de estados (vida/muerte, efectos)
- Entrada de usuario con manejo de errores

### Mecánicas Avanzadas

- **Defensa Combinada**: Tanque + Aliado = Defensa Total
- **Provocación Obligatoria**: Enemigos deben atacar al provocador
- **Daño Mínimo**: Siempre se causa al menos 1 punto de daño
- **Limpieza Automática**: Efectos se remueven cuando corresponde

## Estructura de Archivos

```
src/
└── dqs/
    ├── main/
    │   ├── App.java                    # Aplicación principal
    │   ├── PruebaEnemigos.java         # Pruebas de estadísticas
    │   └── PruebaEnemigosAleatorios.java # Pruebas de aleatoriedad
    ├── modelos/
    │   ├── Personaje.java              # Clase abstracta base
    │   ├── Heroe.java                  # Clase héroe
    │   ├── Enemigo.java                # Clase enemigo
    │   ├── Batalla.java                # Gestión de combates
    │   ├── Agresivo.java               # Interfaz de comportamiento ofensivo
    │   ├── Sanador.java                # Interfaz de comportamiento de soporte
    │   ├── Tanque.java                 # Interfaz de comportamiento defensivo
    │   ├── Tipo_Heroe.java             # Enumeración de tipos de héroe
    │   └── Tipo_Enemigo.java           # Enumeración de tipos de enemigo
    └── utilidades/
        └── Utilidades.txt              # Archivos de apoyo
```

## Autor

Sistema RPG desarrollado como proyecto educativo para demostrar conceptos de programación orientada a objetos en Java.

## Versión

1.0 - Sistema completo de batallas RPG con control manual y mecánicas avanzadas.