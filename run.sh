#!/bin/bash

# Compile the Dragon Quest Battle Simulation
echo "Compiling Dragon Quest Battle Simulation..."

cd "$(dirname "$0")"

# Clean previous compilation
find . -name "*.class" -delete

# Compile all Java files
javac -cp src/main/java src/main/java/com/dragonquest/abilities/Ability.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Character.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Hero.java
javac -cp src/main/java src/main/java/com/dragonquest/characters/Monster.java
javac -cp src/main/java src/main/java/com/dragonquest/battle/Battle.java
javac -cp src/main/java src/main/java/com/dragonquest/DragonQuestBattle.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Starting Dragon Quest Battle Simulation..."
    echo "========================================"
    
    # Run the game
    java -cp src/main/java com.dragonquest.DragonQuestBattle
else
    echo "Compilation failed!"
    exit 1
fi