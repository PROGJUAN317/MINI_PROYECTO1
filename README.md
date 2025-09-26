# 🐉 Dragon Quest VIII: Battle Simulation

Simulación en Java inspirada en Dragon Quest VIII. Implementa un sistema de combate por turnos entre héroes y monstruos con atributos, habilidades y lógica estratégica. Proyecto en consola enfocado en reutilización de código, modularidad y decisiones tácticas.

## 🎮 Características

### Sistema de Combate por Turnos
- **Turnos Dinámicos**: El orden se basa en la estadística de velocidad + factor aleatorio
- **Múltiples Acciones**: Atacar, usar habilidades, defenderse o ver estado
- **Sistema de Daño**: Cálculo basado en ataque vs defensa con variabilidad
- **Condiciones de Victoria**: La batalla termina cuando todos los héroes o monstruos son derrotados

### Cuatro Clases de Héroes Únicas
1. **⚔️ Guerrero (Warrior)**: Alto daño y defensa, especialista en combate cuerpo a cuerpo
   - *Habilidades*: Slash, Shield Bash, War Cry
   - *Especialidad*: Ataques críticos y resistencia superior

2. **🔮 Mago (Mage)**: Maestro de la magia elemental con ataques devastadores
   - *Habilidades*: Fireball, Ice Shard, Lightning Bolt
   - *Especialidad*: Alto daño mágico y MP extendido

3. **✨ Sanador (Healer)**: Soporte del equipo con magia curativa
   - *Habilidades*: Heal, Group Heal, Blessing
   - *Especialidad*: Curación y buffs para aliados

4. **🗡️ Pícaro (Rogue)**: Atacante rápido con alta probabilidad de críticos
   - *Habilidades*: Backstab, Poison Strike, Shadow Step
   - *Especialidad*: Velocidad superior y ataques furtivos

### Cinco Tipos de Monstruos
- **👺 Goblin**: Rápido pero frágil, ataque veloz
- **💪 Orc**: Fuerte y resistente, ataque brutal
- **💀 Skeleton**: No-muerto con magia oscura
- **🐲 Dragon**: Jefe épico con ataques devastadores
- **🟢 Slime**: Criatura equilibrada y resistente

### Niveles de Dificultad
1. **Fácil**: Bandidos del Bosque (Goblins & Slimes)
2. **Medio**: Grupo de Asalto Orc
3. **Difícil**: Legión No-Muerta (Skeletons)
4. **Épico**: Dragón Ancestral
5. **Aleatorio**: Encuentro generado dinámicamente

## 🚀 Cómo Ejecutar

### Opción 1: Script Automático
```bash
./run.sh
```

### Opción 2: Compilación Manual
```bash
# Compilar
javac -cp src/main/java src/main/java/com/dragonquest/abilities/Ability.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Character.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Hero.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Monster.java
javac -cp src/main/java src/main/java/com/dragonquest/battle/Battle.java
javac -cp src/main/java src/main/java/com/dragonquest/DragonQuestBattle.java

# Ejecutar
java -cp src/main/java com.dragonquest.DragonQuestBattle
```

### Ejecutar Pruebas
```bash
javac -cp src/main/java src/main/java/com/dragonquest/BattleTest.java
java -cp src/main/java com.dragonquest.BattleTest
```

## 🏗️ Arquitectura del Código

### Estructura de Paquetes
```
src/main/java/com/dragonquest/
├── DragonQuestBattle.java          # Clase principal
├── BattleTest.java                 # Pruebas del sistema
├── abilities/
│   └── Ability.java                # Sistema de habilidades
├── battle/
│   └── Battle.java                 # Lógica de combate por turnos
└── characters/
    ├── Character.java              # Clase base para personajes
    ├── Hero.java                   # Héroes controlados por el jugador
    └── Monster.java                # Enemigos con IA
```

### Características Técnicas
- **Programación Orientada a Objetos**: Herencia, polimorfismo y encapsulación
- **Patrón Strategy**: Diferentes comportamientos de IA para monstruos
- **Sistema Modular**: Componentes independientes y reutilizables
- **Interfaz Clara**: Salida de consola rica y fácil de entender

## 🎯 Mecánicas de Juego

### Sistema de Estadísticas
- **HP (Puntos de Vida)**: Determina cuánto daño puede recibir
- **MP (Puntos de Magia)**: Necesarios para usar habilidades especiales
- **Ataque**: Influye en el daño infligido
- **Defensa**: Reduce el daño recibido
- **Velocidad**: Determina el orden de turnos

### Fórmulas de Combate
- **Daño Básico**: `max(1, Ataque - Defensa/2) + varianza(-5 a +5)`
- **Críticos**: 25% para Pícaros, 10% para otros héroes
- **Orden de Turnos**: `Velocidad + aleatorio(0-10)`

### Inteligencia Artificial
Los monstruos tienen diferentes niveles de agresividad que afectan sus decisiones:
- **Alta Agresividad** (Orcs): Prefieren ataques directos
- **Media Agresividad** (Dragons): Balance entre ataque y habilidades
- **Baja Agresividad** (Skeletons): Más estratégicos, usan más magia

## 🏆 Objetivos Cumplidos

✅ **Sistema de combate por turnos completo**  
✅ **Cuatro héroes únicos con habilidades especializadas**  
✅ **Múltiples tipos de enemigos con IA diferenciada**  
✅ **Interfaz de consola clara y comprensible**  
✅ **Arquitectura modular y orientada a objetos**  
✅ **Mecánicas balanceadas y estratégicas**  
✅ **Código reutilizable y extensible**

## 👨‍💻 Desarrollo

Este proyecto demuestra:
- Implementación de patrones de diseño
- Gestión de estados complejos
- Desarrollo de IA básica para juegos
- Diseño de interfaces de usuario en consola
- Programación orientada a objetos avanzada
