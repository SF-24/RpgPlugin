// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerLevelManager {

    Rpg rpg;
    public PlayerLevelManager(Rpg rpg) {
        this.rpg = rpg;
    }

    public int getPlayerLevel(Player player) {
        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        int level = yamlConfiguration.getInt("level");

        return level;
    }

    public int calculateLevel(int exp) {
        Configuration config = rpg.getConfigManager().getConfiguration();

        List<Integer> list = new ArrayList<>();
        list = config.getIntegerList("experience-per-level");

        Integer level = 1;

        for(Integer element : list) {
            if(exp >= element) {
                level = list.indexOf(element) + 1;
            }
        }
        return level;
    }

    public void levelUp(Player player, int oldLevel, int level, int xpToNext) {
        ArrayList<String> MessageList = new ArrayList<>();

        PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
        YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
        File file = playerDataManager.getYamlFile();

        int skillPointsGet = 0;
        int leveledAmount = 0;
        ArrayList<Integer> levelsList= new ArrayList<>();

        int skillPoints = yamlConfiguration.getInt("skill_points");
        if(level - 1 == oldLevel) {
            skillPointsGet = 1;
            leveledAmount = 1;
            levelsList.add(level);
            levelsList.add(level-1);
        } else if(level-2 == oldLevel) {
            skillPointsGet = 2;
            leveledAmount = 2;
            levelsList.add(level);
            levelsList.add(level-1);
            levelsList.add(level-2);
        }  else if(level-3 == oldLevel) {
            skillPointsGet = 2;
            leveledAmount = 2;
            levelsList.add(level);
            levelsList.add(level-1);
            levelsList.add(level-2);
            levelsList.add(level-3);
        }  else if(level-4 == oldLevel) {
            skillPointsGet = 2;
            leveledAmount = 2;
            levelsList.add(level);
            levelsList.add(level-1);
            levelsList.add(level-2);
            levelsList.add(level-3);
            levelsList.add(level-4);
        }  else if(level-5 == oldLevel) {
            skillPointsGet = 2;
            leveledAmount = 2;
            levelsList.add(level);
            levelsList.add(level-1);
            levelsList.add(level-2);
            levelsList.add(level-3);
            levelsList.add(level-4);
            levelsList.add(level-5);
        }  else if(level-6 == oldLevel) {
            skillPointsGet = 2;
            leveledAmount = 2;
            levelsList.add(level);
            levelsList.add(level-1);
            levelsList.add(level-2);
            levelsList.add(level-3);
            levelsList.add(level-4);
            levelsList.add(level-5);
            levelsList.add(level-6);
        } else {
            int cap = rpg.getConfigManager().getConfiguration().getList("experience-per-level").size();
            for(int i = 0; i >= cap ; i++) {
                if(oldLevel + i == level) {
                    skillPointsGet = i;
                    leveledAmount = i;
                    levelsList.add(level);
                    for(int j = level; j==i; j--) {
                        levelsList.add(j);
                    }
                }
            }
        }

        player.sendMessage(ChatColor.RED + "[DEBUG] " + "Your levels: " + levelsList);

        skillPoints += skillPointsGet;
        yamlConfiguration.set("level", level);
        yamlConfiguration.set("skill_points", skillPoints);
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringManager stringManager = new StringManager();
        MessageList.add(ChatColor.GREEN + "_____________________________________________________");
        MessageList.add(ChatColor.WHITE.toString() + ChatColor.BOLD + "Level Up");
        MessageList.add("");
        MessageList.add(ChatColor.YELLOW + "You are now level " + ChatColor.GOLD.toString() + ChatColor.GOLD + level + ChatColor.YELLOW + "!");
        if(skillPointsGet == 1) {
            MessageList.add(ChatColor.YELLOW + "You have earned a skill point.");
        } else if (skillPointsGet > 1) {
            MessageList.add(ChatColor.YELLOW + "You have earned " + skillPointsGet + " skill points.");
        }
        MessageList.add("");

        int amountXp = xpToNext;

        if(xpToNext > 0) {
            int newLevelSoon = level + 1;
            MessageList.add(ChatColor.YELLOW + "You need " + ChatColor.GOLD + amountXp + " xp " + ChatColor.YELLOW + "to reach level " + ChatColor.GOLD + newLevelSoon);
        } else {
            MessageList.add(ChatColor.YELLOW + "You have reached the level cap!");
        }
        MessageList.add(ChatColor.GREEN + "_____________________________________________________");

        for(String val : MessageList) {
            StringManager.sendCenteredMessage(player, val);
        }

        try {
            rpg.getDatabaseManager().updatePlayerData(player);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
