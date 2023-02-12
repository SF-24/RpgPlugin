package com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LeapAbility {

    public LeapAbility(Player player, Rpg rpg) {

        Vector dir = player.getLocation().getDirection().normalize();

        dir = dir.add(dir);

        if(dir.getY()>0.9) {
            dir.setY(0.9);
        }

        if(dir.getY()<0.5) {
            dir.setY(0.5);
        }

        player.setSneaking(false);
        player.setVelocity(dir);

        Bukkit.getScheduler().runTaskLater(rpg,() -> {
            player.setSneaking(true);
            player.setSneaking(false);
        },5);
    }

}
