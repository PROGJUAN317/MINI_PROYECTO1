package com.dragonquest.battle;

import com.dragonquest.characters.Character;
import com.dragonquest.characters.Hero;
import com.dragonquest.characters.Monster;
import com.dragonquest.abilities.Ability;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages turn-based combat between heroes and monsters
 */
public class Battle {
    private List<Hero> heroes;
    private List<Monster> monsters;
    private List<Character> turnOrder;
    private Scanner scanner;
    private boolean battleEnded;
    private boolean heroesWon;
    private int turnCount;

    public Battle(List<Hero> heroes, List<Monster> monsters) {
        this.heroes = new ArrayList<>(heroes);
        this.monsters = new ArrayList<>(monsters);
        this.scanner = new Scanner(System.in);
        this.battleEnded = false;
        this.heroesWon = false;
        this.turnCount = 0;
        initializeTurnOrder();
    }

    /**
     * Initialize turn order based on character speed
     */
    private void initializeTurnOrder() {
        turnOrder = new ArrayList<>();
        turnOrder.addAll(heroes);
        turnOrder.addAll(monsters);
        
        // Sort by turn priority (speed + randomness)
        turnOrder.sort((a, b) -> Integer.compare(b.getTurnPriority(), a.getTurnPriority()));
    }

    /**
     * Start and manage the battle loop
     */
    public void startBattle() {
        System.out.println("=".repeat(60));
        System.out.println("⚔️  BATTLE BEGINS! ⚔️");
        System.out.println("=".repeat(60));
        
        displayBattleStatus();
        
        while (!battleEnded) {
            turnCount++;
            System.out.println("\n" + "─".repeat(40));
            System.out.println("Turn " + turnCount);
            System.out.println("─".repeat(40));
            
            processTurn();
            checkBattleEnd();
            
            if (!battleEnded) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        displayBattleResult();
    }

    /**
     * Process one complete turn for all characters
     */
    private void processTurn() {
        // Recalculate turn order each turn for dynamic combat
        List<Character> aliveTurnOrder = turnOrder.stream()
                .filter(Character::isAlive)
                .sorted((a, b) -> Integer.compare(b.getTurnPriority(), a.getTurnPriority()))
                .collect(Collectors.toList());
        
        for (Character character : aliveTurnOrder) {
            if (!character.isAlive() || battleEnded) {
                continue;
            }
            
            System.out.println("\n🎯 " + character.getName() + "'s turn!");
            
            if (character instanceof Hero) {
                processHeroTurn((Hero) character);
            } else if (character instanceof Monster) {
                processMonsterTurn((Monster) character);
            }
            
            // Check if battle ended after this character's action
            if (checkBattleEnd()) {
                break;
            }
        }
    }

    /**
     * Process a hero's turn with player input
     */
    private void processHeroTurn(Hero hero) {
        displayCharacterStatus(hero);
        
        System.out.println("\nChoose an action:");
        System.out.println("1. Attack");
        System.out.println("2. Use Ability");
        System.out.println("3. Defend");
        System.out.println("4. View Status");
        
        System.out.print("Enter your choice (1-4): ");
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                performHeroAttack(hero);
                break;
            case "2":
                performHeroAbility(hero);
                break;
            case "3":
                performHeroDefend(hero);
                break;
            case "4":
                displayBattleStatus();
                processHeroTurn(hero); // Give another chance after viewing status
                break;
            default:
                System.out.println("Invalid choice! Defaulting to attack.");
                performHeroAttack(hero);
                break;
        }
    }

    /**
     * Hero performs a basic attack
     */
    private void performHeroAttack(Hero hero) {
        Monster target = selectMonsterTarget();
        if (target != null) {
            int damage = hero.performAttack(target);
            System.out.println(hero.getName() + " attacks " + target.getName() + 
                             " for " + damage + " damage!");
            
            if (!target.isAlive()) {
                System.out.println("💀 " + target.getName() + " has been defeated!");
            }
        }
    }

