package com.dragonquest.characters;

import com.dragonquest.abilities.Ability;

/**
 * Represents a hero character that can be controlled by the player
 */
public class Hero extends Character {
    private HeroClass heroClass;
    private String specialty;

    public enum HeroClass {
        WARRIOR,    // High attack and defense, low MP
        MAGE,       // High MP and magical abilities, low defense
        HEALER,     // Healing abilities and support magic
        ROGUE       // High speed and critical hits, balanced stats
    }

    public Hero(String name, HeroClass heroClass, int level) {
        super(name, 0, 0, 0, 0, 0, level); // Will be set by class
        this.heroClass = heroClass;
        initializeByClass();
        setupAbilities();
    }

    /**
     * Initialize hero stats based on their class
     */
    private void initializeByClass() {
        int baseHp = 80 + (level * 12);
        int baseMp = 30 + (level * 8);
        int baseAttack = 25 + (level * 3);
        int baseDefense = 20 + (level * 2);
        int baseSpeed = 15 + (level * 2);

        switch (heroClass) {
            case WARRIOR:
                this.maxHp = baseHp + 30;
                this.maxMp = baseMp - 10;
                this.attack = baseAttack + 8;
                this.defense = baseDefense + 6;
                this.speed = baseSpeed - 2;
                this.specialty = "High damage and defense";
                break;
            case MAGE:
                this.maxHp = baseHp - 15;
                this.maxMp = baseMp + 25;
                this.attack = baseAttack - 3;
                this.defense = baseDefense - 5;
                this.speed = baseSpeed + 3;
                this.specialty = "Powerful magical attacks";
                break;
            case HEALER:
                this.maxHp = baseHp + 10;
                this.maxMp = baseMp + 15;
                this.attack = baseAttack - 5;
                this.defense = baseDefense + 3;
                this.speed = baseSpeed + 4;
                this.specialty = "Healing and support magic";
                break;
            case ROGUE:
                this.maxHp = baseHp - 5;
                this.maxMp = baseMp + 5;
                this.attack = baseAttack + 3;
                this.defense = baseDefense - 2;
                this.speed = baseSpeed + 8;
                this.specialty = "High speed and critical hits";
                break;
        }

        // Set current HP and MP to max
        this.currentHp = this.maxHp;
        this.currentMp = this.maxMp;
    }

    /**
     * Setup class-specific abilities
     */
    private void setupAbilities() {
        switch (heroClass) {
            case WARRIOR:
                addAbility(new Ability("Slash", "A powerful sword attack", 5, 
                          Ability.AbilityType.ATTACK, 15));
                addAbility(new Ability("Shield Bash", "Stuns the enemy", 8, 
                          Ability.AbilityType.SPECIAL, 10));
                addAbility(new Ability("War Cry", "Intimidates enemies", 12, 
                          Ability.AbilityType.DEBUFF, 0));
                break;
            case MAGE:
                addAbility(new Ability("Fireball", "Deals fire damage", 12, 
                          Ability.AbilityType.ATTACK, 20));
                addAbility(new Ability("Ice Shard", "Deals ice damage", 10, 
                          Ability.AbilityType.ATTACK, 18));
                addAbility(new Ability("Lightning Bolt", "Deals lightning damage", 15, 
                          Ability.AbilityType.ATTACK, 25));
                break;
            case HEALER:
                addAbility(new Ability("Heal", "Restores HP to an ally", 8, 
                          Ability.AbilityType.HEAL, 25));
                addAbility(new Ability("Group Heal", "Heals all allies", 20, 
                          Ability.AbilityType.HEAL, 15));
                addAbility(new Ability("Blessing", "Boosts ally's stats", 15, 
                          Ability.AbilityType.BUFF, 0));
                break;
            case ROGUE:
                addAbility(new Ability("Backstab", "Critical attack from behind", 6, 
                          Ability.AbilityType.ATTACK, 12));
                addAbility(new Ability("Poison Strike", "Poisonous attack", 10, 
                          Ability.AbilityType.ATTACK, 8));
                addAbility(new Ability("Shadow Step", "Increases evasion", 12, 
                          Ability.AbilityType.BUFF, 0));
                break;
        }
    }

    /**
     * Override attack for potential critical hits (especially for Rogue)
     */
    @Override
    public int performAttack(Character target) {
        int damage = calculateDamage(target);
        
        // Rogue has higher critical hit chance
        double critChance = (heroClass == HeroClass.ROGUE) ? 0.25 : 0.1;
        
        if (Math.random() < critChance) {
            damage = (int) (damage * 1.5);
            System.out.println("Critical hit!");
        }
        
        target.takeDamage(damage);
        return damage;
    }

    @Override
    public String getCharacterType() {
        return "Hero (" + heroClass.toString().toLowerCase() + ")";
    }

    // Getters
    public HeroClass getHeroClass() { return heroClass; }
    public String getSpecialty() { return specialty; }

    @Override
    public String getStatusDisplay() {
        return String.format("%s the %s (Lv.%d): %d/%d HP, %d/%d MP", 
                           name, heroClass.toString().toLowerCase(), level, 
                           currentHp, maxHp, currentMp, maxMp);
    }
}