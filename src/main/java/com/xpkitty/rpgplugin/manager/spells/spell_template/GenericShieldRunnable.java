// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spell_template;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.shield.ShieldCategory;
import com.xpkitty.rpgplugin.manager.spells.shield.ShieldType;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.CustomParticle;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GenericShieldRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    float speed;
    double dist = 0;
    double t = 0;
    int flyDistance;
    public SpellFunction function;
    SpellTarget target;

    int spellStrength;

    String val1;
    String val2;
    String val3;

    CustomParticle particle;
    World world;
    //Particle particle =  Particle.DRAGON_BREATH;

    public GenericShieldRunnable(Rpg rpg, Player player, SpellFunction function, float speed, int flyDistance, SpellTarget targets, String val1, String val2, String val3, CustomParticle particle, int spellStrength) {
        this.player = player;
        this.rpg = rpg;
        this.dir = player.getLocation().getDirection().normalize();
        this.loc = player.getLocation();
        this.speed=speed;
        this.target=targets;
        this.flyDistance=flyDistance;
        this.function=function;
        this.val1=val1;
        this.val2=val2;
        this.val3=val3;
        this.world=player.getWorld();
        this.spellStrength=spellStrength;

        this.particle=particle;

        if(function.equals(SpellFunction.ENTITY_KNOCKBACK)) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,2.0f,2.0f);
        }

        int v1 = Integer.parseInt(val1);
        int v2 = Integer.parseInt(val2);
        int v3 = Integer.parseInt(val3);

        rpg.shieldManager.addShield(rpg,player, this,v1,v2,v3, ShieldType.SHIELD_360, ShieldCategory.STANDARD,function);

        start();
    }

    public void start() {
        runTaskTimer(rpg,0,1);
    }



    @Override
    public void run() {
        loc=player.getLocation();

        for(int i =0; i<4; i++) {
            t += Math.PI / 8; // or try pi/2
            double newX = Math.cos(t) + loc.getX();
            double newZ = Math.sin(t) + loc.getZ();
            double newY;

            //double newY = (t / 5) + loc.getY();
            for(double k=0; k<2; k+=0.5) {
                newY=k+loc.getY();
                if (particle.getType().equals(Particle.REDSTONE)) {
                    world.spawnParticle(particle.getType(), newX, newY, newZ, particle.getCount(), 0, 0, 0, 1, new Particle.DustOptions(particle.getColor(), particle.getSize()));
                } else {
                    world.spawnParticle(particle.getType(), newX, newY, newZ, particle.getCount(), 0, 0, 0, 1);
                }
            }


        }
        /*
         double radius = 0.9;

            double tAdding = 0.025;
        int amount = 0;
            for (double t = 0; t <= 2 * Math.PI * radius; t += tAdding) {
                amount += 1;
            }
        double raiseAmount = 2.0/amount;
        double y= loc.getY();
        for (double t = 0; t <= 2*Math.PI*radius; t += tAdding) {
            double x = (radius * Math.cos(t)) + loc.getX();
            double z = (loc.getZ() + radius * Math.sin(t));
            Location particleLocation = new Location(world, x, y, z);
            if(particle.getType().equals(Particle.REDSTONE)) {
                world.spawnParticle(particle.getType(), particleLocation, 0, 0, 0, 0, 1, new Particle.DustOptions(particle.getColor(), particle.getSize()));
            } else {
                world.spawnParticle(particle.getType(), particleLocation, 0, 0, 0, 0, 1);
            }
            y+=raiseAmount;
        }*/
    }
}
