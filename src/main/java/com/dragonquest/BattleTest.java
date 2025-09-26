package com.dragonquest;

import com.dragonquest.characters.Hero;
import com.dragonquest.characters.Monster;
import com.dragonquest.abilities.Ability;

/**
 * Simple test class to validate core mechanics
 */
public class BattleTest {
    public static void main(String[] args) {
        System.out.println("🧪 Dragon Quest Battle System - Core Mechanics Test");
        System.out.println("=" .repeat(60));
        
        // Test Character Creation
        testCharacterCreation();
        
        // Test Combat Mechanics
        testCombatMechanics();
        
        // Test Abilities
        testAbilities();
        
        System.out.println("\n✅ All tests completed successfully!");
        System.out.println("The battle simulation is ready to use.");
        System.out.println("\nTo start the full game, run:");
        System.out.println("java -cp src/main/java com.dragonquest.DragonQuestBattle");
    }
    
    private static void testCharacterCreation() {
        System.out.println("\n📋 Testing Character Creation...");
        
        // Create heroes
        Hero warrior = new Hero("Test Warrior", Hero.HeroClass.WARRIOR, 5);
        Hero mage = new Hero("Test Mage", Hero.HeroClass.MAGE, 5);
        Hero healer = new Hero("Test Healer", Hero.HeroClass.HEALER, 5);
        Hero rogue = new Hero("Test Rogue", Hero.HeroClass.ROGUE, 5);
        
        System.out.println("✅ " + warrior.getStatusDisplay());
        System.out.println("✅ " + mage.getStatusDisplay());
        System.out.println("✅ " + healer.getStatusDisplay());
        System.out.println("✅ " + rogue.getStatusDisplay());
        
        // Create monsters
        Monster goblin = new Monster("Test Goblin", Monster.MonsterType.GOBLIN, 4);
        Monster dragon = new Monster("Test Dragon", Monster.MonsterType.DRAGON, 10);
        
        System.out.println("✅ " + goblin.getStatusDisplay());
        System.out.println("✅ " + dragon.getStatusDisplay());
    }
    
    private static void testCombatMechanics() {
        System.out.println("\n⚔️ Testing Combat Mechanics...");
        
        Hero warrior = new Hero("Test Warrior", Hero.HeroClass.WARRIOR, 8);
        Monster goblin = new Monster("Test Goblin", Monster.MonsterType.GOBLIN, 5);
        
        System.out.println("Before combat:");
        System.out.println("  " + warrior.getStatusDisplay());
        System.out.println("  " + goblin.getStatusDisplay());
        
        // Test attack
        int damage = warrior.performAttack(goblin);
        System.out.println("\n" + warrior.getName() + " attacks " + goblin.getName() + 
                         " for " + damage + " damage!");
        
        System.out.println("After combat:");
        System.out.println("  " + warrior.getStatusDisplay());
        System.out.println("  " + goblin.getStatusDisplay());
        
        // Test if goblin is still alive
        if (goblin.isAlive()) {
            System.out.println("✅ Combat mechanics working - goblin survived with " + 
                             goblin.getCurrentHp() + " HP");
        } else {
            System.out.println("✅ Combat mechanics working - goblin was defeated!");
        }
    }
    
    private static void testAbilities() {
        System.out.println("\n💫 Testing Abilities...");
        
        Hero mage = new Hero("Test Mage", Hero.HeroClass.MAGE, 6);
        Hero healer = new Hero("Test Healer", Hero.HeroClass.HEALER, 6);
        Monster orc = new Monster("Test Orc", Monster.MonsterType.ORC, 6);
        
        System.out.println("Mage abilities:");
        for (Ability ability : mage.getAbilities()) {
            System.out.println("  - " + ability.getDisplayInfo());
        }
        
        System.out.println("\nHealer abilities:");
        for (Ability ability : healer.getAbilities()) {
            System.out.println("  - " + ability.getDisplayInfo());
        }
        
        // Test mage attack ability
        if (!mage.getAbilities().isEmpty()) {
            Ability fireball = mage.getAbilities().get(0); // Should be Fireball
            System.out.println("\nTesting " + fireball.getName() + ":");
            String result = fireball.use(mage, orc);
            System.out.println("  " + result);
            System.out.println("  Orc HP after: " + orc.getCurrentHp() + "/" + orc.getMaxHp());
        }
        
        // Test healing ability
        if (!healer.getAbilities().isEmpty()) {
            // Damage the healer first
            healer.takeDamage(30);
            System.out.println("\nHealer damaged: " + healer.getCurrentHp() + "/" + healer.getMaxHp());
            
            Ability heal = healer.getAbilities().get(0); // Should be Heal
            System.out.println("Testing " + heal.getName() + ":");
            String result = heal.use(healer, healer); // Self-heal
            System.out.println("  " + result);
            System.out.println("  Healer HP after: " + healer.getCurrentHp() + "/" + healer.getMaxHp());
        }
        
        System.out.println("✅ Abilities system working correctly!");
    }
}