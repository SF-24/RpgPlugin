package com.xpkitty.rpgplugin.command;

import com.google.common.cache.Cache;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_groups.parties.PartyManager;
import com.xpkitty.rpgplugin.manager.StringManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PartyCommand implements CommandExecutor {
    Rpg rpg;

    public PartyCommand(Rpg rpg) {
        this.rpg = rpg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            System.out.println("ERROR! You are not a player!");
            return false;
        } else {

            MiscPlayerManager miscPlayerManager = rpg.getMiscPlayerManager();
            Player player = (Player) sender;

            if(args[0].equalsIgnoreCase("tpall")) {
                String partyName = miscPlayerManager.getPlayerParty(player);
                PartyManager partyManager = miscPlayerManager.getPartyInstance(partyName);

                if(partyManager.getOwner().getUniqueId().equals(player.getUniqueId())) {

                    Cache<UUID, Long> teleportCooldown = rpg.getMiscPlayerManager().getTeleportCooldown();
                    
                    if(!teleportCooldown.asMap().containsKey(player.getUniqueId())) {
                        for(Player player1 : partyManager.getPlayers()) {
                            if(player1.getUniqueId() != partyManager.getOwner().getUniqueId()) {
                                Location location = partyManager.getOwner().getLocation();
                                player1.teleport(location);
                                player1.sendMessage("You have been teleported to party leader.");
                            }
                        }
                        partyManager.getOwner().sendMessage("All party members have been teleported to your location.");

                        rpg.getMiscPlayerManager().teleportCooldown.put(player.getUniqueId(),System.currentTimeMillis() + 1800000);
                    } else {
                        long distance = teleportCooldown.asMap().get(player.getUniqueId()) - System.currentTimeMillis();
                        player.sendMessage(ChatColor.RED + "You must wait " + TimeUnit.MILLISECONDS.toMinutes(distance) + " minutes to use this command");

                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not party leader!");
                }
            } else if(args[0].equalsIgnoreCase("invite")) {
                boolean breaks = false;
                for(Player player2 : Bukkit.getServer().getOnlinePlayers()) {
                    if(args[1].equalsIgnoreCase(player2.getName())) {
                        if(miscPlayerManager.isPlayerInParty(player)) {
                            String partyName = miscPlayerManager.getPlayerParty(player);
                            PartyManager partyInstance = miscPlayerManager.getPartyInstance(partyName);

                            Player owner = partyInstance.getOwner();
                            if(owner.getUniqueId().equals(player.getUniqueId())) {
                                if(!partyInstance.getPlayers().contains(player2)) {
                                    player2.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.AQUA + " has sent you a party request.");
                                    player2.sendMessage(ChatColor.AQUA + "Type: " + ChatColor.GOLD + "/party accept " + ChatColor.AQUA + "to accept.");
                                    miscPlayerManager.invitePlayer(player2,player);
                                    player.sendMessage("Sent party request to " +player2.getName());
                                } else {
                                    player.sendMessage(ChatColor.RED + player2.getDisplayName() + " is already in your party");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "You are not the party leader.");
                            }
                        } else {
                            player2.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.AQUA + " has sent you a party request.");
                            player2.sendMessage(ChatColor.AQUA + "Type: " + ChatColor.GOLD + "/party accept " + player.getName() + " " + ChatColor.AQUA + "to accept.");
                            miscPlayerManager.invitePlayer(player2,player);
                            player.sendMessage("Sent party request to " +player2.getName());
                            breaks = true;

                        }

                        breaks = true;
                    }
                }
                if(!breaks) {
                    player.sendMessage(ChatColor.RED + "That player is not online!");
                }


            } else if(args[0].equalsIgnoreCase("leave")){
                if(miscPlayerManager.isPlayerInParty(player)) {
                    for(Player playerVal : miscPlayerManager.getPartyInstance(miscPlayerManager.getPlayerParty(player)).getPlayers()) {
                        playerVal.sendMessage(ChatColor.BLUE + player.getDisplayName() + ChatColor.WHITE + " left the party");
                    }

                    miscPlayerManager.removeFromParty(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party");
                }
            } else if(args[0].equalsIgnoreCase("join")) {

            } else if(args[0].equalsIgnoreCase("accept")) {
                if(!miscPlayerManager.isPlayerInParty(player)) {
                    Player player2 = Bukkit.getPlayer(args[1]);
                    if(miscPlayerManager.isInvitedBy(player,player2)) {
                        if(miscPlayerManager.isPlayerInParty(Bukkit.getPlayer(args[1]))) {
                            Player inviter = Bukkit.getPlayer(args[1]);
                            String name = miscPlayerManager.getPlayerParty(inviter);
                            if(miscPlayerManager.getPartyInstance(name).getOwner().equals(inviter.getUniqueId())) {
                                miscPlayerManager.getPartyInstance(name).addPlayer(player);
                                miscPlayerManager.removeInvite(player,player2);
                            } else {
                                player.sendMessage(inviter.getDisplayName() + " is no longer party leader of party " + miscPlayerManager.getPartyInstance(name).getName());
                            }
                        } else {
                            miscPlayerManager.removeInvite(player,player2);
                            Player owner = player2;
                            ArrayList<Player> players = new ArrayList<>();
                            players.add(player);
                            players.add(player2);
                            player.sendMessage("You joined " + owner.getDisplayName() + "'s party.");
                            miscPlayerManager.newParty(owner.getDisplayName() + "'s party" , players, owner);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have any party invites from that player");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are already in a party");
                }
            }/* else if(args[0].equalsIgnoreCase("rename")){
                String newName = "";

                for(int i = 1; i < (args.length); i++) {
                    if(i>1) {
                        newName = newName + " ";
                    }
                    newName = newName + args[i];
                }

                player.sendMessage(ChatColor.AQUA + "Party renamed to [" + ChatColor.GOLD + newName + "]");
                miscPlayerManager.getPartyInstance(miscPlayerManager.getPlayerParty(player)).rename(newName);

            }*/ else if(args[0].equalsIgnoreCase("info")) {
                if(miscPlayerManager.isPlayerInParty(player)) {

                    String partyName = miscPlayerManager.getPlayerParty(player);
                    Player owner = miscPlayerManager.getPartyInstance(partyName).getOwner();
                    String partyDisplayName = miscPlayerManager.getPartyInstance(partyName).getName();

                    StringManager.sendCenteredMessage(player, ChatColor.GOLD + "[PARTY INFORMATION]");
                    player.sendMessage("Party name: " + ChatColor.GREEN + partyName);
                    player.sendMessage("Party display name: " + ChatColor.RED + partyDisplayName);

                    if(player.getUniqueId().equals(owner.getUniqueId())) {
                        player.sendMessage("Party owner: " + ChatColor.AQUA + owner.getDisplayName() + ChatColor.WHITE + " (You are the owner)");
                    } else {
                        player.sendMessage("Party owner: " + ChatColor.AQUA + owner.getDisplayName());
                    }

                    player.sendMessage(ChatColor.GOLD + "List of players:");
                    for(Player currPlayer : miscPlayerManager.getPartyInstance(partyName).getPlayers()) {
                        if(player.getUniqueId().equals(currPlayer.getUniqueId())) {
                            player.sendMessage("- " + currPlayer.getDisplayName() + " (you)");
                        } else {
                            player.sendMessage("- " + currPlayer.getDisplayName());
                        }
                    }
                    player.sendMessage("");
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in a party.");
                }


            } else {
                player.sendMessage(ChatColor.RED + "Incorrect arguments!");

            }
        }


        return false;
    }
}
