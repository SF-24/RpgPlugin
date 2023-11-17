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
