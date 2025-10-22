# Sistema de Velocidades - Documentación

## Descripción General

Se ha implementado un sistema de combate basado en velocidades que determina el orden de acción de los personajes en cada turno de batalla.

## Características Implementadas

### 1. Sistema de Orden por Velocidad

- **Ordenamiento**: Los personajes actúan en orden descendente según su atributo `velocidad`
- **Tie-breaker**: En caso de empate en velocidad:
  1. Los héroes actúan antes que los enemigos
  2. Si ambos son del mismo tipo, el orden es aleatorio
- **Recalculación**: El orden se recalcula al inicio de cada turno

### 2. Sistema de Parálisis

Nuevos métodos en la clase `Personaje`:
- `estaParalizado()`: Verifica si el personaje está paralizado
- `serParalizado(int turnos)`: Paraliza al personaje por N turnos
- `removerParalisis()`: Elimina el estado de parálisis
- `decrementarEstadosPorTurno()`: Decrementa la duración de la parálisis

**Comportamiento**: Los personajes paralizados saltan su turno automáticamente.

### 3. Gestión de Estados

- Los estados (parálisis, buffs, debuffs) se decrementan automáticamente al final de cada turno
- La provocación sigue funcionando correctamente con el nuevo sistema

## Clases Modificadas

### Personaje.java

**Nuevos atributos:**
```java
protected boolean esta_paralizado = false;
protected int turnosParalizados = 0;
```

**Nuevos métodos:**
- `boolean estaParalizado()`
- `void serParalizado(int turnos)`
- `void removerParalisis()`
- `void decrementarEstadosPorTurno()`

### Batalla.java

**Nuevos métodos públicos:**
- `boolean ejecutarTurnoPorVelocidad(Heroe[] heroes, Enemigo[] enemigos, boolean modoManual)`

**Métodos auxiliares privados:**
- `List<Personaje> obtenerOrdenPorVelocidad(Heroe[], Enemigo[])`
- `boolean hayHeroesVivos(Heroe[])`
- `boolean hayEnemigosVivos(Enemigo[])`
- `Enemigo elegirEnemigoVivo(Enemigo[])`
- `Heroe elegirHeroeVivo(Heroe[])`

### App.java

**Modificaciones:**
- Actualizado `iniciarBatalla()` para mostrar opciones de modo de batalla
- Nuevos métodos:
  - `simulacionBatallaPorVelocidad(boolean modoManual)`
  - `ejecutarTurnoVelocidadManual()`
  - `hayHeroesVivos()` y `hayEnemigosVivos()` (métodos auxiliares)

## Modos de Batalla

Al iniciar una batalla, ahora puedes elegir entre:

1. **Batalla Tradicional (Manual)**: El modo original donde los héroes actúan primero, luego los enemigos
2. **Batalla por Velocidad (Manual)**: Sistema nuevo donde el jugador controla a cada héroe en su turno correspondiente según velocidad
3. **Batalla por Velocidad (Automático)**: Sistema nuevo completamente automático para pruebas y simulaciones

## Ejemplo de Uso

```java
// Crear personajes con diferentes velocidades
Heroe guerrero = new Heroe("Guerrero", Tipo_Heroe.GUERRERO, 200, 40, 45, 28, 28); // vel=28
Heroe mago = new Heroe("Mago", Tipo_Heroe.MAGO, 80, 200, 38, 15, 11); // vel=11
Enemigo orco = Enemigo.crearEnemigo(Tipo_Enemigo.ORCO, "Orco"); // vel aleatoria

// Crear y configurar batalla
Batalla batalla = new Batalla();
batalla.agregarHeroe(guerrero, 0);
batalla.agregarHeroe(mago, 1);
batalla.agregarEnemigo(orco, 0);

// Ejecutar turno por velocidad (modo automático)
batalla.ejecutarTurnoPorVelocidad(
    batalla.getEquipoHeroes(),
    batalla.getEquipoEnemigos(),
    false
);

// Probar parálisis
guerrero.serParalizado(2); // Paralizado por 2 turnos
System.out.println(guerrero.estaParalizado()); // true

// Los estados se decrementan automáticamente al final del turno
```

## Orden de Ejecución en un Turno

1. **Obtener orden**: Se crea una lista ordenada de todos los participantes vivos por velocidad
2. **Mostrar orden**: Se imprime el orden de acción del turno
3. **Ejecutar acciones**: Para cada personaje en orden:
   - Si está muerto o paralizado, se salta
   - Si es héroe: en modo manual pide acción, en automático ataca al primer enemigo vivo
   - Si es enemigo: si está provocado ataca al provocador, sino ataca a un héroe aleatorio
4. **Decrementar estados**: Se decrementan los contadores de todos los estados activos
5. **Verificar victoria**: Se comprueba si todos los héroes o enemigos han sido derrotados

## Integración con Mecánicas Existentes

### Provocación
- Los enemigos provocados atacan obligatoriamente al provocador
- La provocación se verifica durante el turno del enemigo
- Si el provocador muere, la provocación se elimina automáticamente

### Defensa
- La defensa combinada del tanque sigue funcionando normalmente
- No afecta el orden de turno

### Curación y Habilidades
- Todas las habilidades (curar, revivir, buffs) funcionan con el nuevo sistema
- Se ejecutan en el turno correspondiente según la velocidad del personaje

## Pruebas Realizadas

✅ Ordenamiento correcto por velocidad
✅ Tie-breaker (héroes antes que enemigos)
✅ Sistema de parálisis (2 turnos probados)
✅ Decrementación automática de estados
✅ Provocación integrada con velocidad
✅ Compatibilidad con todas las mecánicas existentes

## Notas de Implementación

- El sistema utiliza `java.util.Collections.sort()` para ordenar eficientemente
- La complejidad temporal del ordenamiento es O(n log n) donde n = número de participantes vivos
- Los estados se manejan de forma independiente para cada personaje
- El sistema es compatible con futuras expansiones (más estados, efectos especiales, etc.)
