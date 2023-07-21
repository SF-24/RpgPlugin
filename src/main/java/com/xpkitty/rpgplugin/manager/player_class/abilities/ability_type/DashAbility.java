// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities.ability_type;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DashAbility {

    public DashAbility(Player player, Rpg rpg) {

        Vector dir = player.getLocation().getDirection().normalize();

        dir = dir.add(dir);
        dir = dir.add(player.getLocation().getDirection().normalize());
        dir = dir.add(player.getLocation().getDirection().normalize());

        if(dir.getY()>0.25) {
            dir.setY(0.25);
        }

        player.setVelocity(dir);
    }

}
