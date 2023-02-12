package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.IncendioSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class IncendioRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    IncendioRunnable(Rpg rpg, Player player, Vector dir) {
        this.player = player;
        this.rpg = rpg;
        this.dir = dir;
    }

    public void start() {

        runTaskTimer(rpg,0,1/2);
    }


    @Override
    public void run() {
        if(t==0) {
            loc = player.getLocation();
        }

        for(int i = 0; i<7; i++) {

            t += 0.5;
            double x = dir.getX() * t;
            double y = dir.getY() * t + 1.5;
            double z = dir.getZ() * t;
            loc.add(x, y, z);

            if (!loc.getBlock().getType().equals(Material.AIR)) {
                Location blockLoc = loc.getBlock().getLocation();
                blockLoc.setY(blockLoc.getY()+1);
                if(blockLoc.getBlock().getType().equals(Material.AIR)) {
                    blockLoc.getBlock().setType(Material.FIRE);
                }

                cancel();
                //loc.getWorld().spawnParticle(Particle.FLAME,loc,0,0.2,0,0,5);
                player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 1.0f);
            } else {
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.75) {
                        if (!e.equals(player)) {
                            if (e instanceof LivingEntity) {
                                cancel();

                                LivingEntity livingEntity = (LivingEntity) e;
                                livingEntity.setFireTicks(50);
                                livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
                            }
                        }
                    }
                }
            }


            //loc.getWorld().spawnP article(Particle.FLAME, loc, 0, 0.2, 0, 0, 5);
            loc.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0, 0, 5);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, 0.2, 0, 0, 5, new Particle.DustOptions(Color.ORANGE, 2));
            loc.subtract(x, y, z);
            if (t > 40) {
                this.cancel();
                player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 1.0f);
            }
        }
    }
}
