/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
package com.xpkitty.rpgplugin.manager.spells.spells_old.SacredFlameSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.EntityManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SacredFlameRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    SacredFlameRunnable(Rpg rpg, Player player) {
        this.player = player;
        this.rpg = rpg;
    }

    public void start() {

        runTaskTimer(rpg,0,1/4);
    }


    @Override
    public void run() {
        //player.sendMessage("DEBUG");
        if(t==0) {
            loc = player.getLocation();
            dir = player.getLocation().getDirection().normalize();
        }

        for(int i=0; i<4; i++) {


            t += 0.5;
            double x = dir.getX() * t;
            double y = dir.getY() * t + 1.5;
            double z = dir.getZ() * t;
            loc.add(x, y, z);

            if (!loc.getBlock().getType().equals(Material.AIR)) {
                cancel();
                loc.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 0, 0.2, 0, 0, 5);
                player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 1.0f);
            } else {
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.75) {
                        if (!e.equals(player)) {
                            if (e instanceof LivingEntity) {
                                //cancel();
                                LivingEntity livingEntity = (LivingEntity) e;
                                livingEntity.setFireTicks(200);

                                if (EntityManager.isUndead(livingEntity.getType())) {
                                    livingEntity.damage(10f);
                                }
                            }
                        }
                    }
                }
            }


            loc.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0, 0, 5);
            loc.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0, 0, 5);


            loc.subtract(x, y, z);
            if (t > 40) {
                this.cancel();
            }
        }
    }
}
