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
