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
package com.xpkitty.rpgplugin.manager;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperienceManager {


    public static void updateXpBar(Rpg rpg, Player player) {
        if(player.hasPermission("rpgpl.debug")) {
        }
        player.sendMessage("DEBUG: updating xp bar");

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();

        int exp = yamlConfiguration.getInt("experience");
        int level = yamlConfiguration.getInt("level");

        setXpBar(rpg, player, exp, level);
    }

    public static void setXpBar(Rpg rpg, Player player, int exp, int level) {
        ArrayList<String> offList = new ArrayList<>();/*
        offList.add("world");
        offList.add("world_nether");
        offList.add("world_the_end");*/
        offList.add("CityLife");
        /*offList.add("more");*/

        if(!offList.contains(player.getWorld().getName())) {
            List<?> list = rpg.getConfigManager().getConfiguration().getList("experience-per-level");
            player.setLevel(level);
            float levelXp = Float.parseFloat(String.valueOf(list.get(level)));
            float lastLevelXp = Float.parseFloat(String.valueOf(list.get(level-1)));
            StringManager stringManager = new StringManager();
            if(player.hasPermission("rpgpl.debug")) {
                StringManager.debugMessage(player,"Level Xp " + levelXp +  " last Level Xp " + lastLevelXp + " Xp " + exp);
            }
            if(level < list.size()) {
                float percentage = (exp-lastLevelXp)/(levelXp-lastLevelXp);
                if(player.hasPermission("rpgpl.debug")) {
                    player.sendMessage(ChatColor.RED + "[DEBUG] " + "exp-lastLevelXp/levelXp-lastLevelXp=" + percentage);
                }
                player.setExp(percentage);
            } else {
                player.setExp(0.0f);
            }
        } else {
            player.sendMessage("Cannot update XP Bar");
        }
    }

    public void addXp(Player player, Rpg rpg, int amount) {

        PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
        YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
        File file = playerDataManager.getYamlFile();


        int level = yamlConfiguration.getInt("level");
        int xp = yamlConfiguration.getInt("experience") + amount;

        int newLevel = rpg.getPlayerLevelManager().calculateLevel(xp);
        int toNext;

        if(newLevel <= rpg.getConfigManager().getConfiguration().getList("experience-per-level").size()) {
            toNext = (int) rpg.getConfigManager().getConfiguration().getList("experience-per-level").get(newLevel);
            toNext -= xp;
        } else {
            toNext = -1;
        }

        yamlConfiguration.set("experience",xp);
        yamlConfiguration.set("level", newLevel);
        try {
            yamlConfiguration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String msg = "§b§l+" + amount + " EXP";
        player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(msg));
        setXpBar(rpg, player, xp, newLevel);

        if(newLevel > level) {
            rpg.getPlayerLevelManager().levelUp(player, level, newLevel, toNext);
        }
        ExperienceManager.updateXpBar(rpg,player);

        player.sendMessage("XP: " + xp);
        player.sendMessage("LEVEL: " + level);

    }

}
