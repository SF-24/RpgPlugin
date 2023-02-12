package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.Colloportus;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ColloportusRunnable extends BukkitRunnable {

    Location loc;
    Vector dir;
    Player player;
    Rpg rpg;
    double t = 0;

    ColloportusRunnable(Rpg rpg, Player player, Vector dir) {
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

            if (!loc.getBlock().getType().equals(Material.AIR)) {
                if(loc.getBlock().getType().toString().contains("DOOR")) {
                    BlockData blockState = loc.getBlock().getBlockData();
                    if(blockState instanceof Door) {
                        ((Door) blockState).setOpen(false);
                        loc.getBlock().setBlockData(blockState);
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
