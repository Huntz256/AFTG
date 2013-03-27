package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityBlaze; //
import net.minecraft.entity.monster.EntityCaveSpider; //
import net.minecraft.entity.monster.EntitySkeleton; //
import net.minecraft.entity.monster.EntityWitch; //

public class BiomeGenHell extends BiomeGenBase
{
    public BiomeGenHell(int par1)
    {
        super(par1);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 20, 4, 4)); //Default 1, 4, 4
        
        //The Nether may spawn Blazes, Nether Skeletons, Witches, and Cave Spiders anywhere
        this.spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 6, 1, 2)); 
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCaveSpider.class, 8, 1, 1)); 
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 2, 3)); //10, 2, 3
        
        //
    }
}
