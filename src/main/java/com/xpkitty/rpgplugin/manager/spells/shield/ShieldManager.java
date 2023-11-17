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
package com.xpkitty.rpgplugin.manager.spells.shield;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_template.GenericShieldRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ShieldManager {

    HashMap<UUID, Shield> shields = new HashMap<>();

    public boolean playerHasShield(Player player) {
        return shields.containsKey(player.getUniqueId());
    }
    public boolean playerHasShield(UUID uuid) {
        return shields.containsKey(uuid);
    }

    public int getShieldHealth(Player player) {
        return shields.get(player.getUniqueId()).getHealth();
    }

    public int getShieldHealth(UUID uuid) {
        return shields.get(uuid).getHealth();
    }

    public void addShield(Rpg rpg, Player player, GenericShieldRunnable shieldRunnable, int timeInTicks, int health, int armour, ShieldType type, ShieldCategory category, SpellFunction function) {
        shields.put(player.getUniqueId(),new Shield(health,armour,type,category,function));

        player.sendMessage(ChatColor.GOLD+"You are now shielded");
        Bukkit.getScheduler().runTaskLater(rpg,() -> {
            shields.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD+"You are no longer shielded");
            shieldRunnable.cancel();
        }, timeInTicks);
    }

    public void removeShield(Player player) {
        shields.remove(player.getUniqueId());
    }

    public void damageShield(Player player, int damage) {
        shields.get(player.getUniqueId()).damageShield(damage);
    }

    public Shield getShield(Player player) {
        return shields.get(player.getUniqueId());
    }
}