    /**
     * Hero uses an ability
     */
    private void performHeroAbility(Hero hero) {
        List<Ability> availableAbilities = hero.getAbilities().stream()
                .filter(ability -> ability.canUse(hero))
                .collect(Collectors.toList());
        
        if (availableAbilities.isEmpty()) {
            System.out.println("No abilities available! Performing basic attack instead.");
            performHeroAttack(hero);
            return;
        }
        
        System.out.println("\nAvailable abilities:");
        for (int i = 0; i < availableAbilities.size(); i++) {
            System.out.println((i + 1) + ". " + availableAbilities.get(i).getDisplayInfo());
        }
        
        System.out.print("Choose ability (1-" + availableAbilities.size() + "): ");
        String choice = scanner.nextLine().trim();
        
        try {
            int abilityIndex = Integer.parseInt(choice) - 1;
            if (abilityIndex >= 0 && abilityIndex < availableAbilities.size()) {
                Ability ability = availableAbilities.get(abilityIndex);
                Character target = selectAbilityTarget(ability);
                if (target != null) {
                    String result = ability.use(hero, target);
                    System.out.println(result);
                    
                    if (!target.isAlive() && target instanceof Monster) {
                        System.out.println("💀 " + target.getName() + " has been defeated!");
                    }
                }
            } else {
                System.out.println("Invalid choice! Performing basic attack instead.");
                performHeroAttack(hero);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Performing basic attack instead.");
            performHeroAttack(hero);
        }
    }

    /**
     * Hero defends (reduces incoming damage for this turn)
     */
    private void performHeroDefend(Hero hero) {
        System.out.println(hero.getName() + " takes a defensive stance!");
        // Could implement temporary defense boost here
    }

    /**
     * Process a monster's turn with AI
     */
    private void processMonsterTurn(Monster monster) {
        Character[] heroTargets = heroes.stream()
                .filter(Character::isAlive)
                .toArray(Character[]::new);
        
        if (heroTargets.length == 0) {
            return; // No valid targets
        }
        
        String action = monster.makeAIDecision(heroTargets);
        
        if (action.equals("attack")) {
            Character target = monster.selectTarget(heroTargets);
            if (target != null) {
                int damage = monster.performAttack(target);
                System.out.println(monster.getName() + " attacks " + target.getName() + 
                                 " for " + damage + " damage!");
                
                if (!target.isAlive()) {
                    System.out.println("💀 " + target.getName() + " has fallen!");
                }
            }
        } else if (action.startsWith("ability:")) {
            String abilityName = action.substring(8);
            Ability ability = monster.getAbilities().stream()
                    .filter(a -> a.getName().equals(abilityName))
                    .findFirst()
                    .orElse(null);
            
            if (ability != null) {
                Character target = monster.selectTarget(heroTargets);
                if (target != null) {
                    String result = ability.use(monster, target);
                    System.out.println(result);
                    
                    if (!target.isAlive()) {
                        System.out.println("💀 " + target.getName() + " has fallen!");
                    }
                }
            }
        }
    }

    /**
     * Select a monster target for hero attacks
     */
    private Monster selectMonsterTarget() {
        List<Monster> aliveMonsters = monsters.stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
        
        if (aliveMonsters.isEmpty()) {
            return null;
        }
        
        if (aliveMonsters.size() == 1) {
            return aliveMonsters.get(0);
        }
        
        System.out.println("\nSelect target:");
        for (int i = 0; i < aliveMonsters.size(); i++) {
            System.out.println((i + 1) + ". " + aliveMonsters.get(i).getStatusDisplay());
        }
        
        System.out.print("Choose target (1-" + aliveMonsters.size() + "): ");
        String choice = scanner.nextLine().trim();
        
        try {
            int targetIndex = Integer.parseInt(choice) - 1;
            if (targetIndex >= 0 && targetIndex < aliveMonsters.size()) {
                return aliveMonsters.get(targetIndex);
            }
        } catch (NumberFormatException e) {
            // Fall through to default
        }
        
        // Default to first alive monster
        return aliveMonsters.get(0);
    }

    /**
     * Select target for ability use
     */
    private Character selectAbilityTarget(Ability ability) {
        // For healing abilities, target heroes; for others, target monsters
        if (ability.getType() == Ability.AbilityType.HEAL || 
            ability.getType() == Ability.AbilityType.BUFF) {
            List<Hero> aliveHeroes = heroes.stream()
                    .filter(Character::isAlive)
                    .collect(Collectors.toList());
            
            if (aliveHeroes.size() == 1) {
                return aliveHeroes.get(0);
            }
            
            System.out.println("\nSelect ally target:");
            for (int i = 0; i < aliveHeroes.size(); i++) {
                System.out.println((i + 1) + ". " + aliveHeroes.get(i).getStatusDisplay());
            }
            
            System.out.print("Choose target (1-" + aliveHeroes.size() + "): ");
            String choice = scanner.nextLine().trim();
            
            try {
                int targetIndex = Integer.parseInt(choice) - 1;
                if (targetIndex >= 0 && targetIndex < aliveHeroes.size()) {
                    return aliveHeroes.get(targetIndex);
                }
            } catch (NumberFormatException e) {
                // Fall through to default
            }
            
            return aliveHeroes.get(0);
        } else {
            return selectMonsterTarget();
        }
    }

    /**
     * Display current battle status
     */
    private void displayBattleStatus() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("⚔️  BATTLE STATUS ⚔️");
        System.out.println("═".repeat(50));
        
        System.out.println("\n🛡️  HEROES:");
        for (Hero hero : heroes) {
            String status = hero.isAlive() ? "✅" : "💀";
            System.out.println("  " + status + " " + hero.getStatusDisplay());
        }
        
        System.out.println("\n👹 MONSTERS:");
        for (Monster monster : monsters) {
            String status = monster.isAlive() ? "✅" : "💀";
            System.out.println("  " + status + " " + monster.getStatusDisplay());
        }
        
        System.out.println("═".repeat(50));
    }

