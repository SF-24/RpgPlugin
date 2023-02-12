package com.xpkitty.rpgplugin.manager.spells.spells_old.ColorLaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LaserRunnableRed extends BukkitRunnable {
    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;
    float power;

    public LaserRunnableRed(Rpg rpg, Player player, float power) {
        this.player = player;
        this.rpg = rpg;
        this.power=power;
    }

    public void start() {

        runTaskTimer(rpg,0,1/12);

    }


    @Override
    public void run() {
        //player.sendMessage("DEBUG");
        if(t==0) {
            loc = player.getLocation();
            dir = player.getLocation().getDirection().normalize();
        }

        for(int i = 0; i<20; i++) {

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
                            }
                        }
                    }
                }

                //loc.getWorld().spawnParticle(Particle.DRAGON_BREATH,loc,0,0,0,0,50);
                loc.getWorld().spawnParticle(Particle.REDSTONE,loc,0,0.2,0,0,5,new Particle.DustOptions(Color.RED,2));
                loc.getWorld().spawnParticle(Particle.REDSTONE,loc,0,0.2,0,0,5,new Particle.DustOptions(Color.RED,2));



                loc.subtract(x,y,z);
                if(t>40) {
                    this.cancel();
                }

            }
        }


    }
}
