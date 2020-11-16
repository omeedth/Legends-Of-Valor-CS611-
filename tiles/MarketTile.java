package tiles;

/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Tile that allows a player to purchase from a market
 *              1. Players can stop here to buy/sell items
 * 
 */

/* External Imports */
import java.util.List;
import java.util.Scanner;

/* Internal Imports */
import entities.players.Party;
import entities.players.Hero;
import items.Inventory;
import items.Storable;
import items.Gold;
import items.Item;
import items.Items;
import items.MoneyPouch;
import jcolor.Ansi;
import jcolor.Attribute;
import utility.KeyboardInputUtility;
import utility.PrintUtility;

public class MarketTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = 'M';

    /* Data Members */

    /* Constructors */

    public MarketTile(Coordinate2D coords) {
        super(coords,TILE_CHAR);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO Auto-generated method stub
        
        //  MarketTile:
        //      1. Show market (the items they possess and the prices, level cap etc.)
        //      2. Show heroes' Inventory and current money

        // If the object interacting with this Tile is NOT the party of heroes, do nothing
        if (!(obj instanceof Party)) return;

        Party objects = (Party) obj;
        if (objects.size() == 0 || !(objects.get(0) instanceof Hero)) return;

        Party<Hero> heroes = (Party<Hero>) obj;

        // Create Scanner Object
        Scanner input = new Scanner(System.in);

        // Decide to enter the market or not
        String enterChoice = KeyboardInputUtility.promptTillValid(input, "Would you like to enter the market? (y/n): ", (str) -> {
            String lowerStr = str.toLowerCase();
            return lowerStr.equals("y") || lowerStr.equals("n");
        });

        if (enterChoice.equals("n")) return;
        
        // Decide to sell or buy
        String choice = KeyboardInputUtility.promptTillValid(input, "Would you like to \"Buy\" or \"Sell\": ", (str) -> {
            String lowerStr = str.toLowerCase();
            return lowerStr.equals("buy") || lowerStr.equals("sell");
        });

        // Convert to lowercase
        choice = choice.toLowerCase();
        int OFFSET = 1;

        // Buying items from the market
        if (choice.equals("buy")) {

            // Show Wares
            List<Item> items = Items.getItemList();
            System.out.println(Ansi.colorize("Shop:", Attribute.BOLD(), Attribute.TEXT_COLOR(22)));
            System.out.println(String.format(Ansi.colorize("%-20s\t%-20s\t%-20s", Attribute.BOLD()), "Name", "Required Level", "Cost"));
            System.out.println("------------------------------------------------------------------");
            int START = 1;
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                int displayIndex = i + START;
                System.out.println(String.format(Ansi.colorize("%d. %-20s\t", Attribute.BOLD(), Attribute.TEXT_COLOR(183)) + "%-20d\t%-20d",displayIndex,item.getName(),item.getRequiredLevel(),item.getCost()));
            }
            System.out.println();

            // Choose a hero
            System.out.println(Ansi.colorize("Select a Hero that is buying items:\n", Attribute.BOLD()));
            int heroChoice = KeyboardInputUtility.chooseFromList(input,heroes.partyToList());
            Hero hero = heroes.get(heroChoice);
            MoneyPouch moneyPouch = hero.getMoneyPouch();

            // Show hero's wallet
            System.out.println(moneyPouch);

            // Choose an item to buy
            String userInput = KeyboardInputUtility.promptTillValid(input, "Select the item to buy by their number, or \"Quit\" to go back: ", (str) -> {
                String lowerStr = str.toLowerCase();
                String numberRegex = "^[0-9]+$";

                // Check to see if it is the correct index
                int chosenNumber;
                if (lowerStr.matches(numberRegex) && (chosenNumber = Integer.parseInt(lowerStr) - OFFSET) >= 0  && chosenNumber < items.size()) {
                    return true;
                }

                // Otherwise return if we typed back
                return lowerStr.equals("quit");
            });

            // Convert user input to lowercase
            userInput = userInput.toLowerCase();

            // Check to see if the user quit
            if (userInput.equals("quit")) {
                // Do nothing
            } else {
                int itemChoice = Integer.parseInt(userInput) - OFFSET;
                Item item = items.get(itemChoice);
                Inventory inventory = hero.getInventory();
                
                boolean enoughMoney = moneyPouch.canUseCurrency(Gold.class, item.getCost());
                boolean canStore = hero.getInventory().canStore(item);

                /* DEBUGGING */
                System.out.println(String.format("Item: Size: %f, Weight: %f, numElement: %d",item.getSize(),item.getWeight(),1));
                System.out.println(String.format("Inventory: Size Capacity: %f/%f, Weight Capacity: %f/%f, Object Capacity: %d/%d",inventory.getCurrSize(),inventory.getSizeCapacity(),inventory.getCurrWeight(),inventory.getWeightCapacity(),inventory.numItems(),inventory.getObjectCapacity()));
                /*-----------*/

                if (enoughMoney && canStore) {
                    moneyPouch.useCurrency(Gold.class, item.getCost());
                    System.out.println(Ansi.colorize(String.format("%s bought %s",hero.getName(),item.getName()), Attribute.BOLD()));
                    inventory.add(item);

                    /* DEBUGGING */
                    System.out.println("Inventory is now:\n" + inventory);
                    /*-----------*/

                } else if (!enoughMoney) {
                    System.out.println(String.format("Unfortunately, ") + Ansi.colorize(String.format("%s",hero.getName()), Attribute.BOLD()) + " does not have enough " + Ansi.colorize(String.format("%s",Gold.class.getSimpleName()), Attribute.TEXT_COLOR(214)) + " to purchase " + Ansi.colorize(String.format("%s",item.getName()), Attribute.BOLD(), Attribute.TEXT_COLOR(171)));
                } else if (!canStore) {
                    System.out.println(String.format("Unfortunately, ") + Ansi.colorize(String.format("%s\'s",hero.getName()), Attribute.BOLD()) + " inventory does not have enough " + Ansi.colorize("storage", Attribute.TEXT_COLOR(214)) + " to hold the " + Ansi.colorize(String.format("%s",item.getName()), Attribute.BOLD(), Attribute.TEXT_COLOR(171)));
                }
            }

        }

        // Selling items to the market
        else {

            // Choose a hero
            System.out.println(Ansi.colorize("Select a Hero that is selling their items:\n", Attribute.BOLD()));
            int heroChoice = KeyboardInputUtility.chooseFromList(input,heroes.partyToList());
            Hero hero = heroes.get(heroChoice);

            // Show Heroes' Inventory
            Inventory inventory = hero.getInventory();
            System.out.println(inventory);

            // If there are items to sell, choose an item to sell
            if (inventory.numItems() > 0) {
                String userInput = KeyboardInputUtility.promptTillValid(input, "Select the item to sell by their number, or \"Quit\" to go back: ", (str) -> {
                    String lowerStr = str.toLowerCase();
                    String numberRegex = "^[0-9]+$";

                    // Check to see if it is the correct index
                    int chosenNumber;
                    if (lowerStr.matches(numberRegex) && (chosenNumber = Integer.parseInt(lowerStr) - OFFSET) >= 0  && chosenNumber < inventory.numItems()) {
                        return true;
                    }

                    // Otherwise return if we typed back
                    return lowerStr.equals("quit");
                });

                // Convert user input to lowercase
                userInput = userInput.toLowerCase();

                // Check to see if the user quit
                if (userInput.equals("quit")) {
                    // Do nothing
                } else {
                    int itemChoice = Integer.parseInt(userInput) - OFFSET;
                    Item item = (Item) inventory.get(itemChoice);
                    
                    hero.getMoneyPouch().addCurrency(new Gold(item.getCost()));
                }

            }

        }        

    }

}
