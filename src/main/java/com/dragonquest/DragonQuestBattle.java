package com.dragonquest;

import com.dragonquest.battle.Battle;
import com.dragonquest.characters.Hero;
import com.dragonquest.characters.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Dragon Quest Battle Simulation
 * Sets up heroes and monsters for turn-based combat
 */
public class DragonQuestBattle {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayWelcomeMessage();
        
        // Create the classic party of four heroes
        List<Hero> heroes = createHeroParty();
        
        // Display hero party
        displayHeroParty(heroes);
        
        // Allow player to choose battle difficulty
        List<Monster> monsters = createMonsterEncounter();
        
        // Start the battle
        Battle battle = new Battle(heroes, monsters);
        battle.startBattle();
        
        // Offer to play again
        offerPlayAgain();
    }

    /**
     * Display welcome message and game introduction
     */
    private static void displayWelcomeMessage() {
        System.out.println("╔" + "═".repeat(68) + "╗");
        System.out.println("║" + " ".repeat(68) + "║");
        System.out.println("║" + centerText("🐉 DRAGON QUEST VIII: BATTLE SIMULATION 🐉", 68) + "║");
        System.out.println("║" + " ".repeat(68) + "║");
        System.out.println("║" + centerText("Welcome to the epic turn-based battle system!", 68) + "║");
        System.out.println("║" + centerText("Command a party of four heroes in tactical combat", 68) + "║");
        System.out.println("║" + centerText("against fearsome monsters!", 68) + "║");
        System.out.println("║" + " ".repeat(68) + "║");
        System.out.println("╚" + "═".repeat(68) + "╝");
        System.out.println();
        
        System.out.println("📜 BATTLE INSTRUCTIONS:");
        System.out.println("• Each character acts based on their speed stat");
        System.out.println("• Heroes can Attack, use Abilities, or Defend");
        System.out.println("• Monsters are controlled by AI");
        System.out.println("• Battle ends when all heroes or all monsters are defeated");
        System.out.println("• Different character classes have unique abilities and stats");
        System.out.println();
        
        System.out.print("Press Enter to begin your adventure...");
        scanner.nextLine();
        System.out.println();
    }

    /**
     * Create the classic party of four heroes with different classes
     */
    private static List<Hero> createHeroParty() {
        List<Hero> heroes = new ArrayList<>();
        
        System.out.println("🛡️  ASSEMBLING YOUR HERO PARTY 🛡️");
        System.out.println("─".repeat(40));
        
        // Create four heroes with different classes and balanced levels
        heroes.add(new Hero("Aric the Brave", Hero.HeroClass.WARRIOR, 8));
        heroes.add(new Hero("Lyra Stormweaver", Hero.HeroClass.MAGE, 7));
        heroes.add(new Hero("Seraphina the Pure", Hero.HeroClass.HEALER, 7));
        heroes.add(new Hero("Kael Shadowstep", Hero.HeroClass.ROGUE, 8));
        
        System.out.println("Your party has been assembled!");
        return heroes;
    }

    /**
     * Display hero party information
     */
    private static void displayHeroParty(List<Hero> heroes) {
        System.out.println("\n🎖️  YOUR HERO PARTY 🎖️");
        System.out.println("═".repeat(60));
        
        for (int i = 0; i < heroes.size(); i++) {
            Hero hero = heroes.get(i);
            System.out.println((i + 1) + ". " + hero.getStatusDisplay());
            System.out.println("   Specialty: " + hero.getSpecialty());
            System.out.println("   Abilities: " + 
                hero.getAbilities().stream()
                    .map(ability -> ability.getName())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("None"));
            System.out.println();
        }
        
        System.out.print("Press Enter to choose your battle...");
        scanner.nextLine();
    }

    /**
     * Create monster encounter based on difficulty choice
     */
    private static List<Monster> createMonsterEncounter() {
        System.out.println("\n⚔️  CHOOSE YOUR BATTLE ⚔️");
        System.out.println("─".repeat(30));
        System.out.println("1. Easy - Forest Bandits (Goblins & Slimes)");
        System.out.println("2. Medium - Orc Raiding Party");
        System.out.println("3. Hard - Undead Legion (Skeletons)");
        System.out.println("4. Epic - Ancient Dragon");
        System.out.println("5. Random Encounter");
        
        System.out.print("Select difficulty (1-5): ");
        String choice = scanner.nextLine().trim();
        
        List<Monster> monsters = new ArrayList<>();
        
        switch (choice) {
            case "1":
                monsters.add(new Monster("Goblin Scout", Monster.MonsterType.GOBLIN, 5));
                monsters.add(new Monster("Goblin Warrior", Monster.MonsterType.GOBLIN, 6));
                monsters.add(new Monster("Green Slime", Monster.MonsterType.SLIME, 4));
                break;
            case "2":
                monsters.add(new Monster("Orc Brute", Monster.MonsterType.ORC, 7));
                monsters.add(new Monster("Orc Chieftain", Monster.MonsterType.ORC, 8));
                monsters.add(new Monster("Orc Shaman", Monster.MonsterType.ORC, 6));
                break;
            case "3":
                monsters.add(new Monster("Skeleton Warrior", Monster.MonsterType.SKELETON, 7));
                monsters.add(new Monster("Skeleton Mage", Monster.MonsterType.SKELETON, 6));
                monsters.add(new Monster("Skeleton Knight", Monster.MonsterType.SKELETON, 8));
                break;
            case "4":
                monsters.add(new Monster("Ancient Red Dragon", Monster.MonsterType.DRAGON, 12));
                break;
            case "5":
                createRandomEncounter(monsters);
                break;
            default:
                System.out.println("Invalid choice. Creating random encounter...");
                createRandomEncounter(monsters);
                break;
        }
        
        System.out.println("\n👹 ENEMY FORCES APPEAR!");
        System.out.println("═".repeat(50));
        for (Monster monster : monsters) {
            System.out.println("💀 " + monster.getStatusDisplay());
        }
        System.out.println("═".repeat(50));
        
        System.out.print("Press Enter to begin battle...");
        scanner.nextLine();
        
        return monsters;
    }

    /**
     * Create a random monster encounter
     */
    private static void createRandomEncounter(List<Monster> monsters) {
        Monster.MonsterType[] types = Monster.MonsterType.values();
        int numMonsters = 2 + (int) (Math.random() * 3); // 2-4 monsters
        
        for (int i = 0; i < numMonsters; i++) {
            Monster.MonsterType randomType = types[(int) (Math.random() * types.length)];
            int level = 4 + (int) (Math.random() * 6); // Level 4-9
            String name = generateMonsterName(randomType);
            monsters.add(new Monster(name, randomType, level));
        }
    }

    /**
     * Generate random names for monsters
     */
    private static String generateMonsterName(Monster.MonsterType type) {
        String[] prefixes = {"Vicious", "Dark", "Fierce", "Ancient", "Wild", "Cursed"};
        String[] suffixes = {"Bane", "Claw", "Fang", "Shadow", "Terror", "Doom"};
        
        String prefix = prefixes[(int) (Math.random() * prefixes.length)];
        String suffix = suffixes[(int) (Math.random() * suffixes.length)];
        
        switch (type) {
            case GOBLIN:
                return prefix + " Goblin " + suffix;
            case ORC:
                return prefix + " Orc " + suffix;
            case SKELETON:
                return prefix + " Skeleton " + suffix;
            case DRAGON:
                return prefix + " Dragon " + suffix;
            case SLIME:
                return prefix + " Slime " + suffix;
            default:
                return "Unknown Monster";
        }
    }

    /**
     * Offer to play again
     */
    private static void offerPlayAgain() {
        System.out.println("\n🎮 Would you like to play again? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        
        if (choice.equals("y") || choice.equals("yes")) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("Starting new adventure...");
            System.out.println("═".repeat(60) + "\n");
            main(new String[0]); // Restart the game
        } else {
            System.out.println("\n🌟 Thank you for playing Dragon Quest Battle Simulation! 🌟");
            System.out.println("May your adventures continue in distant lands...");
        }
    }

    /**
     * Center text within a given width
     */
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        while (sb.length() < width) {
            sb.append(" ");
        }
        
        return sb.toString();
    }
}