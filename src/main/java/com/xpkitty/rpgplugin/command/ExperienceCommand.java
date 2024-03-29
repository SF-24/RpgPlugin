/*
 *     Copyright (C) 2024 Sebastian Frynas
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
package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.ExperienceManager;
import com.xpkitty.rpgplugin.manager.player_groups.parties.PartyManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ExperienceCommand implements CommandExecutor {

    Rpg rpg;

    public ExperienceCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player ) sender;

            PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
            YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
            File file;

            if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
                int level =rpg.getPlayerLevelManager().calculateLevel(yamlConfiguration.getInt("experience"));
                yamlConfiguration.set("level", level);

                player.sendMessage("Your experience overview:");
                player.sendMessage("LEVEL: " + level);
                player.sendMessage("EXP: " + yamlConfiguration.getInt("experience"));
            } else if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
                int setExp = Integer.parseInt(args[1]);

                ExperienceManager.addXp(player, setExp);
            } else if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
                Player player2 = Bukkit.getPlayer(args[2]);

                int setExp = Integer.parseInt(args[1]);

                ExperienceManager.addXp(player2, setExp);

            } else if(args.length == 3 && args[0].equalsIgnoreCase("give_party_per_player")) {
                //TODO: CHECK LINE 57
                Player player2 = Bukkit.getPlayer(args[2]);

                if(rpg.getMiscPlayerManager().isPlayerInParty(player2)) {

                    String partyName = rpg.getMiscPlayerManager().getPlayerParty(player2);
                    PartyManager partyInstance = rpg.getMiscPlayerManager().getPartyInstance(partyName);

                    for(Player partyPlayer : partyInstance.getPlayers()) {
                        int setExp = Integer.parseInt(args[1]);
                        ExperienceManager.addXp(partyPlayer, setExp);
                    }
                } else {
                    int setExp = Integer.parseInt(args[1]);
                    ExperienceManager.addXp(player2, setExp);
                }



            } else if(args.length == 2 && args[0].equalsIgnoreCase("give_party_per_player")) {

                if(rpg.getMiscPlayerManager().isPlayerInParty(player)) {

                    String partyName = rpg.getMiscPlayerManager().getPlayerParty(player);
                    PartyManager partyInstance = rpg.getMiscPlayerManager().getPartyInstance(partyName);

                    for(Player partyPlayer : partyInstance.getPlayers()) {
                        int setExp = Integer.parseInt(args[1]);
                        ExperienceManager.addXp(partyPlayer, setExp);
                    }
                } else {
                    int setExp = Integer.parseInt(args[1]);
                    ExperienceManager.addXp(player, setExp);
                }



            }  else if(args.length == 2 && args[0].equalsIgnoreCase("give_party_split")) {

                if(rpg.getMiscPlayerManager().isPlayerInParty(player)) {

                    String partyName = rpg.getMiscPlayerManager().getPlayerParty(player);
                    PartyManager partyInstance = rpg.getMiscPlayerManager().getPartyInstance(partyName);

                    int perPlayer = (int) (Float.parseFloat(args[1])/partyInstance.getPlayers().size());

                    for(Player partyPlayer : partyInstance.getPlayers()) {
                        ExperienceManager.addXp(partyPlayer, perPlayer);
                    }
                } else {
                    int setExp = Integer.parseInt(args[1]);
                    ExperienceManager.addXp(player, setExp);
                }



            } else if(args.length == 3 && args[0].equalsIgnoreCase("give_party_split")) {
                Player player2 = Bukkit.getPlayer(args[2]);

                if(rpg.getMiscPlayerManager().isPlayerInParty(player2)) {

                    String partyName = rpg.getMiscPlayerManager().getPlayerParty(player2);
                    PartyManager partyInstance = rpg.getMiscPlayerManager().getPartyInstance(partyName);

                    int perPlayer = (int) (Float.parseFloat(args[1])/partyInstance.getPlayers().size());

                    for(Player partyPlayer : partyInstance.getPlayers()) {
                        ExperienceManager.addXp(partyPlayer, perPlayer);
                    }
                } else {
                    int setExp = Integer.parseInt(args[1]);
                    ExperienceManager.addXp(player2, setExp);
                }



            } else if(args.length==2 && args[0].equalsIgnoreCase("set")) {
                yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
                file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();
                int xpToSet = Integer.parseInt(args[1]);
                yamlConfiguration.set("experience",xpToSet);
                int level = rpg.getPlayerLevelManager().calculateLevel(xpToSet);
                yamlConfiguration.set("level",level);
                try {
                    player.sendMessage("Set you EXP to " + ChatColor.AQUA + args[1]);
                    yamlConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    player.sendMessage(ChatColor.RED + "ERROR trying to save yamlConfiguration. IOException");
                }
                ExperienceManager.setXpBar(player, xpToSet,level);
            }
        } else {
            if(args.length == 1 && args[0].equalsIgnoreCase("list") && Bukkit.getServer().getOnlinePlayers().size()>0) {
                System.out.println();
                System.out.println("SHOWING LIST OF SERVER PLAYERS AND THEIR EXP AND LEVELS:");

                for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                    YamlConfiguration forThisPlayer;
                    PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
                    forThisPlayer = playerDataManager.getModifyYaml();
                    int exp = forThisPlayer.getInt("experience");
                    int level = rpg.getPlayerLevelManager().calculateLevel(exp);

                    System.out.println(player.getName()+ ":");
                    System.out.println("- LEVEL: " + level);
                    System.out.println("- EXP: " + exp);

                }
                System.out.println();
            } else if(args.length == 1 && args[0].equalsIgnoreCase("list") && Bukkit.getServer().getOnlinePlayers().size()<=0) {
                System.out.println("You cannot view levels and exp of all online players, because there are none.");
            } else {
                System.out.println("This command must be executed as a player or requires additional args.");
            }
        }

        return false;
    }
}
