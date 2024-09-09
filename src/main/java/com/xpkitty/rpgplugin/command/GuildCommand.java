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
import com.xpkitty.rpgplugin.manager.data.guild_data.GuildDataReader;
import com.xpkitty.rpgplugin.manager.player_groups.guilds.GuildManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GuildCommand implements CommandExecutor {
    Rpg rpg;

    public GuildCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            System.out.println("only a player can execute this command");
        } else {
            Player player = (Player) sender;

            // ARGS = 0
            if((args.length ==0)) {
                sendCommandSyntax(player);

                // ARGS = 1
            } else if(args.length==1) {

                // SHOW LIST OF GUILDS
                if(args[0].equalsIgnoreCase("list")) {
                    ArrayList<Integer> guildIds = GuildManager.getGuildList();


                    if(guildIds.equals(new ArrayList<>())) {
                        player.sendMessage(ChatColor.RED + "No guilds currently exist");
                    } else if(guildIds.size() < 1) {
                        player.sendMessage(ChatColor.RED + "No guilds currently exist");
                    } else {
                        player.sendMessage(ChatColor.GOLD + "Listing Guilds");

                        for(Integer element : guildIds) {
                            GuildDataReader guildDataReader = new GuildDataReader(rpg,element);
                            String name = guildDataReader.loadData(rpg,element).getGuildName();

                            player.sendMessage( "[id: " + element + "] " + name);
                        }
                    }

                    // OPEN GUILD MENU
                } else if(args[0].equalsIgnoreCase("menu")) {
                    player.sendMessage("Guild menu is on the //todo list");
                    // TODO guild menu


                    // SHOW GUILD INFO
                } else if(args[0].equalsIgnoreCase("info")) {
                    player.sendMessage("Guild info is on the //todo list");
                    // TODO guild info

                } else if(args[0].equalsIgnoreCase("join")) {
                    player.sendMessage(ChatColor.RED + "syntax: /guild join <guildId>");
                }
                // ARGS >1
            } else {
                if(args[0].equalsIgnoreCase("create")) {
                    if(GuildManager.getPlayerGuildCount(player) < rpg.getGuildConfig().getMaxGuildsPerPlayer()) {
                        String guildName = "";
                        int guildNameSize = 0;

                        for(String arg : args) {
                            if(!arg.equalsIgnoreCase("create")) {
                                if(guildNameSize == 0) {
                                    guildName = arg;
                                } else {
                                    guildName = guildName + " " + arg;
                                }
                                guildNameSize++;
                            }
                        }

                        guildName = guildName.substring(0, 1).toUpperCase() + guildName.substring(1);

                        GuildManager.createGuild(player,guildName);
                    } else {
                        player.sendMessage(ChatColor.RED + "You have reached the guild limit." + ChatColor.WHITE + " You can only be in " + ChatColor.GOLD + rpg.getGuildConfig().getMaxGuildsPerPlayer() + ChatColor.WHITE + " guilds at once)");
                    }

                } else if(args[0].equalsIgnoreCase("join")) {
                    int id = -1;
                    try {
                        id = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "The specified guild id is not a number.");
                        player.sendMessage(ChatColor.RED + "syntax: /guild join <guildId>");
                        return false;
                    }

                    if(args.length>2) {
                        player.sendMessage(ChatColor.RED + "You have specified too many parameters");
                        player.sendMessage(ChatColor.RED + "syntax: /guild join <guildId>");
                    }

                    // check if id does not exist
                    if(!GuildManager.getGuildList().contains(id)) {
                        player.sendMessage(ChatColor.RED + "The specified guild id does not exist.");
                        player.sendMessage(ChatColor.RED+"Use "+ChatColor.GOLD+"\"/guild list\""+ChatColor.RED+" to view a list of guilds.");
                        return false;
                    }

                    if(GuildManager.isPlayerInGuild(id,player.getUniqueId())) {
                        player.sendMessage(ChatColor.RED + "You are already a member of this guild");
                        return false;
                    }

                    if(GuildManager.getPlayerGuildCount(player) >= rpg.getGuildConfig().getMaxGuildsPerPlayer()) {
                        player.sendMessage(ChatColor.RED + "You have reached the guild limit." + ChatColor.WHITE + " You can only be in " + ChatColor.GOLD + rpg.getGuildConfig().getMaxGuildsPerPlayer() + ChatColor.WHITE + " guilds at once)");
                        return false;
                    }

                    GuildManager.sendJoinRequest(id,player);

                    player.sendMessage("COMING SOON!");
                    //TODO guild joining

                    /*
                    StringBuilder guildName = new StringBuilder("na/e");

                    for(String arg : args) {
                        if(!arg.equals("join")) {
                            if(guildName.toString().equalsIgnoreCase("na/e")) {
                                guildName = new StringBuilder(arg);
                            } else {
                                guildName.append(" ").append(arg);
                            }
                        }
                    }

                    if(!guildName.toString().equalsIgnoreCase("na/e")) {
                        Integer id = GuildManager.getGuildIdByName(rpg, String.valueOf(guildName));

                        if(id!=-1) {
                            player.sendMessage("Successfully joined guild " + guildName);

                            Guild guildInstance = rpg.getGuildManager().getGuildInstance(id);
                            guildInstance.addPlayer(player);

                        } else {
                            player.sendMessage(ChatColor.RED + "Guild does not exist");
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "Please specify guild name");
                    }*/


                    //GUILD INFO ABOUT CERTAIN GUILD BY ID
                } else if(args[0].equalsIgnoreCase("info")) {
                    player.sendMessage("COMING SOON!");

                    int id = -1;

                    try {
                        id = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Usage: /guild info <guild id>");
                    }

                     /*
                     if(id>-1) {
                         int playerGuildCount = GuildManager.getPlayerGuildCount(rpg, player);
                         int maxGuildCount = rpg.getGuildConfig().getMaxGuildsPerPlayer();

                         if (playerGuildCount < maxGuildCount) {
                             if (!GuildManager.getGuildList(rpg).contains(id)) {
                                 player.sendMessage(ChatColor.RED + "Guild does not exist");
                                 player.sendMessage(ChatColor.RED + "Please specify guild ID");
                             } else {
                                 Guild guildInstance = rpg.getGuildManager().getGuildInstance(id);

                                 HashMap<String, String> players = guildInstance.getPlayerNames();

                                 String guildName = GuildManager.getGuildName(rpg, id);

                                 player.sendMessage(ChatColor.AQUA + "Displaying list of players for guild " + guildName);

                                 for (String element : players.keySet()) {
                                     player.sendMessage(" " + element + " [" + players.get(element) + "]");
                                 }
                             }
                         } else {
                             player.sendMessage(ChatColor.RED + "You can only be in " + maxGuildCount + " guilds at once");
                         }
                     }*/
                }
            }
        }


        return false;
    }

    private void sendCommandSyntax(Player player) {
        player.sendMessage(ChatColor.RED + "Incorrect command syntax");
    }
}
