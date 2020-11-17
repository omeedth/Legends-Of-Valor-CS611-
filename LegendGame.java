/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent legend Game
 * Subclass of RPG Game
 **/

import java.util.*;

public class LegendGame extends RpgGame{

    /* public static Variables */
    public static final int BOARD_DIMENSIONS = 8;

    /* Data Members */

    protected HeroCollect heroList;
    protected MonsterCollect monsterList;
    
    protected LegendMarket market;
    protected PlayerTeam team;
    
   /* Default constructor */
    LegendGame(){
        super();
        this.team = new PlayerTeam();
    }
    
    /* Default constructor */
    LegendGame(Board world, HeroCollect heroList, MonsterCollect monsterList, LegendMarket market){
        super(world);
        this.heroList = heroList;
        this.monsterList = monsterList;
        this.market = market;
        this.team = new PlayerTeam();
    }

    /** Display welcome message before game start */
    public void welcome(){
        System.out.println();
        System.out.println(" \u001B[35m ::∴★∵**☆．∴★∵**☆．．☆．∵★∵∴☆．．*☆．∴★∵． \u001B[0m ");
        System.out.println();
        System.out.println("       \u001B[35m LEGENDS: MOSTER AND HEROES \u001B[0m");
        System.out.println();
        System.out.println(" \u001B[35m ::∴★∵**☆．∴★∵**☆．．☆．∵★∵∴☆．．*☆．∴★∵． \u001B[0m ");
        System.out.println();
        System.out.println("  \u001B[35mWelcome to the world of adventure!");
        System.out.println();
        System.out.println("  This is a typical RPG game, where you control your heroes to fight agains mosters all over the world.");
        System.out.println("  You may randomly encounter fight during you adventure.");
        System.out.println("  Your heroes gain money and experience when they beat the monsters.");
        System.out.println("  Markets are the place where you can sale and buy things.");
        System.out.println("  To armed up your heros, you may want to buy useful weapons, armors, potions, or spells and sale those you no longer needed.\u001B[0m");
        System.out.println();
        System.out.print("Type I/i to view basic info, Q/q to exit, or any other key to start play:");
        Scanner in = new Scanner(System.in);
        String gameOpt = in.nextLine();
        if(gameOpt.equals("I")||gameOpt.equals("i")){
            this.info();
            this.init();
        }else if(gameOpt.equals("Q")||gameOpt.equals("q")){
            System.exit(0);
        }else{
            this.init();
        }
    }
    
    /** Initialize the team */
    public void init(){
        // Build the team
        System.out.println();
        System.out.println("       Now it is time to build your team!");
        System.out.println();
        System.out.println("       There are three types of heroes: Warriors, Sorcerers and Paladins, each with different  favored skills.");
        System.out.println("       Please note: you may select up to 3 heroes for your team.");
        System.out.println();
        // Add members to team
        boolean loop = true;
        do{
            // Display heros
            this.heroList.display();
            // Build team
            this.team.addMember(this.heroList.selectHero());
            if(this.team.size() == 3){
                loop = false;
            }else{
                System.out.print("Do you want to select another hero? (Y/y for yes, N/n for no or Q/q to quit game):");
                Scanner in = new Scanner(System.in);
                String optKey;
                boolean innerLoop;
                do{
                    optKey = in.nextLine();
                    innerLoop = false;
                    if(optKey.equals("Y")||optKey.equals("y")){
                        continue;
                    }else if(optKey.equals("N")||optKey.equals("n")){
                        loop = false;
                    }else if(optKey.equals("Q")||optKey.equals("q")){
                        System.exit(0);
                    }else{
                        System.out.print("Invalid input, please type Y/y for yes, N/n for no:");
                        innerLoop = true;
                    }
                }while(innerLoop);
            }
        }while(loop);
    }
    
    
    /** Play the game based on game rules */
    public void play(){
        // End the game if all heroes die
        do{
            // Show world map before move
            this.world.displayMap();
            // Move team to new tile pos
            this.move();
            // Launch event on that tile
            this.event();
        }while(this.team.isFaint()==false);
    }
    
    public void event(){
        // Get curret pos of the team
        int teamX = this.team.getXPos();
        int teamY = this.team.getYPos();
        Tile destTile = this.world.getTile(teamX,teamY); // Modified
        String eSign = destTile.toString();
        // If current tile is a Market, trade
        if(eSign.equals("\u001B[34mM\u001B[0m")){
            System.out.println();
            System.out.println("\u001B[32m You Found a Market. \u001B[0m");
            System.out.println();
            this.trade();
        }else{
            // If current tile is common, encounter a battle with 80% chance
            if(Math.random()<0.80){
                System.out.println();
                System.out.println("\u001B[31m You Encountered a Fight. \u001B[0m");
                System.out.println();
                this.battle();
            }else{
                System.out.println();
                System.out.println("\u001B[35m Nothing Special Happend. \u001B[0m");
                System.out.println();
                this.common();
            }
        }
        // Update change to the world
        // this.world.updateTeamPos(this.team.getXPos(),this.team.getYPos());
        Tile source = new CommonTile(new Coordinate2D());
        this.world.movePiece(this.team, source, destTile);
    }
    
