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
package com.xpkitty.rpgplugin.manager.spells.spells_old.LaserSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LaserSpell {
    Rpg rpg;
    UUID uuid;
    SpellList type;
    float power;

    public LaserSpell(Rpg rpg, SpellList type, UUID uuid,float power) {
        this.rpg = rpg;
        this.uuid = uuid;
        this.type = type;
        onStart(Bukkit.getPlayer(uuid),power);
        this.power=power;
    }

    public void onStart(Player player,float power) {
        //player.sendMessage("power: " + power);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 5.0f, 2.0f);
        LaserRunnable task = new LaserRunnable(rpg,player,power);
        task.start();
    }
}
