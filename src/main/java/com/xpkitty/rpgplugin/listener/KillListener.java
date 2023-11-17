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
package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.StringManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.util.ArrayList;

public class KillListener implements Listener {
    Rpg rpg;

    public KillListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        e.setDroppedExp(0);

        if(e.getEntity().getKiller() instanceof Player) {
            //TODO: add xp and item reward methods
            Player player = e.getEntity().getKiller();
            YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
            File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

            ArrayList<String> offList = new ArrayList<>();
            offList.add("world");
            offList.add("world_nether");
            offList.add("world_the_end");
            offList.add("CityLife");
            offList.add("test");

            if(!offList.contains(player.getWorld().getName())) {
                if(e.getEntity().getType().equals(EntityType.HUSK) && !yamlConfiguration.getBoolean("ACHIEVEMENTS.KILL_GOBLIN")) {
                    StringManager stringManager = new StringManager();
                    stringManager.sendAchievement(rpg, player,"KILL_GOBLIN",ChatColor.AQUA + "Goblin Slayer");

                } else if((e.getEntity().getType().equals(EntityType.ZOMBIE) || e.getEntity().getType().equals(EntityType.SKELETON) || e.getEntity().getType().equals(EntityType.STRAY) || e.getEntity().getType().equals(EntityType.DROWNED) || e.getEntity().getType().equals(EntityType.ZOGLIN) || e.getEntity().getType().equals(EntityType.ZOMBIE_VILLAGER)) && !yamlConfiguration.getBoolean("ACHIEVEMENTS`.KILL_UNDEAD")) {
                    StringManager stringManager = new StringManager();
                    stringManager.sendAchievement(rpg, player,"KILL_UNDEAD",ChatColor.AQUA + "Bane of Undead");
                }
            }
        }
    }
}
