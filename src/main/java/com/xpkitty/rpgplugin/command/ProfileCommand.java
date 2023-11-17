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
package com.xpkitty.rpgplugin.command;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {

    Rpg rpg;

    public ProfileCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args[0].equalsIgnoreCase("set") && args.length==2) {

                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).setActiveProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Activated profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " does not exist");
                }


            } else if(args[0].equalsIgnoreCase("delete") && args.length==2) {

                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).deleteProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Deleted profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " does not exist");
                }


            } else if(args[0].equalsIgnoreCase("create") && args.length == 2) {
                boolean exist = rpg.getConnectListener().getPlayerProfileInstance(player).checkIfProfileExists(args[1]);
                if(!exist) {
                    rpg.getConnectListener().getPlayerProfileInstance(player).createProfile(args[1]);
                    player.sendMessage(ChatColor.WHITE + "Created profile " + ChatColor.AQUA + args[1]);
                } else {
                    player.sendMessage(ChatColor.RED + "Profile " + args[1] + " already exist");
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.BOLD + "Listing player profiles:");

                for(String key : rpg.getConnectListener().getPlayerProfileInstance(player).getModifyProfileYaml().getConfigurationSection("profiles").getKeys(false)) {

                    player.sendMessage("- " + key + " [" + rpg.getConnectListener().getPlayerProfileInstance(player).getModifyProfileYaml().getString("profiles." + key + ".name") + "]");
                }
            } else if(args[0].equalsIgnoreCase("current")) {

                player.sendMessage("Your active profile is " + ChatColor.AQUA + rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile());

            } else {
                player.sendMessage(ChatColor.RED + "ERROR! Incorrect command syntax");
            }
        } else {
            System.out.println("ERROR! You can only execute /profile command as a player");
        }

        return false;
    }
}
