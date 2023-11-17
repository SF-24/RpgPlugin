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
package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.WingardiumLeviosaSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WingardiumLeviosaRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    WingardiumLeviosaRunnable(Rpg rpg, Player player,Vector dir) {
        this.player = player;
        this.rpg = rpg;
        this.dir = dir;
    }

    public void start() {

        runTaskTimer(rpg,0,1/4);
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
                cancel();
                loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 50, 0, 0, 0, 1);

                player.getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 1.0f);
            } else {
                for (Entity e : loc.getChunk().getEntities()) {
                    if (e.getLocation().distance(loc) < 1.75) {
                        if (e instanceof LivingEntity && !e.equals(player)) {
                            cancel();
                            LivingEntity livingEntity = (LivingEntity) e;
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 30, 4, false, false, true));

                        }
                    }
                }
            }


            loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 50, 0, 0, 0, 0);
            loc.subtract(x, y, z);
            if (t > 40) {
                this.cancel();
            }
        }
    }
}
