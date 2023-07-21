// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_groups.parties;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PartyManager {

    ArrayList<UUID> players = new ArrayList<>();
    Player owner;
    String name;

    public void create(ArrayList<Player> players, Player owner, String name) {
        this.owner = owner;
        this.name = name;

        for(Player player : players) {
            if(!this.players.contains(player.getUniqueId())) {
                this.players.add(player.getUniqueId());
            }

            if(player.getUniqueId().equals(owner.getUniqueId())) {
                player.sendMessage("Party created.");
            } else {
                player.sendMessage( ChatColor.AQUA + "You are now in a party");
            }
        }
    }

    public void addPlayer(Player player) {
        player.sendMessage(ChatColor.AQUA + "You are now in party " + ChatColor.GOLD + name);
        players.add(player.getUniqueId());
    }

    public void rename(String newName) {
        name = newName;
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players2 = new ArrayList<>();
        for(UUID uuid : players) {
            players2.add(Bukkit.getPlayer(uuid));
        }
        return players2;
    }

    public String getName() { return name; }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }
}
