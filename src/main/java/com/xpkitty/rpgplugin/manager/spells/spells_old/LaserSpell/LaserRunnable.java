// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spells_old.LaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LaserRunnable extends BukkitRunnable {
    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;
    float power;

    public LaserRunnable(Rpg rpg, Player player, float power) {
        this.player = player;
        this.rpg = rpg;
        this.power = power;
    }

    public void start() {

        runTaskTimer(rpg,0,1/2);
        //player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 2.0f);
    }

    @Override
    public void run() {
        if(t==0) {
            player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 2.0f);
        }
        //player.sendMessage("DEBUG");

        if(t==0) {
            loc = player.getLocation();
            dir = player.getLocation().getDirection().normalize();
        }

        for(int i = 0; i<10; i++) {
            t += 0.5;
            double x = dir.getX() * t;
            double y = dir.getY() * t + 1.5;
            double z = dir.getZ() * t;
            loc.add(x,y,z);

            if(!loc.getBlock().getType().equals(Material.AIR)) {
                cancel();
                //loc.getWorld().spawnParticle(Particle.CRIT,loc,0,0.2,0,0,5);
                //player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 1.0f);
            } else {
                for(Entity e : loc.getChunk().getEntities()) {
                    if(e.getLocation().distance(loc) < 1.75) {
                        if(!e.equals(player)) {
                            if(e instanceof LivingEntity) {
                                cancel();
                                LivingEntity livingEntity = (LivingEntity) e;

                                livingEntity.damage(power);
                                //player.sendMessage(String.valueOf(power));
                            }
                        }
                    }
                }
            }

            loc.getWorld().spawnParticle(Particle.REDSTONE,loc,0,0.2,0,0,5,new Particle.DustOptions(Color.PURPLE,2));
            loc.getWorld().spawnParticle(Particle.REDSTONE,loc,0,0.2,0,0,5,new Particle.DustOptions(Color.PURPLE,2));
            //loc.getWorld().spawnParticle(Particle.DRAGON_BREATH,loc,0,0,0,0,50);
            //loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,loc,0,0,0,0,75);


            loc.subtract(x,y,z);
            if(t>40) {
                this.cancel();
            }
        }


    }
}
