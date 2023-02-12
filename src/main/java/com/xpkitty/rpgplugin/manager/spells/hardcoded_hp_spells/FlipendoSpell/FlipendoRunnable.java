package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.FlipendoSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FlipendoRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    FlipendoRunnable(Rpg rpg, Player player, Vector dir) {
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
            player.getWorld().playSound(player.getLocation(),Sound.ENTITY_DRAGON_FIREBALL_EXPLODE,2.0f,2.0f);
        }

        for(int i = 0; i<7; i++) {

            t += 0.5;
            double x = dir.getX() * t;
            double y = dir.getY() * t + 1.5;
            double z = dir.getZ() * t;
            loc.add(x, y, z);

            if (!loc.getBlock().getType().equals(Material.AIR)) {
                cancel();
            } else {
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.75) {
                        if (!e.equals(player)) {
                            if (e instanceof LivingEntity) {
                                cancel();

                                dir=player.getLocation().getDirection().normalize();
                                dir.multiply(dir);
                                dir.multiply(1.35f);
                                dir=dir.setY(0.25);

                                e.setVelocity(dir);
                            }
                        }
                    }
                }
            }


            //loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, 0.2, 0, 0, 5, new Particle.DustOptions(Color.RED, 2));
            //loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, 0.2, 0, 0, 5, new Particle.DustOptions(Color.RED, 2));
            loc.subtract(x, y, z);
            if (t > 40) {
                this.cancel();
            }
        }
    }
}
