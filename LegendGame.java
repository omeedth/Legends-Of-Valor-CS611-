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
    protected HeroTeam team;
    protected MonsterTeam monsterTeam;
    
    protected int turn; // Keep track number of turns to spawn monster
    
   /* Default constructor */
    LegendGame(){
        super();
        this.team = new HeroTeam();
        this.turn = 0;
    }
    
    /* Default constructor */
    LegendGame(Board world, HeroCollect heroList, MonsterCollect monsterList, LegendMarket market){
        super(world);
        this.heroList = heroList;
        this.monsterList = monsterList;
        this.market = market;
        this.team = new HeroTeam();
        this.monsterTeam = new MonsterTeam();
        this.turn = 0;
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

        /* Generate Board */
        int[][] tileIds = generateTileIdMatrix();
        this.world.fillMatrixFromTileIdMatrix(tileIds);

        /* Generate start location on the board for the heroes */
        Set<Class<? extends Tile>> blackListedTiles = new HashSet<>();
        blackListedTiles.add(InaccessibleTile.class);
        Coordinate2D generatedPoint = this.world.getLocationOnBoard(blackListedTiles,true);

        /* Set Position of Heroes */

        // Check if valid player coordinates were found
        if (generatedPoint == null) {
            System.out.println(this.world);
            throw new IllegalArgumentException("The map had no valid starting location for the party!");
        }

        Tile startTile = this.world.get(generatedPoint);
        this.team.move(startTile);
        startTile.setPiece(this.team);
        
        /* Generate monster team of size 3 */
        this.monsterTeam = this.monsterList.generateTeam(3, this.team.getLevel());

    }
    
    
    /** Play the game based on game rules */
    public void play(){
        boolean gameFlag = true; //Set to false when either team reach nexus of opponent
        do{
            // Regain HP & Mana
            this.team.regain();
            // Spawn new monster with current team level in monster Nexus every 8 turns
            if(this.turn>= 8){
                this.monsterTeam = this.monsterList.extendTeam(this.monsterTeam,3,this.team.getLevel());
                this.turns = 0;
            }
            /* Show world map before move */
            this.world.displayMap();
            // All alive Hero & monsters take action
            this.battle();
            // Count turns
            this.turn += 1;
            // Check if win
            if(this.team.isWin()){ gameFlag = false;}
            else if(this.monsterTeam.isWin()){ gameFlag = false;}
            // NO NEED TO MOVE TEAM: MOVE FOR HERO & MONSTER SHOULD BE HANDLE INDIVIDUALLY
            /* Move team to new tile pos */
            // this.move(); // EDITED - I switched to use my performTurn method which should handle movement and tile interaction
            //this.performTurn();
            /* Launch event on that tile */
            // this.event(); // EDITED - I switched to have this be incorporated in the performTurn() method
        }while(gameFlag);
        gameOver();
    }
    
    // public void event(){
    //     // Get curret pos of the team
    //     int teamX = this.team.getXPos();
    //     int teamY = this.team.getYPos();
    //     Tile destTile = this.world.getTile(teamX,teamY); // Modified
    //     String eSign = destTile.toString();
    //     // If current tile is a Market, trade
    //     if(eSign.equals("\u001B[34mM\u001B[0m")){
    //         System.out.println();
    //         System.out.println("\u001B[32m You Found a Market. \u001B[0m");
    //         System.out.println();
    //         this.trade();
    //     }else{
    //         // If current tile is common, encounter a battle with 80% chance
    //         if(Math.random()<0.80){
    //             System.out.println();
    //             System.out.println("\u001B[31m You Encountered a Fight. \u001B[0m");
    //             System.out.println();
    //             this.battle();
    //         }else{
    //             System.out.println();
    //             System.out.println("\u001B[35m Nothing Special Happend. \u001B[0m");
    //             System.out.println();
    //             this.common();
    //         }
    //     }
    //     // Update change to the world
    //     // this.world.updateTeamPos(this.team.getXPos(),this.team.getYPos());
    //     Tile source = new CommonTile(new Coordinate2D());
    //     this.world.movePiece(this.team, source, destTile);
    // }
    
    /** Trade in market */
    public void trade(){
        this.market.welcome(this.team);
    }
    
    /** Common event when reach empty slot */
    // public void common(){
    //     System.out.print("Enter R/r to take a rest, or any other key to continue adventure:");
    //     Scanner in = new Scanner(System.in);
    //     String optKey = in.nextLine();
    //     if(optKey.equals("Q")||optKey.equals("q")){
    //         System.exit(0);
    //     }else if(optKey.equals("R")||optKey.equals("r")){
    //         // Display heros in team
    //         this.team.display();
    //         boolean loop = true;
    //         do{
    //             System.out.print("Enter Hero ID to view a hero, or C/c to continue adventure:");
    //             optKey = in.nextLine();
    //             if(optKey.equals("Q")||optKey.equals("q")){
    //                 System.exit(0);
    //             }else if(optKey.matches("\\d+")){
    //                 int heroId = Integer.parseInt(optKey);
    //                 if(heroId>0 && heroId<= this.team.size()){
    //                     this.viewHero((Hero)this.team.getMember(heroId-1));
    //                 }else{
    //                     System.out.print("Invalid input, enter Hero ID to view a hero, or C/c to continue adventure:");
    //                 }
    //             }else if(optKey.equals("C")==false && optKey.equals("c")==false){
    //                 System.out.print("Invalid input, enter Hero ID to view a hero, or C/c to continue adventure:");
    //             }
    //         }while(loop);
    //     }
    // }
    
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
     * Each alive hero and monster take their turns
     */
    
    public void battle(){
        /* Let each member in team take turn */
        this.team.takeTurn(this.world);
        /* Let each monster take turn */
        this.monsterTeam.takeTurn(this.world);
    }
    
    
    /** GAMEOVER WHEN EITHER MONSTER OR PLAYER REACH NEXUS */
    public void gameOver(){
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        System.out.println("       \u001B[31m GAME END \u001B[0m");
        System.out.println();
        System.out.println(" \u001B[31m ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ \u001B[0m ");
        System.out.println();
        System.exit(0);
    }

    /* ADDED CODE (CODE FOR THE TURN) */
    /*
    public void performTurn() {
        // TODO:
        //  1. have player move to a different tile
        //  2. run tile logic (EX: monster spawn with percentage chance on CommonTile)

        Scanner input = new Scanner(System.in);

        // Get player input for movement
        String choice = KeyboardInputUtility.promptTillValid(input, "Please input where you would like to go (W,A,S,D) or (quit/QUIT): ", (inputStr) -> {
            String str = inputStr.toLowerCase();
            if (str.equals("quit")) return true;
            return new HashSet<String>(Arrays.asList(new String[] {"w","a","s","d"})).contains(str) && validMove(str);
        });

        // If you want to quit the game exit this method early
        if (choice.equals("quit")) {
            System.exit(0);
            return;
        }

        // Move the party TODO: Consolidate repeated code
        choice.toLowerCase();
        Coordinate2D currentCoords = this.team.getCoords();
        Tile currTile = this.world.get(currentCoords);
        Coordinate2D destination = null;
        Tile destinationTile = null;

        if (choice.equals("w")) {
            // Go up
            destination = new Coordinate2D(currentCoords.getX(),currentCoords.getY() + 1);
        }
        
        else if (choice.equals("a")) {
            // Go left
            destination = new Coordinate2D(currentCoords.getX() - 1,currentCoords.getY());
        }

        else if (choice.equals("s")) {
            // Go down
            destination = new Coordinate2D(currentCoords.getX(),currentCoords.getY() - 1);
        }

        else if (choice.equals("d")) {
            // Go right
            destination = new Coordinate2D(currentCoords.getX() + 1,currentCoords.getY());
        }

        destinationTile = this.world.get(destination);

        System.out.println("Source: " + currTile + ", Destination: " + destinationTile);
        this.world.movePiece(this.team, currTile, destinationTile);

        // Run tile logic:
        //  MarketTile:
        //      1. Show market (the items they possess and the prices, level cap etc.)
        //      2. Show heroes' Inventory and current money
        //  CommonTile:
        //      1. Roll a die to see if there is a monster encounter
        //          - If no monster encounter show map again and allow the players to rest
        //      2. If monster encounter show battle scene
        //      3. Allow options to equip, attack, cast, use item
        //      4. Once the battle is over show experience gained and possible level up screen
        //          - If players died then make them lose experience, gold, etc.
        if (destinationTile instanceof Interactable) {
            ((Interactable) destinationTile).interact(this.team);
        }

        KeyboardInputUtility.promptTillValid(input, "Press \"Enter\" to continue", (str) -> true);
        System.out.println();

    }
    */
    /*--------------------------------*/
    
    /** Move team on the world map */
    // public void move(){
    //     System.out.print("Time to take a move to the next destination:");
    //     // Get curret pos of the team
    //     int teamX = this.team.getXPos();
    //     int teamY = this.team.getYPos();
        
    //     String moveKey;
    //     Scanner in = new Scanner(System.in);
    //     boolean loop = true;
    //     do{
    //         moveKey = in.nextLine();
    //         if(moveKey.equals("W")||moveKey.equals("w")){
    //             if(teamY <= 0){
    //                 System.out.print("Reach top edge of the world, please try a different move:");
    //             }else if(this.world.isOccupied(teamX, teamY-1)){
    //                 System.out.print("Inaccessible tile, please try a different move:");
    //             }else{
    //                 this.team.moveUp();
    //                 loop = false;
    //             }
    //         }else if(moveKey.equals("A")||moveKey.equals("a")){
    //             if(teamX <= 0){
    //                 System.out.print("Reach left edge of the world, please try a different move:");
    //             }else if(this.world.isOccupied(teamX-1, teamY)){
    //                 System.out.print("Inaccessible tile, please try a different move:");
    //             }else{
    //                 this.team.moveLeft();
    //                 loop = false;
    //             }
    //         }else if(moveKey.equals("S")||moveKey.equals("s")){
    //             if(teamY >= this.world.getHeight()){
    //                 System.out.print("Reach bottom edge of the world, please try a different move:");
    //             }else if(this.world.isOccupied(teamX, teamY+1)){
    //                 System.out.print("Inaccessible tile, please try a different move:");
    //             }else{
    //                 this.team.moveDown();
    //                 loop = false;
    //             }
    //         }else if(moveKey.equals("D")||moveKey.equals("d")){
    //             if(teamX >= this.world.getWidth()){
    //                 System.out.print("Reach right edge of the world, please try a different move:");
    //             }else if(this.world.isOccupied(teamX+1, teamY)){
    //                 System.out.print("Inaccessible tile, please try a different move:");
    //             }else{
    //                 this.team.moveRight();
    //                 loop = false;
    //             }
    //         }else if(moveKey.equals("Q")||moveKey.equals("q")){
    //             System.exit(0);
    //         }else if(moveKey.equals("I")||moveKey.equals("i")){
    //             this.team.display();
    //             System.out.print("Time to take a move to the next destination:");
    //         }else{
    //             System.out.print("Invalid input, please use W/A/S/D:");
    //         }
    //     }while(loop);
    // }

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

    /* ADDED - Generate the map for Legends of Valor (Top,Mid,Bot) lanes */

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
        // ArrayList<Float> probabilities = new ArrayList<Float>(); // Probabilities of Tiles
        // probabilities.add(0.2f);
        // probabilities.add(0.2f);
        // probabilities.add(0.2f);
        // probabilities.add(0.4f);

        Map<Float,Integer> probabilityMapping = new HashMap<Float,Integer>();
        probabilityMapping.put(0.2f,TileFactory.BUSH);
        probabilityMapping.put(0.2f,TileFactory.CAVE);
        probabilityMapping.put(0.2f,TileFactory.KOULOU);
        probabilityMapping.put(0.4f,TileFactory.PLAIN);

        /* Lane Variables */
        int laneWidth = 2;
        int laneBufferWidth = 1;
        int numLanes = ((w + laneBufferWidth) / (laneWidth + laneBufferWidth));

        // System.out.println("Number of Lanes: " + numLanes); // CORRECT

        for (int lane = 0; lane < numLanes; lane++) {
            int offset = (laneWidth * lane) + (lane * laneBufferWidth);
            // System.out.printf("Lane %d: Offset = %d\n",lane,offset); // CORRECT
            generateTileIdMatrixForLane(laneWidth, offset, tileIds);

            if (lane == numLanes - 1) {
                continue;
            }

            int laneOffset = offset + laneWidth;
            for (int buffer = 0; buffer < laneBufferWidth; buffer++) {             
                // System.out.printf("Lane %d: Buffer Offset = %d\n",lane, laneOffset); // CORRECT
                addBufferColumn(buffer + laneOffset, TileFactory.INACCESSIBLE, tileIds);
            }            
        }

        return tileIds;
    }

    public int[][] addBufferColumn(int column, int bufferTileId, int[][] tileIds) {

        assert tileIds != null;
        assert tileIds.length > 0 && column < tileIds[0].length;

        for (int row = 0; row < this.world.getHeight(); row++) {
            tileIds[row][column] = bufferTileId;
        }

        return tileIds;

    }

    /* Generate Tile ID Matrix For a single Lane */
    // TODO: Generate a Lane (Top, Mid, and Bot) where each lane has randomly
    //       assigned Tiles
    //       NOTE: The first row is the Monster's Nexus, The last row is the Hero's Nexus,
    //             and there are inaccessible tiles in between each lane
    public int[][] generateTileIdMatrixForLane(int laneWidth, int horizontalOffset, int[][] tileIds) {

        assert this.world.getHeight() > 0;
        assert laneWidth >= 0 && (laneWidth + horizontalOffset) < this.world.getWidth();
        assert tileIds != null && tileIds.length > 0 && laneWidth < tileIds[0].length;

        // Variables for the matrix of tileIds
        int w = laneWidth;
        int h = this.world.getHeight();

        // Make mapping of probabilities for possible tiles
        // ArrayList<Float> probabilities = new ArrayList<Float>(); // Probabilities of Tiles
        // probabilities.add(0.5f);
        // probabilities.add(0.2f);
        // probabilities.add(0.3f);

        Map<Integer,Float> probabilityMapping = new HashMap<Integer,Float>();
        probabilityMapping.put(TileFactory.BUSH,0.2f);
        probabilityMapping.put(TileFactory.CAVE,0.2f);
        probabilityMapping.put(TileFactory.KOULOU,0.2f);
        probabilityMapping.put(TileFactory.PLAIN,0.4f);

        /* Nexus Tile ID */
        int NEXUS_TILE_ID = TileFactory.NEXUS;

        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[0][col + horizontalOffset] = NEXUS_TILE_ID;
        }

        // Choosing the tile Ids to place in the specific cell
        for (int row = 1; row < (h-1); row++) {
            for (int col = 0; col < w; col++) {

                int id = getTileIdFromProbabilityList(probabilityMapping);

                // Assigning the tileId
                tileIds[row][col + horizontalOffset] = id;
                
            }
        }

        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[h-1][col + horizontalOffset] = NEXUS_TILE_ID;
        }
        return tileIds;
    }

    public int getTileIdFromProbabilityList(Map<Integer,Float> probabilities) {
        // Variables for choosing the random tile type
        float rand = (float) Math.random();
        float currProbabilityThreshold = 0;
        int id = -1;

        // Figuring out the tile to place
        List<Integer> keys = new ArrayList<Integer>(probabilities.keySet());
        for (Integer probabilitykey : keys) {
            currProbabilityThreshold += probabilities.get(probabilitykey);
            if (rand <= currProbabilityThreshold) {
                id = probabilitykey;
                break;
            }
        }

        // For List<Float>
        // for (int i = 0; i < probabilities.size(); i++) {
        //     currProbabilityThreshold += probabilities.get(i);
        //     if (rand <= currProbabilityThreshold) {
        //         id = i;
        //         break;
        //     }
        // }

        // Default tile if none chosen
        if (id == -1) {
            id = keys.get(keys.size() - 1);
        }

        return id;
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

    /*-------------------------------------------------------------------------------*/

}
