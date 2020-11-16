package tiles;

/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a concrete Tile that:
 *              1. Is accessible by the player (enterable) 
 *              2. Has a chance of encountering monsters
 * 
 * 
 */

/* External Imports */
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import jcolor.Ansi;
import jcolor.Attribute;

/* Internal Imports */
import entities.players.Hero;
import entities.players.Party;
import entities.players.Player;
import items.Equippable;
import items.Inventory;
import items.ConsumableItem;
import items.Equipment;
import items.Storable;
import items.Gold;
import entities.monsters.Monster;
import entities.monsters.MonsterComparator;
import entities.monsters.MonsterFactory;
import entities.monsters.Monsters;
import spells.Spell;
import spells.SpellFactory;
import spells.Spells;
import utility.KeyboardInputUtility;
import utility.PrintUtility;
import utility.FileParserUtility;

public class CommonTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = ' ';
    public static double ENCOUNTER_CHANCE = .4;

    /* Data Members */

    /* Constructors */

    public CommonTile(Coordinate2D coords) {
        super(coords,TILE_CHAR);
    }

    /* Accessor Methods */

    @Override
    public char toCharacter() {
        if (this.isEmpty()) return TILE_CHAR;
        return this.getPiece().represent();
    }

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO Auto-generated method stub
        
        //  CommonTile:
        //      1. Roll a die to see if there is a monster encounter
        //          - If no monster encounter show map again and allow the players to rest
        //      2. If monster encounter show battle scene
        //      3. Allow options to equip, attack, cast, use item
        //      4. Once the battle is over show experience gained and possible level up screen
        //          - If players died then make them lose experience, gold, etc.

        // If the object interacting with this Tile is NOT the party of heroes, do nothing
        if (!(obj instanceof Party)) return;

        Party objects = (Party) obj;
        if (objects.size() == 0 || !(objects.get(0) instanceof Hero)) return;

        Party<Hero> heroes = (Party<Hero>) obj;
        List<Hero> livingHeroes = heroes.partyToList();

        // Roll the die
        System.out.println("Rolling Die...");
        double rand = Math.random();

        // We have an encounter
        if (rand <= ENCOUNTER_CHANCE) {
            System.out.println(Ansi.colorize("You have encountered a monster!", Attribute.BOLD(), Attribute.RED_TEXT()));

            // Get the max level hero
            Hero maxLeveledHero = heroes.get(0);
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                if (hero.getLevel() > maxLeveledHero.getLevel()) {
                    maxLeveledHero = hero;
                }
            }

            // Get the list of monsters and sort them by level
            List<Monster> monsters = Monsters.getMonsterList();
            MonsterComparator mc = new MonsterComparator();
            Collections.sort(monsters, mc.getLevelComparator());

            // Find out the range of indices with monsters less than or equal to the level of the max leveled hero
            int monsterStartIndex = 0;
            int monsterEndIndex = 0;
            do {
                monsterEndIndex++;
            } while(monsterEndIndex < monsters.size() && monsters.get(monsterEndIndex).getLevel() <= maxLeveledHero.getLevel());

            // Choose a random monster in that range            
            List<Monster> chosenMonsters = new ArrayList<Monster>();
            List<Monster> aliveMonsters;
            int monsterAmount =  (int) (1 + Math.ceil(Math.random() * (heroes.size() - 1)));
            for (int i = 0; i < monsterAmount; i++) {
                int randomMonsterIndex = (int) Math.ceil(monsterStartIndex + (Math.random() * (monsterEndIndex - monsterStartIndex - 1)));
                chosenMonsters.add(monsters.get(randomMonsterIndex).clone());
            }
            aliveMonsters = new ArrayList<Monster>(chosenMonsters);
            
            /* Battle Sequence */

            boolean hasLivingMember = true;
            boolean enemiesAlive = true;
            while (hasLivingMember && enemiesAlive) {

                // Show Enemy Status (TODO: Modularize)
                System.out.println(String.format(Ansi.colorize("Enemy Team:\n", Attribute.BOLD(), Attribute.TEXT_COLOR(196)) + 
                                                 Ansi.colorize("%-20s\t",Attribute.BOLD()) + Ansi.colorize("%-20s", Attribute.BOLD(), Attribute.TEXT_COLOR(166)) + Ansi.colorize("%-20s\t", Attribute.BOLD(), Attribute.RED_TEXT()) + Ansi.colorize("%-20s\t%-20s\t%-20s\n", Attribute.BOLD()), 
                                                 "Name", "Level", "Health Points", "Damage", "Defense", "Dodge"));
                
                // Create Separator
                String separator = "";
                while (separator.length() < 140) {
                    separator += "-";
                }

                System.out.println(separator);
                
                for (Monster monster : chosenMonsters) {
                    System.out.println(String.format("%-20s\t" + Ansi.colorize("%-20d\t", Attribute.TEXT_COLOR(130)) + Ansi.colorize("%-20d\t", Attribute.TEXT_COLOR(161)) + "%-20d\t%-20d\t%-20d\t",
                                                     monster.getName(), monster.getLevel(), monster.getHealthPoints(), monster.getDamage(), monster.getDefense(), monster.getDodgeChance()));
                }          
                System.out.println();      

                // Show Party Status
                System.out.println(heroes.partyDisplay());

                // Decide what to do for each Hero (equip,attack,cast,use)
                Scanner input = new Scanner(System.in);
                for (int i = 0; i < heroes.size(); i++) {

                    Hero hero = heroes.get(i);

                    // Decide what the hero does if they are still alive
                    if (hero.isAlive()) {

                        // Decide what this hero will do during their turn 
                        String choice = KeyboardInputUtility.promptTillValid(input, String.format("\nCHOOSE MOVE FOR " + Ansi.colorize("%s", Attribute.BOLD(), Attribute.TEXT_COLOR(253)) + " (EQUIP/equip, ATTACK/attack, CAST/cast, USE/use): ", hero.getName()), (str) -> {
                            String lowerStr = str.toLowerCase();
                            return (lowerStr.equals("equip") /* && hero.getInventory().size() > 0 */ ) || lowerStr.equals("attack") || lowerStr.equals("cast") || lowerStr.equals("use");
                        });

                        // Do choice here

                        choice = choice.toLowerCase();

                        // Equip Logic
                        if (choice.equals("equip")) {
                            // TODO: Show items in inventory, then allow the user to equip or go back

                            // Only display the equippable items
                            HashSet<Class<? extends Storable>> desiredItemTypes = new HashSet<>();
                            desiredItemTypes.add(Equipment.class);
                            Inventory equippableOnlyInventory = hero.getInventory().getInventoryByItemTypesAndSubTypes(desiredItemTypes);

                            /* DEBUGGING */
                            // System.out.println("Showing Only Equippable Items:\n" + equippableOnlyInventory + "\n");
                            // System.out.println("Showing All Items:\n" + hero.getInventory() + "\n");
                            /*-----------*/
                            
                            System.out.println(equippableOnlyInventory);
                            if (hero.getInventory().numItems() > 0) {
                                int OFFSET = 1;
                                // TODO: Encapsculate this into a method MODULARIZE
                                String userInput = KeyboardInputUtility.promptTillValid(input, "Please choose an item from your inventory by its number, or \"Quit\" to do nothing: ", (str) -> {
                                    String lowerStr = str.toLowerCase();
                                    String numberRegex = "^[0-9]+$";

                                    // Check to see if it is the correct index
                                    int chosenNumber;
                                    if (lowerStr.matches(numberRegex) && (chosenNumber = Integer.parseInt(lowerStr) - OFFSET) >= 0  && chosenNumber < hero.getInventory().numItems()) {
                                        return true;
                                    }

                                    // Otherwise return if we typed back
                                    return lowerStr.equals("quit");
                                });

                                // Convert toLower
                                userInput = userInput.toLowerCase();

                                if (userInput.equals("quit")) {
                                    continue;
                                }

                                // Assuming only equippable items
                                int itemIndex = Integer.parseInt(userInput) - OFFSET; 
                                Equipment equippableItem = (Equipment) equippableOnlyInventory.get(itemIndex);
                                hero.equip(equippableItem);

                            }                            
                        }

                        // Attack Logic
                        else if (choice.equals("attack")) {
                            // TODO: Allow user to choose which monster to target, do attack
                            System.out.println("Select the monster you would like to attack:\n");
                            int monsterToAttackIndex = KeyboardInputUtility.chooseFromList(input, aliveMonsters);
                            Monster monster = aliveMonsters.get(monsterToAttackIndex);    // TODO: hurt the monster
                            System.out.println();
                            hero.attack(monster, hero.getWeapon());
                        }

                        // Cast Logic
                        else if (choice.equals("cast")) {
                            // TODO: Show abilities the user is able to cast, allow user to choose which monster to target and perform the attack
                            System.out.println("Select the monster you would like to attack:\n");
                            int monsterToAttackIndex = KeyboardInputUtility.chooseFromList(input, aliveMonsters);
                            Monster monster = aliveMonsters.get(monsterToAttackIndex);    // TODO: hurt the monster

                            // Compile Possible Spells
                            String slashDelimiter = "/";
                            String spaceDelimiter = "\\s+";
                            List<List<List<String>>> spellInfoStrings = new ArrayList<>();

                            // Get spell types
                            List<List<String>> fireSpells = FileParserUtility.parseFile("./Data/Spells/FireSpells.txt", slashDelimiter, spaceDelimiter);
                            List<List<String>> iceSpells = FileParserUtility.parseFile("./Data/Spells/IceSpells.txt", slashDelimiter, spaceDelimiter);
                            List<List<String>> lightningSpells = FileParserUtility.parseFile("./Data/Spells/LightningSpells.txt", slashDelimiter, spaceDelimiter);

                            // Remove Header
                            fireSpells.remove(0);
                            iceSpells.remove(0);
                            lightningSpells.remove(0);

                            // Compile Spells together
                            spellInfoStrings.add(fireSpells);
                            spellInfoStrings.add(iceSpells);
                            spellInfoStrings.add(lightningSpells);

                            // Choose spell type
                            List<String> spellTypes = new ArrayList<>();
                            spellTypes.add("Fire");
                            spellTypes.add("Ice");
                            spellTypes.add("Lightning");

                            System.out.println("Choose a spell type by their number: ");
                            int spellTypeChoice = KeyboardInputUtility.chooseFromList(input, spellTypes);

                            List<List<String>> spellType = spellInfoStrings.get(spellTypeChoice);
                            List<Spell> spells = Spells.getSpellsFromSpellStringList(spellTypeChoice,spellType);
                            System.out.println(PrintUtility.listToString(spells,false,true));

                            int OFFSET = 1;
                            String userInput = KeyboardInputUtility.promptTillValid(input, "Choose the spell by their number, or type \"Quit\" to do nothing: ", (str) -> {

                                String lowerStr = str.toLowerCase();
                                String numberRegex = "^[0-9]+$";

                                if (lowerStr.equals("quit")) return true;

                                // Check to see if it is the correct index
                                int chosenNumber;
                                if (lowerStr.matches(numberRegex) && (chosenNumber = Integer.parseInt(lowerStr) - OFFSET) >= 0  && chosenNumber < spells.size() && hero.getLevel() >= spells.get(chosenNumber).getRequiredLevel() && hero.getCurrMagicPower() >= spells.get(chosenNumber).getManaCost() ) {
                                    return true;
                                } else {
                                    System.out.println("Can't Case that spell! Check Mana Cost, Level Requirement, etc.");
                                }

                                return false;

                            });

                            if(userInput.equalsIgnoreCase("quit")) {
                                // do nothing
                            }

                            else {
                                int spellIndex = Integer.parseInt(userInput) - OFFSET;
                                Spell spell = spells.get(spellIndex);

                                System.out.println();
                                hero.attack(monster, spell);
                            }

                        }

                        // Use Logic
                        else if (choice.equals("use")) {
                            // TODO: Show inventory but only the consumable Items, allow user to choose which monster to target if it is a damaging item or themself if it's a potion

                            // Only show consumable items
                            HashSet<Class<? extends Storable>> desiredItemTypes = new HashSet<>();
                            desiredItemTypes.add(ConsumableItem.class);
                            Inventory consumableOnlyInventory = hero.getInventory().getInventoryByItemTypesAndSubTypes(desiredItemTypes);

                            System.out.println(consumableOnlyInventory);
                            if (hero.getInventory().numItems() > 0) {
                                int OFFSET = 1;
                                String userInput = KeyboardInputUtility.promptTillValid(input, "Please choose an item from your inventory by its number, or \"Quit\" to do nothing: ", (str) -> {
                                    String lowerStr = str.toLowerCase();
                                    String numberRegex = "^[0-9]+$";

                                    // Check to see if it is the correct index
                                    int chosenNumber;
                                    if (lowerStr.matches(numberRegex) && (chosenNumber = Integer.parseInt(lowerStr) - OFFSET) >= 0  && chosenNumber < hero.getInventory().numItems()) {
                                        return true;
                                    }

                                    // Otherwise return if we typed back
                                    return lowerStr.equals("quit");
                                });

                                // Convert toLower
                                userInput = userInput.toLowerCase();

                                if (userInput.equals("quit")) {
                                    continue;
                                }

                                // Assuming only equippable items
                                int itemIndex = Integer.parseInt(userInput) - OFFSET; 
                                ConsumableItem consumableItem = (ConsumableItem) consumableOnlyInventory.get(itemIndex);
                                hero.consume(consumableItem);

                            }                            

                        }                        

                    }
                }      
                
                // Monsters Attack Back                
                System.out.println(Ansi.colorize("\nMonsters are attacking back\n", Attribute.BOLD(), Attribute.TEXT_COLOR(202)));
                for (Monster monster : chosenMonsters) {
                    if (monster.isAlive()) {
                        int chosenHero = (int) (Math.random() * (livingHeroes.size() - 1));
                        Hero target = livingHeroes.get(chosenHero);
                        monster.attack(target, null);
                    }
                }
 
                // Find out if there are living heroes
                for (Hero hero : heroes.partyToList()) {
                    if (!hero.isAlive()) {
                        livingHeroes.remove(hero);
                    }
                }
                hasLivingMember = livingHeroes.size() > 0; // If you found a living member you would have broken out of the for loop early thus making "i" less than the length of the list

                // Update alive monster list
                for (Monster monster : chosenMonsters) {
                    if (!monster.isAlive() && aliveMonsters.contains(monster)) {
                        aliveMonsters.remove(monster);
                    }
                }
                enemiesAlive = aliveMonsters.size() > 0;

                // // Find out if there are living monsters
                // i = 0;
                // for (; i < chosenMonsters.size(); i++) {
                //     if (monsters.get(i).isAlive()) break;
                // }
                // enemiesAlive = i < monsters.size();

                // TODO: Delete Later
                // hasLivingMember = false;
                // enemiesAlive = false;

                // Heal 10% of mana and health
                System.out.println(Ansi.colorize("Healing...", Attribute.BOLD(), Attribute.BRIGHT_GREEN_TEXT()));
                System.out.println();
                int i2 = 0;
                for (; i2 < heroes.size(); i2++) {
                    Hero hero = heroes.get(i2);
                    hero.heal((hero.getMaxHealthPoints() / 20));
                    hero.replenishMana((hero.getMaxMagicPower() / 20));
                }

            }  

            System.out.println(Ansi.colorize("The battle is over!", Attribute.BOLD(), Attribute.TEXT_COLOR(171)));
            System.out.println();
            
            // Give living Heroes experience, and penalize fainted heroes
            int i = 0;
            Gold earnedMoney = new Gold((monsters.get(0).getLevel() * 100) * chosenMonsters.size());
            int earnedExperience = (monsters.get(0).getLevel() * 15) * chosenMonsters.size();
            int lossMoney = 150;
            int lossEXP = 15;
            for (; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                if (hero.isAlive()) {

                    // Allocate earned money                   
                    hero.getMoneyPouch().addCurrency(earnedMoney);
                    System.out.println(String.format("Hero " + Ansi.colorize("%s", Attribute.BOLD()) + Ansi.colorize(" earned ", Attribute.BOLD(), Attribute.TEXT_COLOR(10)) + Ansi.colorize("%f Gold", Attribute.BOLD(), Attribute.TEXT_COLOR(214)),hero.getName(),earnedMoney.getAmount()));             

                    // Allocate Experience    
                    hero.increaseEXP(earnedExperience);   
                    System.out.println(String.format("Hero " + Ansi.colorize("%s", Attribute.BOLD()) + Ansi.colorize(" earned ", Attribute.BOLD(), Attribute.TEXT_COLOR(10)) + Ansi.colorize("%d EXP", Attribute.BOLD(), Attribute.TEXT_COLOR(3)),hero.getName(),earnedExperience));             

                } else {

                    // Take away money
                    hero.getMoneyPouch().removeCurrency(Gold.class, lossMoney);
                    System.out.println(String.format("Hero " + Ansi.colorize("%s", Attribute.BOLD()) + Ansi.colorize(" lost ", Attribute.BOLD(), Attribute.TEXT_COLOR(9)) + Ansi.colorize("%d Gold", Attribute.BOLD(), Attribute.TEXT_COLOR(214)),hero.getName(),lossMoney));             

                    // Take away Experience
                    hero.decreaseEXP(lossEXP);
                    System.out.println(String.format("Hero " + Ansi.colorize("%s", Attribute.BOLD()) + Ansi.colorize(" lost ", Attribute.BOLD(), Attribute.TEXT_COLOR(9)) + Ansi.colorize("%d EXP", Attribute.BOLD(), Attribute.TEXT_COLOR(3)),hero.getName(),lossEXP));             

                }
                System.out.println();
            }

        }

        // No encounter
        else {
            System.out.println(Ansi.colorize("There was no monster encounter!", Attribute.BOLD(), Attribute.BRIGHT_MAGENTA_TEXT(), Attribute.SLOW_BLINK()));
            System.out.println(Ansi.colorize("Your party may rest...", Attribute.BOLD(), Attribute.GREEN_TEXT()));

            // Heal 10% of mana and health
            System.out.println(Ansi.colorize("\nHealing...", Attribute.BOLD(), Attribute.BRIGHT_GREEN_TEXT()));
            System.out.println();
            int i2 = 0;
            for (; i2 < heroes.size(); i2++) {
                Hero hero = heroes.get(i2);
                hero.heal((hero.getMaxHealthPoints() / 10));
                hero.replenishMana((hero.getMaxMagicPower() / 10));
            }
        }

    }

}
