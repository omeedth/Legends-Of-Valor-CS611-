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
    protected Board world;
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

        /* Generate monster team */
        this.monsterTeam = this.monsterList.generateTeam(3, this.team.getLevel());
        
        int[][] tileIds = generateTileIdMatrix();
        this.world.generateAllLanes(generateTileIdMatrixValor);

        // TO-DO: PLACE HEROES AND MONSTERS ON NEXUS ON EACH LANE
        // function in lane/board return a list of nexus to place hero/monster
        this.team.spawn(world.getHeroNexus());
        this.monsterTeam.spawn(world.getMonsterNexus());
    }
    
    
    /** Play the game based on game rules */
    public void play(){
        boolean gameFlag = true; //Set to false when either team reach nexus of opponent
        do{
            // Regain HP & Mana
            this.team.regain();
            // Spawn new monster with current team level in monster Nexus every 8 turns
            if(this.turn >= 8){
                // Generate new team of monsters
                MonsterTeam newTeam = this.monsterList.generateTeam(3,this.team.getLevel());
                // Spawn monsters in new team
                //newTeam.spawn(nexusList);
                // Join the new spawned monsters with exist monster
                this.monsterTeam.joinTeam(newTeam);
                this.turn = 0;
            }
            /* Show world map before move */
            this.world.displayMap();
            // All alive Hero & monsters take action
            this.battle();
            // Count turns
            this.turn += 1;
            // Check if win
            if(this.team.isWin(this.world)){ gameFlag = false;}
            else if(this.monsterTeam.isWin(this.world)){ gameFlag = false;}
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
    
    
        /* Generate Tile ID Matrix For a single Lane */
        // TODO: Generate a Lane (Top, Mid, and Bot) where each lane has randomly
        //       assigned Tiles
        //       NOTE: The first row is the Monster's Nexus, The last row is the Hero's Nexus,
        //             and there are inaccessible tiles in between each lane
    public int[][] generateTileIdMatrixValor(Lane board) {
        
        int w = board.getWidth();
        int h = board.getHeight();
        int[][] tileIds = new int[h][w];
        
        Map<Integer,Float> probabilityMapping = new HashMap<Integer,Float>();
        probabilityMapping.put(TileFactory.BUSH,0.2f);
        probabilityMapping.put(TileFactory.CAVE,0.2f);
        probabilityMapping.put(TileFactory.KOULOU,0.2f);
        probabilityMapping.put(TileFactory.PLAIN,0.4f);
        
        /* Nexus Tile ID */
        int MONSTER_NEXUS_TILE_ID = TileFactory.MONSTER_NEXUS;
        int HERO_NEXUS_TILE_ID = TileFactory.HERO_NEXUS;
        
        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[0][col] = MONSTER_NEXUS_TILE_ID;
        }
        
        // Choosing the tile Ids to place in the specific cell
        for (int row = 1; row < (h-1); row++) {
            for (int col = 0; col < w; col++) {
                int id = getTileIdFromProbabilityList(probabilityMapping);
                // Assigning the tileId
                tileIds[row][col] = id;
            }
        }
        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[h-1][col] = HERO_NEXUS_TILE_ID;
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
        if (id == -1) {
            id = keys.get(keys.size() - 1);
        }
        return id;
    }
}
