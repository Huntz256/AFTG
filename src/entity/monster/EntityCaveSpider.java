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
        this.experienceValue = 15; //Increased EXP to balance the buffs
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
            	//Now has increased poison duration and more negative effects
            	
                byte b0 = 3; //Default: b0 = 0;

                if (this.worldObj.difficultySetting > 1)
                {
                    if (this.worldObj.difficultySetting == 2)
                    {
                        b0 = 8; //Default: b0 = 7;
                    }
                    else if (this.worldObj.difficultySetting == 3)
                    {
                        b0 = 16; //Default: b0 = 15;
                    }
                }
                
                if (b0 > 0)
                {
                	
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, b0 * 20, 0));
 
                    //Apply hunger IV; apply an additional random negative effect from the following; then apply fire resistance
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, b0 * 20, 3));
                    
                    Random random = new Random();
                    switch(random.nextInt(7))
                	{
                	case 0:
                		((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, b0 * 20, random.nextInt(6) + 4)); break; //Hunger V - X (0 == I) 
                	case 1:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, b0 * 20, random.nextInt(4))); break; //Nausea I-IV 
                	case 2:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, b0 * 20, random.nextInt(4))); break; //Slowness I-IV 
                	case 3:
	                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, b0 * 20, random.nextInt(4))); break; //I-IV 
                	case 4:
                		((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.weakness.id, b0 * 20, random.nextInt(2))); break; //Weakness I-II 
                	case 5: case 6:
                		((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, b0 * 20, 1)); break; //Blindness II 
                	}
                    ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.fireResistance.id, b0 * 20, 1)); //Fire Resistance II
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