    /**
     * Display individual character status
     */
    private void displayCharacterStatus(Character character) {
        System.out.println("\n📊 " + character.getStatusDisplay());
        if (!character.getAbilities().isEmpty()) {
            System.out.println("💫 Abilities:");
            for (Ability ability : character.getAbilities()) {
                String canUse = ability.canUse(character) ? "✅" : "❌";
                System.out.println("  " + canUse + " " + ability.getDisplayInfo());
            }
        }
    }

    /**
     * Check if battle has ended
     */
    private boolean checkBattleEnd() {
        boolean allHeroesDead = heroes.stream().noneMatch(Character::isAlive);
        boolean allMonstersDead = monsters.stream().noneMatch(Character::isAlive);
        
        if (allHeroesDead) {
            battleEnded = true;
            heroesWon = false;
        } else if (allMonstersDead) {
            battleEnded = true;
            heroesWon = true;
        }
        
        return battleEnded;
    }

    /**
     * Display final battle result
     */
    private void displayBattleResult() {
        System.out.println("\n" + "═".repeat(60));
        if (heroesWon) {
            System.out.println("🎉 VICTORY! 🎉");
            System.out.println("The heroes have triumphed over the monsters!");
        } else {
            System.out.println("💀 DEFEAT! 💀");
            System.out.println("The monsters have overwhelmed the heroes...");
        }
        System.out.println("Battle lasted " + turnCount + " turns.");
        System.out.println("═".repeat(60));
    }

    // Getters
    public boolean isBattleEnded() { return battleEnded; }
    public boolean didHeroesWin() { return heroesWon; }
    public int getTurnCount() { return turnCount; }
}