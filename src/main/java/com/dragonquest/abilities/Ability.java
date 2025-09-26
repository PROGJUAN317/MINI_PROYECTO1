package com.dragonquest.abilities;

import com.dragonquest.characters.Character;

/**
 * Represents a special ability or skill that characters can use in battle
 */
public class Ability {
    protected String name;
    protected String description;
    protected int mpCost;
    protected AbilityType type;
    protected int power;

    public enum AbilityType {
        ATTACK,    // Offensive abilities
        HEAL,      // Healing abilities
        BUFF,      // Stat boosting abilities
        DEBUFF,    // Stat reducing abilities
        SPECIAL    // Other special effects
    }

    public Ability(String name, String description, int mpCost, AbilityType type, int power) {
        this.name = name;
        this.description = description;
        this.mpCost = mpCost;
        this.type = type;
        this.power = power;
    }

    /**
     * Use this ability on a target
     * Returns the result message of the ability use
     */
    public String use(Character user, Character target) {
        if (!user.useMp(mpCost)) {
            return user.getName() + " doesn't have enough MP to use " + name + "!";
        }

        switch (type) {
            case ATTACK:
                return performAttack(user, target);
            case HEAL:
                return performHeal(user, target);
            case BUFF:
                return performBuff(user, target);
            case DEBUFF:
                return performDebuff(user, target);
            case SPECIAL:
                return performSpecial(user, target);
            default:
                return user.getName() + " uses " + name + " but nothing happens!";
        }
    }

    private String performAttack(Character user, Character target) {
        // Special attack with ability power modifier
        int damage = Math.max(1, (user.getAttack() + power) - target.getDefense() / 2);
        int variance = (int) (Math.random() * 6) - 3; // -3 to +3 variance
        damage = Math.max(1, damage + variance);
        
        target.takeDamage(damage);
        return user.getName() + " uses " + name + " on " + target.getName() + 
               " for " + damage + " damage!";
    }

    private String performHeal(Character user, Character target) {
        int healAmount = power + (int) (Math.random() * 10); // Base healing + random
        target.heal(healAmount);
        return user.getName() + " uses " + name + " on " + target.getName() + 
               " and restores " + healAmount + " HP!";
    }

    private String performBuff(Character user, Character target) {
        // For now, just show the effect - could implement temporary stat boosts
        return user.getName() + " uses " + name + " on " + target.getName() + 
               "! " + target.getName() + " feels empowered!";
    }

    private String performDebuff(Character user, Character target) {
        // For now, just show the effect - could implement temporary stat reductions
        return user.getName() + " uses " + name + " on " + target.getName() + 
               "! " + target.getName() + " feels weakened!";
    }

    private String performSpecial(Character user, Character target) {
        return user.getName() + " uses " + name + "! " + description;
    }

    /**
     * Check if the ability can be used by the character
     */
    public boolean canUse(Character user) {
        return user.getCurrentMp() >= mpCost && user.isAlive();
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMpCost() { return mpCost; }
    public AbilityType getType() { return type; }
    public int getPower() { return power; }

    public String getDisplayInfo() {
        return name + " (MP: " + mpCost + ") - " + description;
    }
}