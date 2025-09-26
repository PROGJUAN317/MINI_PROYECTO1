package com.dragonquest.characters;

import com.dragonquest.abilities.Ability;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all characters in the battle system
 * Represents both heroes and monsters with core stats and abilities
 */
public abstract class Character {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int maxMp;
    protected int currentMp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected int level;
    protected List<Ability> abilities;
    protected boolean isAlive;

    public Character(String name, int hp, int mp, int attack, int defense, int speed, int level) {
        this.name = name;
        this.maxHp = hp;
        this.currentHp = hp;
        this.maxMp = mp;
        this.currentMp = mp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.level = level;
        this.abilities = new ArrayList<>();
        this.isAlive = true;
    }

    /**
     * Perform a basic attack on the target
     */
    public int performAttack(Character target) {
        int damage = calculateDamage(target);
        target.takeDamage(damage);
        return damage;
    }

    /**
     * Calculate damage dealt to target based on attack and defense stats
     */
    protected int calculateDamage(Character target) {
        // Basic damage formula: (attacker's attack - target's defense/2) + random variance
        int baseDamage = Math.max(1, this.attack - target.defense / 2);
        int variance = (int) (Math.random() * 10) - 5; // -5 to +5 variance
        return Math.max(1, baseDamage + variance);
    }

    /**
     * Take damage and update HP
     */
    public void takeDamage(int damage) {
        this.currentHp = Math.max(0, this.currentHp - damage);
        if (this.currentHp == 0) {
            this.isAlive = false;
        }
    }

    /**
     * Heal the character
     */
    public void heal(int amount) {
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }

    /**
     * Use MP for abilities
     */
    public boolean useMp(int amount) {
        if (this.currentMp >= amount) {
            this.currentMp -= amount;
            return true;
        }
        return false;
    }

    /**
     * Add an ability to this character
     */
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    /**
     * Get character's turn priority based on speed
     */
    public int getTurnPriority() {
        return this.speed + (int) (Math.random() * 10); // Add randomness to speed
    }

    // Getters
    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentMp() { return currentMp; }
    public int getMaxMp() { return maxMp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public int getLevel() { return level; }
    public List<Ability> getAbilities() { return abilities; }
    public boolean isAlive() { return isAlive; }

    /**
     * Get character status for display
     */
    public String getStatusDisplay() {
        return String.format("%s (Lv.%d): %d/%d HP, %d/%d MP", 
                           name, level, currentHp, maxHp, currentMp, maxMp);
    }

    /**
     * Abstract method for character-specific behavior during their turn
     */
    public abstract String getCharacterType();
}