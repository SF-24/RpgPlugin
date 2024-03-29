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
package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.AlohomoraSpell;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AlohomoraRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    AlohomoraRunnable(Rpg rpg, Player player, Vector dir) {
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

        for(int i = 0; i<15; i++) {

            t += 0.25;
            double x = dir.getX() * t;
            double y = dir.getY() * t + 1.5;
            double z = dir.getZ() * t;
            loc.add(x, y, z);

            //loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 50, 0, 0, 0, 0);
            if (!loc.getBlock().getType().equals(Material.AIR)) {
                if(loc.getBlock().getType().toString().contains("DOOR")) {
                    BlockState blockState = loc.getBlock().getState();
                    Openable openable = (Openable) blockState.getBlockData();
                    if (!openable.isOpen()) {
                        openable.setOpen(true);
                        blockState.setBlockData(openable);
                        blockState.update();
                    }
                }
                cancel();
            }
            loc.subtract(x, y, z);
            if (t > 15) {
                this.cancel();
            }
        }
    }
}
