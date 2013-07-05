package net.minecraft.entity.monster;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob
{
	private int webAmount = 6 / 2; //Max # of web blocks able to be spun; this is 6 even though the value of the variable is 3
	
    public EntitySpider(World par1World)
    {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.setSize(1.4F, 0.9F);
        this.moveSpeed = 1.3F; //Default: 0.8F 
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }

    //Have a chance to spawn web blocks 
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	//If amount of web held is more than zero, have a chance to spawn web blocks given than that nothing is in the way
    	if(this.webAmount > 0)	
    	{
		    Random random = new Random();
		    if(random.nextInt(7000) == 0) 
		    {
		    	if(this.worldObj.getBlockId((int)posX, (int)posY, (int)posZ) == 0)
		    	{
			    	this.worldObj.setBlock((int)posX, (int)posY, (int)posZ, 30);
			    	this.webAmount--;
		    	}
		    }    
    	}
    }
    //
    
    public int getMaxHealth()
    {
        return 16;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.75D - 0.5D;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        float f = this.getBrightness(1.0F);

        if (f < 0.5F)
        {
            double d0 = 64.0D; //Default 16.0D 
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.spider.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
        float f1 = this.getBrightness(1.0F);

        if (f1 > 0.5F && this.rand.nextInt(100) == 0)
        {
            this.entityToAttack = null;
        }
        else
        {
            if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0)
            {
                if (this.onGround)
                {
                    double d0 = par1Entity.posX - this.posX;
                    double d1 = par1Entity.posZ - this.posZ;
                    float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                    this.motionX = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                    this.motionZ = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                    this.motionY = 0.4000000059604645D;
                }
            }    
            else 
            {
                super.attackEntity(par1Entity, par2);
            }
            
            //Spiders now have... poison!
            //Add Hunger III and Poison I for 1 second
            if (par2 < 1.5F)
            {
                ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 2 * 20, 2)); 
                ((EntityLiving)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 2 * 20, 0));
            }
            //
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.silk.itemID;
    }

    /**
     * Drop items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        //Default: super.dropFewItems(par1, par2 + 1);
    	//Spiders now drop items depending on the amount of web held
    	
    	int j = this.getDropItemId();
    	
    	if (j > 0)
        {	
    		//If web amount is more than zero...
	    		//If looting level is above zero, let the Spider drop 0 to k amount of string, where k is the amount of web it has (0 to 6).
	    		//Otherwise let the Spider drop 0 to k*2 amount of string (0 to 3).
    		int k;
    		if((this.webAmount * 2) > 0)
			{
	    		if(par2 == 0) 
	    		{
	    			k = this.rand.nextInt((this.webAmount * 2));
	    		}
	    		else
	    		{
	    			k = this.rand.nextInt((this.webAmount * (2 + par2))); 
	    		}
			
    		
	    		//Drop string
		    	for (int l = 0; l < k; ++l)
		        {
		    		this.dropItem(j, 1);
		        }
			}
        }
    	//

        if (par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0))
        {
            this.dropItem(Item.spiderEye.itemID, 1);
        }
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb() {}

    @SideOnly(Side.CLIENT)

    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount()
    {
        return 1.0F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean isPotionApplicable(PotionEffect par1PotionEffect)
    {
        return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

    /**
     * Initialize this creature.
     */
    public void initCreature()
    {
        //if (this.worldObj.rand.nextInt(100) == 0)
    	//Spiders now have an increased chance of having other monsters mounted on top of them
    	//By default, only one skeleton can mount. Now, many monsters can mount.
    	//A spider mounted on top of a spider appears to be a conjoined spider. This is not a bug, it's a feature.
    	
    	int chance = 10;
        if (this.worldObj.rand.nextInt(chance) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityskeleton.initCreature();
            this.worldObj.spawnEntityInWorld(entityskeleton);
            entityskeleton.mountEntity(this);
            
            if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySkeleton entityskeleton1 = new EntitySkeleton(this.worldObj);
                entityskeleton1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityskeleton1.initCreature();
                this.worldObj.spawnEntityInWorld(entityskeleton1);
                entityskeleton1.mountEntity(entityskeleton);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityCreeper entitycreeper = new EntityCreeper(this.worldObj);
                entitycreeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entitycreeper.initCreature();
                this.worldObj.spawnEntityInWorld(entitycreeper);
                entitycreeper.mountEntity(entityskeleton);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityZombie entityzombie = new EntityZombie(this.worldObj);
                entityzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityzombie.initCreature();
                this.worldObj.spawnEntityInWorld(entityzombie);
                entityzombie.mountEntity(entityskeleton);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySpider entityspider = new EntitySpider(this.worldObj);
                entityspider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityspider.initCreature();
                this.worldObj.spawnEntityInWorld(entityspider);
                entityspider.mountEntity(entityskeleton);
            }
        }
        
        //
        else if (this.worldObj.rand.nextInt(chance) == 0)
        {
            EntityCreeper entitycreeper = new EntityCreeper(this.worldObj);
            entitycreeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entitycreeper.initCreature();
            this.worldObj.spawnEntityInWorld(entitycreeper);
            entitycreeper.mountEntity(this);
            
            if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
                entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityskeleton.initCreature();
                this.worldObj.spawnEntityInWorld(entityskeleton);
                entityskeleton.mountEntity(entitycreeper);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityCreeper entitycreeper1 = new EntityCreeper(this.worldObj);
                entitycreeper1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entitycreeper1.initCreature();
                this.worldObj.spawnEntityInWorld(entitycreeper1);
                entitycreeper1.mountEntity(entitycreeper);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityZombie entityzombie = new EntityZombie(this.worldObj);
                entityzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityzombie.initCreature();
                this.worldObj.spawnEntityInWorld(entityzombie);
                entityzombie.mountEntity(entitycreeper);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySpider entityspider = new EntitySpider(this.worldObj);
                entityspider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityspider.initCreature();
                this.worldObj.spawnEntityInWorld(entityspider);
                entityspider.mountEntity(entitycreeper);
            }
        }
        
        else if (this.worldObj.rand.nextInt(chance) == 0)
        {
            EntityZombie entityzombie = new EntityZombie(this.worldObj);
            entityzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityzombie.initCreature();
            this.worldObj.spawnEntityInWorld(entityzombie);
            entityzombie.mountEntity(this);
            
            if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
                entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityskeleton.initCreature();
                this.worldObj.spawnEntityInWorld(entityskeleton);
                entityskeleton.mountEntity(entityzombie);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityCreeper entitycreeper = new EntityCreeper(this.worldObj);
                entitycreeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entitycreeper.initCreature();
                this.worldObj.spawnEntityInWorld(entitycreeper);
                entitycreeper.mountEntity(entityzombie);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityZombie entityzombie1 = new EntityZombie(this.worldObj);
                entityzombie1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityzombie1.initCreature();
                this.worldObj.spawnEntityInWorld(entityzombie1);
                entityzombie1.mountEntity(entityzombie);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySpider entityspider = new EntitySpider(this.worldObj);
                entityspider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityspider.initCreature();
                this.worldObj.spawnEntityInWorld(entityspider);
                entityspider.mountEntity(entityzombie);
            }
        }

        else if (this.worldObj.rand.nextInt(chance) == 0)
        {
            EntitySpider entityspider = new EntitySpider(this.worldObj);
            entityspider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityspider.initCreature();
            this.worldObj.spawnEntityInWorld(entityspider);
            entityspider.mountEntity(this);
            
            if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
                entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityskeleton.initCreature();
                this.worldObj.spawnEntityInWorld(entityskeleton);
                entityskeleton.mountEntity(entityspider);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityCreeper entitycreeper = new EntityCreeper(this.worldObj);
                entitycreeper.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entitycreeper.initCreature();
                this.worldObj.spawnEntityInWorld(entitycreeper);
                entitycreeper.mountEntity(entityspider);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntityZombie entityzombie = new EntityZombie(this.worldObj);
                entityzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityzombie.initCreature();
                this.worldObj.spawnEntityInWorld(entityzombie);
                entityzombie.mountEntity(entityspider);
            }
            else if (this.worldObj.rand.nextInt(chance) == 0)
            {
                EntitySpider entityspider1 = new EntitySpider(this.worldObj);
                entityspider1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityspider1.initCreature();
                this.worldObj.spawnEntityInWorld(entityspider1);
                entityspider1.mountEntity(entityspider);
                
            }
        }

        //
    }
    
}
