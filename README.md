=== Assignment 3 ===
Contributor: Zhuyun Chen
Email: zchen98@bu.edu
BU ID: U38119774

========================
== Overview ===
========================

This submission implements the Legend:Monster&Heroes game.

To compile, run command: javac *.java
To start the game, run command: java Monsters_and_Heroes
To play the game, follow instructions on screen.
The ./data folder includes all datas for the game, you may change or add new datas to personalize the game. Data formats are included in the readme file in ./data folder.

Game rules:
Control keys:
W/w - move up
A/a - move left
S/s - move down
D/d - move right

Map Signs:
X - team position
M - market
O - inaccessible tile

DRIVER CLASS
== Monsters_and_Heroes==
Main  driver class to play the game.

LOADER CLASS
== HeroLoader ==
Class used to load Hero datas.
== MonsterLoader ==
Class used to load Monster datas.
== ItemLoader ==
Class used to load Item datas.

COLLECT CLASS
== HeroCollect =
Class represents a hero factory and used generate heroes in legend game.
== MonsterCollect =
Class represents a monster factory and used generate monsters in legend game.

GAME CLASS
== Game ==
Class represents a game in general. Design in abstract way and can be extend by subclass.
== RpgGame ==
Sub class of Game, represents a classical rpg game in general, and can be extend to different rpg games.
== LegendGame ==
Sub class of RpgGame, represents a Legend:Monster&Hero game. The Legend game contains a LegendWorld, a LegendMarket, the HeroCollect and MonsterCollect factory, and a PlayerTeam.

TEAM CLASS
== Team ==
Class represents a team in general, and contains a list of Characters.
== PlayerTeam ==
Subclass of Team, represents a team that is controled by player.
== MonsterTeam ==
Subclass of Team, represents a team of monsters.

WORLD CLASS
== World ==
Class represents a game world in general.
== LegendWorld ==
Subclass of World, represents the world for legend game. 

CHARACTER CLASS
== Character ==
Class represents a character in general.
== BattleCahracter ==
Sub class of Character, represents a character that can fight in a battle.
== Monster ==
Sub class of BattleCahracter, represents a Monster in the legend game.
== Hero ==
Sub class of BattleCahracter, represents a Hero/player controled character in the legend game. A Hero has a dressed Weapon and Armor and carries an Inventory with all belongs.
== Dragon ==
Sub class of Monster, represents a monster that is of type Dragon. 
== Exoskeleton ==
Sub class of Monster, represents a monster that is of type Exoskeleton. 
== Spirit ==
Sub class of Monster, represents a monster that is of type Spirit. 
== Warrier ==
Sub class of Hero, represents a Hero that is of type Warrior.
== Sorcerer ==
Sub class of Hero, represents a Hero that is of type Sorcerer. 
== Paladin ==
Sub class of Hero, represents a Hero that is of type Paladin. 

ITEM CLASS
== Item ==
Class represents an Item in general.
== RpgItem ==
Subclass of Item, represents an general Item in rpg game with related properties.
== Weapon ==
Subclass of RpgItem, represents a Weapon item in rpg game.
== Armor ==
Subclass of RpgItem, represents an Armor item in rpg game.
== Potion ==
Subclass of RpgItem, represents a Potion item in rpg game.
== Spell ==
Subclass of RpgItem, represents a Spell item in rpg game.
== FireSpell ==
Subclass of Spell, represents a Spell of type FireSpell.
== IceSpell ==
Subclass of Spell, represents a Spell of type IceSpell.
== LightningSpell ==
Subclass of Spell, represents a Spell of type LightningSpell.

MARKET CLASS
== Market ==
Class represents a market in general.
== LegendMarket ==
Subclass of Market, represents a Market in Legend game. A market contains items of type Weapon/Armor/Potion/Spell.

== Inventory ==
Class represents an inventory in Legend game. An Inventory contains items of type Weapon/Armor/Potion/Spell that's being carried by someone.