    /** Trade in market */
    public void trade(){
        this.market.welcome(this.team);
    }
    
    /** Common event when reach empty slot */
    public void common(){
        System.out.print("Enter R/r to take a rest, or any other key to continue adventure:");
        Scanner in = new Scanner(System.in);
        String optKey = in.nextLine();
        if(optKey.equals("Q")||optKey.equals("q")){
            System.exit(0);
        }else if(optKey.equals("R")||optKey.equals("r")){
            // Display heros in team
            this.team.display();
            boolean loop = true;
            do{
                System.out.print("Enter Hero ID to view a hero, or C/c to continue adventure:");
                optKey = in.nextLine();
                if(optKey.equals("Q")||optKey.equals("q")){
                    System.exit(0);
                }else if(optKey.matches("\\d+")){
                    int heroId = Integer.parseInt(optKey);
                    if(heroId>0 && heroId<= this.team.size()){
                        this.viewHero((Hero)this.team.getMember(heroId-1));
                    }else{
                        System.out.print("Invalid input, enter Hero ID to view a hero, or C/c to continue adventure:");
                    }
                }else if(optKey.equals("C")==false && optKey.equals("c")==false){
                    System.out.print("Invalid input, enter Hero ID to view a hero, or C/c to continue adventure:");
                }
            }while(loop);
        }
    }
    
    /**
     * Helper function
     * Allow player to take action on hero when rest
     * Should only be called by common
     */
    protected void viewHero(Hero hero){
        System.out.print("Do you want to 1.Check Inventory 2.Change Weapon 3.Change Armor 4.Drink Potion 5.Return:");
        Scanner in = new Scanner(System.in);
        String optKey = in.nextLine();
        boolean loop;
        do{
            loop = false;
            optKey = in.nextLine();
            if(optKey.equals("Q")||optKey.equals("q")){
                System.exit(0);
            }else if(optKey.equals("1")){
                hero.displayInventory();
            }else if(optKey.equals("2")){
                hero.changeWeapon();
            }else if(optKey.equals("3")){
                hero.changeArmor();
            }else if(optKey.equals("4")){
                hero.drinkPotion();
            }else if(optKey.equals("5")){
                break;
            }else{
                System.out.print("Invalid option, 1.Check Inventory 2.Change Weapon 3.Change Armor 4.Drink Potion 5.Return:");
                loop = true;
            }
        }while(loop);
    }
    
    /**
     * Battle in game
     * Generate a random team of monsters of same size as playerTeam, and match level of team
     * Take turns until either team faint
     */
    public void battle(){
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        System.out.println("       \u001B[31m BATTLE START \u001B[0m");
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        int battleLevel = this.team.getLevel();
        MonsterTeam monsters = this.monsterList.generateTeam(this.team.size(), battleLevel);
        team.display();
        monsters.display();
        do{
            this.team.takeTurn(monsters);
            if(monsters.isFaint() == false) { monsters.takeTurn(this.team);}
        }while(this.team.isFaint() == false && monsters.isFaint() == false);
        // If player team win, get reward, else, game end
        if(monsters.isFaint()){
            this.team.win(battleLevel);
        }else{
            this.gameOver();
        }
    }
    
    /** When player team lose, game over */
    public void gameOver(){
        System.out.println("Your team was beaten by the monsters...");
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        System.out.println("       \u001B[31m GAME OVER \u001B[0m");
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        System.exit(0);
    }
    
    /** Move team on the world map */
    public void move(){
        System.out.print("Time to take a move to the next destination:");
        // Get curret pos of the team
        int teamX = this.team.getXPos();
        int teamY = this.team.getYPos();
        
        String moveKey;
        Scanner in = new Scanner(System.in);
        boolean loop = true;
        do{
            moveKey = in.nextLine();
            if(moveKey.equals("W")||moveKey.equals("w")){
                if(teamY <= 0){
                    System.out.print("Reach top edge of the world, please try a different move:");
                }else if(this.world.isOccupied(teamX, teamY-1)){
                    System.out.print("Inaccessible tile, please try a different move:");
                }else{
                    this.team.moveUp();
                    loop = false;
                }
            }else if(moveKey.equals("A")||moveKey.equals("a")){
                if(teamX <= 0){
                    System.out.print("Reach left edge of the world, please try a different move:");
                }else if(this.world.isOccupied(teamX-1, teamY)){
                    System.out.print("Inaccessible tile, please try a different move:");
                }else{
                    this.team.moveLeft();
                    loop = false;
                }
            }else if(moveKey.equals("S")||moveKey.equals("s")){
                if(teamY >= this.world.getHeight()){
                    System.out.print("Reach bottom edge of the world, please try a different move:");
                }else if(this.world.isOccupied(teamX, teamY+1)){
                    System.out.print("Inaccessible tile, please try a different move:");
                }else{
                    this.team.moveDown();
                    loop = false;
                }
            }else if(moveKey.equals("D")||moveKey.equals("d")){
                if(teamX >= this.world.getWidth()){
                    System.out.print("Reach right edge of the world, please try a different move:");
                }else if(this.world.isOccupied(teamX+1, teamY)){
                    System.out.print("Inaccessible tile, please try a different move:");
                }else{
                    this.team.moveRight();
                    loop = false;
                }
            }else if(moveKey.equals("Q")||moveKey.equals("q")){
                System.exit(0);
            }else if(moveKey.equals("I")||moveKey.equals("i")){
                this.team.display();
                System.out.print("Time to take a move to the next destination:");
            }else{
                System.out.print("Invalid input, please use W/A/S/D:");
            }
        }while(loop);
    }

