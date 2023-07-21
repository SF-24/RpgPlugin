// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.listener.ConnectListener;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.data.player_data.SkillList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSkillCommand implements CommandExecutor {

    Rpg rpg;

    public SetSkillCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            String errorMessage = ChatColor.RED + "Command syntax error!\nList of skills:\n";
            for(SkillList skillList : SkillList.values()) {
                errorMessage = errorMessage + "- " + skillList.name() + "\n";
            }


            if(args.length==2) {
                for(SkillList value : SkillList.values()) {
                    System.out.println("Doing test for value " + value.name());
                    if(args[0].equalsIgnoreCase(value.name()) && Integer.parseInt(args[1]) > -1) {
                        String valueN = value.name().toLowerCase();

                        ConnectListener connectListener = rpg.getConnectListener();
                        PlayerDataManager playerDataManager = connectListener.getPlayerDataInstance(player);
                        YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
                        File file = playerDataManager.getYamlFile();

                        if(yamlConfiguration.contains("skills." + valueN + ".level")) {
                            yamlConfiguration.set("skills." + valueN + ".level", Integer.parseInt(args[1]));
                        } else {
                            yamlConfiguration.createSection("skills." + valueN + ".level");
                            yamlConfiguration.createSection("skills." + valueN + ".experience");
                            yamlConfiguration.set("skills." + valueN + ".experience", Integer.parseInt(args[1]));
                            yamlConfiguration.set("skills." + valueN + ".experience", 0);
                        }
                        try {
                            yamlConfiguration.save(file);
                            player.sendMessage(ChatColor.WHITE + "Your " + ChatColor.AQUA + valueN + ChatColor.WHITE + " skill has been set to " + ChatColor.GREEN + args[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                    }
                }
            } else {
                player.sendMessage(errorMessage);
            }
        }

        return false;
    }
}
