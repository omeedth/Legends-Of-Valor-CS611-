# Creators

***Contributors:*** Zhuyun Chen,    Alex Thomas </br>
***Emails:***       zchen98@bu.edu, aothomas@bu.edu </br>
***BU IDs:***       U38119774,      U06877336 </br>

# CS611 - Object Oriented Text RPG Overview

We are creating a text based RPG game using Object Oriented Design Principles, and building off our previous implementations of Legends: Monsters And Heroes!

# Installation

Download all of the .java files into it's own folder and continue to usage

# Dependencies

1. ***[Jcolor](https://github.com/dialex/JColor)***

# Usage

When Running the program you can compile all of the java source code into runnable byte code by running the following command

***Suggested:*** use the "make.bat" and "clean.bat" files to build the project

1. Navigate into the directory with the "make.bat" and "clean.bat" files

2. Run the "make" command as shown below! (this will remove all .class files inside this directory and subdirectories of where the file is located)
```bash
./make
```

3. Run the "clean" command when you want to remove all .class files in the directory and subdirectories
```bash
./clean
```

***Alternatively:***

```bash
javac Monsters_and_Heroes.java -encoding utf-8
```

You can run the program by using this command

```bash
java Monsters_and_Heroes
```

You can remove all of the class files in the current folder with this command. (Be VERY CAREFUL. This will remove all class files in the folder)
***NOTE:*** since this project has multiple folders, you must go into each directory and run this command
 
```bash
rm *.class
```

# Game Info

***Game rules:*** 

***Control keys:*** </br> 
W/w - move up </br> 
A/a - move left </br>
S/s - move down </br>
D/d - move right </br>

***Map Signs:*** </br> 
X - team position </br>
M - market </br>
O - inaccessible tile </br>

# Game Setup

Below is an example of how you would run our game if you imported it

```java
public static void main(String args[]) {

    // TODO: Write the actual startup code here

    // Start Game
    LegendsMonstersAndHeroes game = new LegendsMonstersAndHeroes();
    game.start();

}
```

# Classes

## Launcher Classes

---

***Monsters_and_Heroes.java*** - The Launcher for the code

## Loader Classes

---

***HeroLoader.java*** - Class used to load Hero data

***MonsterLoader.java*** - Class used to load Monster data

***ItemLoader.java*** - Class used to load Item data

## Collect Class

---

***HeroCollect.java*** - Class represents a hero factory and used generate heroes in legend game

***MonsterCollect.java*** - Class represents a monster factory and used generate monsters in legend game

## Team Classes

---

***Team.java*** - Class represents a team in general, and contains a list of Characters

***PlayerTeam.java*** - Subclass of Team, represents a team that is controled by player

***MonsterTeam.java*** - Subclass of Team, represents a team of monsters

## Tile Classes

---

***Board.java*** - a Board object containing Tile objects

***CommonTile.java*** - a concrete Tile that: 1. Is accessible by the player (enterable),  2. Has a chance of encountering monsters

***Coordinate2D.java*** - the coordinates on a 2D plane (x,y)

***InaccessibleTile.java*** - a Tile that is inaccessible by entities 1. No Players or Monsters can come here

***Interactable.java*** - an interface for Tile objects that have some sort of interaction built into them

***MarketTile.java*** - a Tile that allows a player to purchase from a market 1. Players can stop here to buy/sell items

***Tile.java*** - an abstract Tile object to be used in a board

***TileFactory.java*** - generates the Tile objects necessary for the game

***TileRepresentable.java*** - an interface defining functionality necessary to represent an object on a tile

***TODO***

## Item Classes 

---

***Item.java*** - Class represents an Item in general

***RpgItem.java*** - Subclass of Item, represents an general Item in rpg game with related properties

***Weapon.java*** - Subclass of RpgItem, represents a Weapon item in rpg game

***Armor.java*** - Subclass of RpgItem, represents an Armor item in rpg game

***Potion.java*** - Subclass of RpgItem, represents a Potion item in rpg game

***Spell.java*** - Subclass of RpgItem, represents a Spell item in rpg game

***FireSpell.java*** - Subclass of Spell, represents a Spell of type FireSpell

***IceSpell.java*** - Subclass of Spell, represents a Spell of type IceSpell

***LightningSpell.java*** - Subclass of Spell, represents a Spell of type LightningSpell

***Inventory.java*** - Class represents an inventory in Legend game. An Inventory contains items of type Weapon/Armor/Potion/Spell that's being carried by someone

## Game Classes

---

***Game.java*** - Class represents a game in general. Design in abstract way and can be extend by subclass

***RpgGame.java*** - Sub class of Game, represents a classical rpg game in general, and can be extend to different rpg games

***LegendGame.java*** - Sub class of RpgGame, represents a Legend:Monster&Hero game. The Legend game contains a LegendWorld, a LegendMarket, the HeroCollect and MonsterCollect factory, and a PlayerTeam

## Character Classes

---

***Dragon.java*** - Sub class of Monster, represents a monster that is of type Dragon

***Exoskeleton.java*** - Sub class of Monster, represents a monster that is of type Exoskeleton

***Monsters.java*** - Sub class of BattleCahracter, represents a Monster in the legend game.

***Spirit.java*** - Sub class of Monster, represents a monster that is of type Spirit

***Character.java*** - Class represents a character in general

***BattleCharacter.java*** - Sub class of Character, represents a character that can fight in a battle

***Hero.java*** - Sub class of BattleCahracter, represents a Hero/player controled character in the legend game. A Hero has a dressed Weapon and Armor and carries an Inventory with all belongs

***Paladin.java*** - Sub class of Hero, represents a Hero that is of type Paladin

***Sorcerer.java*** - Sub class of Hero, represents a Hero that is of type Sorcerer

***Warrior.java*** - Sub class of Hero, represents a Hero that is of type Warrior

## Market Classes

---

***Market.java*** - Class represents a market in general

***LegendMarket.java*** - Subclass of Market, represents a Market in Legend game. A market contains items of type Weapon/Armor/Potion/Spell

# Improvements

1. TODO


