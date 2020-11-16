/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Play the game
 **/
import java.util.*;

public class Monsters_and_Heroes{
    
    public static void main(String[] args) {
        // Load neccessary datas
        System.out.println("\u001B[36m Loading Game data......\u001B[0m");
        // Load items
        ArrayList<Weapon> weapons = ItemLoader.loadWeapons("data/Weaponry.txt");
        ArrayList<Armor> armors = ItemLoader.loadArmors("data/Armory.txt");
        ArrayList<Potion> potions = ItemLoader.loadPotions("data/Potions.txt");
        ArrayList<Spell> spells = ItemLoader.loadFireSpells("data/FireSpells.txt");
        spells.addAll(ItemLoader.loadIceSpells("data/IceSpells.txt"));
        spells.addAll(ItemLoader.loadLightningSpells("data/LightningSpells.txt"));
        // Load Heroes
        ArrayList<Warrior> warList = HeroLoader.loadWarriors("data/Warriors.txt");
        ArrayList<Sorcerer> socList = HeroLoader.loadSorcerers("data/Sorcerers.txt");
        ArrayList<Paladin> paList = HeroLoader.loadPaladins("data/Paladins.txt");
        // Load Monsters
        ArrayList<Dragon> draList = MonsterLoader.loadDragons("data/Dragons.txt");
        ArrayList<Exoskeleton> exoList = MonsterLoader.loadExoskeletons("data/Exoskeletons.txt");
        ArrayList<Spirit> spiList = MonsterLoader.loadSpirits("data/Spirits.txt");
        
        // Set up game
        System.out.println("\u001B[36m Setting up the legend......\u001B[0m");
        LegendMarket market = new LegendMarket(weapons, armors, potions,spells);
        HeroCollect heroCollect = new HeroCollect(warList, socList, paList);
        MonsterCollect monsterCollect = new MonsterCollect(draList, exoList, spiList);
        LegendWorld world = new LegendWorld();
        LegendGame game = new LegendGame(world, heroCollect, monsterCollect, market);
        game.world.generate();
        System.out.println("Load finish.");
        System.out.println();
        
        // Start game
        game.welcome();
        game.play();
    }
}
