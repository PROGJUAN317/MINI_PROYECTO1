package com.dragonquest.characters;

import com.dragonquest.abilities.Ability;

/**
 * Represents a monster/enemy character with AI behavior
 */
public class Monster extends Character {
    private MonsterType monsterType;
    private String description;
    private double aggressiveness; // 0.0 to 1.0, affects AI behavior

    public enum MonsterType {
        GOBLIN,      // Weak but fast
        ORC,         // Strong but slow
        SKELETON,    // Undead with magical resistance
        DRAGON,      // Powerful boss-type enemy
        SLIME        // Balanced basic enemy
    }

    public Monster(String name, MonsterType monsterType, int level) {
        super(name, 0, 0, 0, 0, 0, level);
        this.monsterType = monsterType;
        initializeByType();
        setupAbilities();
    }

    /**
     * Initialize monster stats based on type
     */
    private void initializeByType() {
        int baseHp = 60 + (level * 10);
        int baseMp = 20 + (level * 5);
        int baseAttack = 20 + (level * 2);
        int baseDefense = 15 + (level * 1);
        int baseSpeed = 12 + (level * 1);

        switch (monsterType) {
            case GOBLIN:
                this.maxHp = baseHp - 20;
                this.maxMp = baseMp;
                this.attack = baseAttack - 5;
                this.defense = baseDefense - 8;
                this.speed = baseSpeed + 12;
                this.aggressiveness = 0.8;
                this.description = "A small, quick creature that strikes fast";
                break;
            case ORC:
                this.maxHp = baseHp + 25;
                this.maxMp = baseMp - 10;
                this.attack = baseAttack + 10;
                this.defense = baseDefense + 5;
                this.speed = baseSpeed - 5;
                this.aggressiveness = 0.9;
                this.description = "A brutish warrior with immense strength";
                break;
            case SKELETON:
                this.maxHp = baseHp - 10;
                this.maxMp = baseMp + 10;
                this.attack = baseAttack;
                this.defense = baseDefense + 3;
                this.speed = baseSpeed + 2;
                this.aggressiveness = 0.6;
                this.description = "An undead warrior with dark magic";
                break;
            case DRAGON:
                this.maxHp = baseHp + 50;
                this.maxMp = baseMp + 30;
                this.attack = baseAttack + 15;
                this.defense = baseDefense + 10;
                this.speed = baseSpeed + 5;
                this.aggressiveness = 0.7;
                this.description = "A mighty dragon with devastating attacks";
                break;
            case SLIME:
                this.maxHp = baseHp;
                this.maxMp = baseMp;
                this.attack = baseAttack - 2;
                this.defense = baseDefense;
                this.speed = baseSpeed;
                this.aggressiveness = 0.5;
                this.description = "A gelatinous creature with surprising resilience";
                break;
        }

        this.currentHp = this.maxHp;
        this.currentMp = this.maxMp;
    }

    /**
     * Setup monster-specific abilities
     */
    private void setupAbilities() {
        switch (monsterType) {
            case GOBLIN:
                addAbility(new Ability("Quick Strike", "Fast attack with low damage", 3, 
                          Ability.AbilityType.ATTACK, 8));
                addAbility(new Ability("Dirty Fighting", "Underhanded attack", 5, 
                          Ability.AbilityType.ATTACK, 12));
                break;
            case ORC:
                addAbility(new Ability("Brutal Swing", "Heavy weapon attack", 8, 
                          Ability.AbilityType.ATTACK, 18));
                addAbility(new Ability("Intimidate", "Frightens enemies", 6, 
                          Ability.AbilityType.DEBUFF, 0));
                break;
            case SKELETON:
                addAbility(new Ability("Bone Throw", "Ranged bone projectile", 4, 
                          Ability.AbilityType.ATTACK, 10));
                addAbility(new Ability("Dark Magic", "Drains enemy life", 12, 
                          Ability.AbilityType.ATTACK, 15));
                break;
            case DRAGON:
                addAbility(new Ability("Fire Breath", "Devastating flame attack", 15, 
                          Ability.AbilityType.ATTACK, 25));
                addAbility(new Ability("Tail Sweep", "Attacks all enemies", 10, 
                          Ability.AbilityType.ATTACK, 20));
                addAbility(new Ability("Roar", "Terrifying battle cry", 8, 
                          Ability.AbilityType.DEBUFF, 0));
                break;
            case SLIME:
                addAbility(new Ability("Acid Spit", "Corrosive attack", 6, 
                          Ability.AbilityType.ATTACK, 12));
                addAbility(new Ability("Split", "Confuses enemies", 10, 
                          Ability.AbilityType.SPECIAL, 0));
                break;
        }
    }

    /**
     * AI decision making for monster's turn
     * Returns the action the monster will take
     */
    public String makeAIDecision(Character[] targets) {
        // Simple AI logic based on aggressiveness and current state
        
        // If low on HP and has healing ability, consider healing
        if (currentHp < maxHp * 0.3 && hasHealingAbility()) {
            if (Math.random() < (1.0 - aggressiveness)) {
                return "heal";
            }
        }
        
        // Decide between regular attack and special ability
        if (currentMp > 0 && !abilities.isEmpty() && Math.random() < aggressiveness) {
            // Use a random ability that we can afford
            for (Ability ability : abilities) {
                if (ability.canUse(this)) {
                    return "ability:" + ability.getName();
                }
            }
        }
        
        // Default to basic attack
        return "attack";
    }

    /**
     * Select target based on AI preferences
     */
    public Character selectTarget(Character[] targets) {
        // Simple targeting: attack lowest HP alive target
        Character target = null;
        int lowestHp = Integer.MAX_VALUE;
        
        for (Character character : targets) {
            if (character.isAlive() && character.getCurrentHp() < lowestHp) {
                lowestHp = character.getCurrentHp();
                target = character;
            }
        }
        
        return target;
    }

    private boolean hasHealingAbility() {
        return abilities.stream().anyMatch(ability -> 
            ability.getType() == Ability.AbilityType.HEAL);
    }

    @Override
    public String getCharacterType() {
        return "Monster (" + monsterType.toString().toLowerCase() + ")";
    }

    // Getters
    public MonsterType getMonsterType() { return monsterType; }
    public String getDescription() { return description; }
    public double getAggressiveness() { return aggressiveness; }

    @Override
    public String getStatusDisplay() {
        return String.format("%s the %s (Lv.%d): %d/%d HP, %d/%d MP - %s", 
                           name, monsterType.toString().toLowerCase(), level, 
                           currentHp, maxHp, currentMp, maxMp, description);
    }
}