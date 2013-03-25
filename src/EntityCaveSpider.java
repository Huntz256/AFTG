package net.minecraft.entity.monster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import java.util.Random; //

public class EntityCaveSpider extends EntitySpider
{
    public EntityCaveSpider(World par1World)
    {
        super(par1World);
        this.texture = "/mob/cavespider.png";
        this.setSize(0.7F, 0.5F);
    }

    public int getMaxHealth()
    {
        return 12;
    }

    @SideOnly(Side.CLIENT)

    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount()
    {
        return 0.7F;
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (par1Entity instanceof EntityLiving)
            {
                byte b0 = 0;

                if (this.worldObj.difficultySetting > 1)
                {
                    if (this.worldObj.difficultySetting == 2)
                    {
                        b0 = 7;
                    }
                    else if (this.worldObj.difficultySetting == 3)
                    {
                        b0 = 15;
                    }
                }
                
                if (b0 > 0)
                {
                	
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, b0 * 20, 0));
 
                    //Apply blindness + hunger; apply an additional random negative effect from the following; then apply water breathing
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, b0 * 20, 1)); 
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, b0 * 20, 3));
                    
                    Random random = new Random();
                    switch(random.nextInt(5))
                	{
                	case 0:
                		((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, b0 * 20, random.nextInt(11) + 4)); break; //Hunger V - XV (0 == I) 
                	case 1:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, b0 * 20, random.nextInt(4))); break; //Nausea I-IV (0 == I)
                	case 2:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, b0 * 20, random.nextInt(4))); break; //Slowness I-IV (0 == I)
                	case 3:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, b0 * 20, random.nextInt(4))); break; //I-IV (0 == I)
                	case 4:
                		((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, b0 * 20, random.nextInt(2))); break; //Weakness I-II (0 == I)
                	}
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.waterBreathing.id, b0 * 20, 2));
                    //
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Initialize this creature.
     */
    public void initCreature() {}
}