    /** Display information about the game */
    public void info(){
        System.out.println();
        System.out.println("       \u001B[33m CONTROL INFO \u001B[0m");
        System.out.println();
        System.out.println("        W/w - move up");
        System.out.println("        A/a - move left");
        System.out.println("        S/s - move down");
        System.out.println("        D/d - move right");
        System.out.println("        Q/q - quit game");
        System.out.println("        I/i - show information");
        System.out.println();
        System.out.println("       \u001B[33m MAP INFO \u001B[0m");
        System.out.println();
        System.out.println("       \u001B[31m X\u001B[0m - team position");
        System.out.println("       \u001B[34m M\u001B[0m - market place");
        System.out.println("        O - inaccessible tile");
        System.out.println();
        // Exit info menu
        System.out.print("Enter C/c to continue play or Q/q to exit game:");
        String infoKey;
        boolean loop = true;
        Scanner in = new Scanner(System.in);
        do{
            infoKey = in.nextLine();
            if(infoKey.equals("Q")||infoKey.equals("q")){
                System.exit(0);
            }else if(infoKey.equals("C")||infoKey.equals("c")){
                loop = false;
            }
        }while(loop);
    }

    /* Generate Tile ID Matrix */
    // TODO: Generate 3 Lanes (Top, Mid, and Bot) where each lane has randomly
    //       assigned Tiles
    //       NOTE: The first row is the Monster's Nexus, The last row is the Hero's Nexus,
    //             and there are inaccessible tiles in between each lane
    public int[][] generateTileIdMatrix() {

        // Variables for the matrix of tileIds
        int w = this.world.getWidth();
        int h = this.world.getHeight();
        int[][] tileIds = new int[w][h];

        // Make mapping of probabilities for possible tiles
        ArrayList<Float> probabilities = new ArrayList<Float>();
        probabilities.add(0.5f);
        probabilities.add(0.2f);
        probabilities.add(0.3f);

        // Choosing the tile Ids to place in the specific cell
        for (int row = 0; row < w; row++) {
            for (int col = 0; col < h; col++) {

                // Variables for choosing the random tile type
                float rand = (float) Math.random();
                float currProbabilityThreshold = 0;
                int id = -1;

                // Figuring out the tile to place
                for (int i = 0; i < probabilities.size(); i++) {
                    currProbabilityThreshold += probabilities.get(i);
                    if (rand <= currProbabilityThreshold) {
                        id = i;
                        break;
                    }
                }

                // Default tile if none chosen
                if (id == -1) {
                    id = probabilities.size() - 1;
                }

                // Assigning the tileId
                tileIds[row][col] = id;
                
            }
        }

        return tileIds;
    }

    private boolean validMove(String directionCharacter) {
        String standardDirectionChar = directionCharacter.toLowerCase();
        boolean result = false;

        Coordinate2D currentCoords = this.team.getCoords();
        Tile currTile = this.world.get(currentCoords);
        Coordinate2D destination = null;
        Tile destinationTile = null;

        if (standardDirectionChar.equals("w")) {
            // Go up
            destination = new Coordinate2D(currentCoords.getX(),currentCoords.getY() + 1);
        }
        
        else if (standardDirectionChar.equals("a")) {
            // Go left
            destination = new Coordinate2D(currentCoords.getX() - 1,currentCoords.getY());
        }

        else if (standardDirectionChar.equals("s")) {
            // Go down
            destination = new Coordinate2D(currentCoords.getX(),currentCoords.getY() - 1);
        }

        else if (standardDirectionChar.equals("d")) {
            // Go right
            destination = new Coordinate2D(currentCoords.getX() + 1,currentCoords.getY());
        }

        // Check if destination it inside the board
        if (!this.world.insideBoard(destination))  {
            return false;
        }

        // Get the tile specified by the destination coordinates
        destinationTile = this.world.get(destination);

        // Generate Blacklisted tiles (Inaccessible Tiles) TODO: Optimize! I don't need to create this everytime for the game
        Set<Class<? extends Tile>> blackList = new HashSet<>();
        blackList.add(InaccessibleTile.class);

        // Find result
        result = !blackList.contains(destinationTile.getClass());

        return result;
    }

}
