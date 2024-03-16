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
import com.xpkitty.rpgplugin.manager.player_model.PlayerPart;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerModelCommand implements CommandExecutor {
    public PlayerModelCommand(Rpg rpg) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args==null || args.length==0) {
                player.sendMessage(ChatColor.RED + "USAGE: /player_model <add|remove>");
            } else {
                if(args.length==1) {
                    if(args[0].equalsIgnoreCase("add")) {
                        PlayerPart.clearPlayerPart(player);
                        // TODO: add parts

                    } else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rem")) {
                        PlayerPart.clearPlayerPart(player);
                        // TODO: add part removing

                    } else {
                        player.sendMessage(ChatColor.RED + "USAGE: /player_model <add|remove>");
                    }
                }
            }
        } else {
            System.out.println("command can only be run by player");
        }
        return false;
    }
}
