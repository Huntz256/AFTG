AFTG
====
This is the source code for a Minecraft mod that increases the game's difficulty.  
Version Alpha V0.9 for MC 1.5.1.  

Implemented Features 
---------------------
Under certain conditions, lethal Arrows From The Ground will shoot upwards in volleys.  

Most mobs do 1.5x damage.  
Many mobs are more likely to use enchanted weapons or pick up loot.  
Some mobs can detect the player from a much greater distance.  
Some mobs have been given a small speed boost.  
Many mobs drop more experience and/or more loot than before.


Creeper: they are more aggressive and destructive than before. They may explode even if they don't see you, and they cannot stop their fuse once it starts. Their explosion power has been increased as well, with a small cost of a slightly higher fuse time. On death, they drop weak primed TNT. 

Zombie: heavily armored, they can break down doors on any difficulty and have an increased chance of equipping a sword or a shovel. Player detection radius is also noticeably high. 

Skeleton: also heavily armored, they also shoot faster. With a high chance of using an enchanted bow, they can shoot more powerful arrows.

Spider: beware their venom; they can cause very brief poison and hunger effects on contact. They also sometimes spawn web blocks in their path. Player detection radius is also noticeably high. 

Cave Spider: in addition to everything that the Spider has, the Cave Spider has an upgraded venom that can cause temporary blindness, nausea, hunger, weakness, slowness, and/or death. The venom also works on any difficulty and the length of effect has been very slightly increased.

Silverfish: they give brief Weakness/Nausea effects.

Endermen: they do more damage.


Nether:
Blazes, Wither Skeletons, and Cave Spiders may now spawn anywhere in the Nether. Witches have a small chance to spawn near Nether structures. 

Blazes: they spawn fire blocks in their path. Blazes may spawn deep in Overworld caves, but those that do don't drop blaze rods.  

Zombie Pigman: they will get angry if you get too close. They have a high chance of equipping an enchanted sword.

Ghasts: fireballs are 2x as powerful. 

Magma Slimes: can ignite the player on contact; health is 2x normal.

Other:
Roses do damage on contact due to their sharp thorns. Fire spreads more easily. All raw foods (beef, pork, chicken, fish) have an 80% chance to give Hunger effects. Raw zombie meat has a 100% chance to give Hunger effects. Spider eyes and poisonous potatoes give Poison II. 

Installation
--------------
This all assumes you already have installed Minecraft 1.5.1 and you have a clean minecraft.jar. This is a work in progress mod and it is my first mod. I'm not sure how compatible it is with other mods, but it has tested positive to work as normal with all the mods I have tried with it so far: Optifine, Rei's Minimap, and Single Player Commands.

Non-Modders:  
1. Download Forge 7.7.0.605 here: 
http://files.minecraftforge.net/minecraftforge/minecraftforge-universal-1.5.1-7.7.0.605.zip  
2. Download AFTG here: https://dl.dropbox.com/s/yqaszzqmodaijh1/%5B1.5.1%5D%20AFTG%20v0.9.zip?token_hash=AAEL-5fplQxCpLwiL6XifAIgjqokrt-fU0TKLZuk8Ua8rA&dl=1  
3. Download and start Magic Launcher here: http://www.minecraftforum.net/topic/939149-launcher-magic-launcher-100-mods-options-news/  
4. Click Setup, click Add, and select the downloaded files from steps 1 and 2.  
5. Click OK and login!  
6. Create a NEW world and play. 

Modders:  
(Assuming you use Eclipse.)  
1. Download Forge 7.7.605 source here: http://files.minecraftforge.net/minecraftforge/minecraftforge-src-1.5.1-7.7.0.605.zip  
2. Extract to a folder and run install.cmd.  
3. Go to forge/mcp/src/minecraft/net/minecraft and copy the contents of /srs from GitHub into there.  
4. Start Eclipse and select forge/mcp/eclipse as the Workspace.







