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

package com.xpkitty.rpgplugin.manager.player_model;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerPart {

    public PlayerPart() {

    }



    public static List<Entity> getPlayerParts(Player player) {
        ArrayList<Entity> parts = new ArrayList<>();
        for (Entity passanger : player.getPassengers()) {
            if(passanger.getScoreboardTags().contains("playherPart")) {
                parts.add(passanger);
            }
        }
        return parts;
    }

    public static void createPlayerPart(Player player) {
        // remove existing passanger
        ArmorStand part = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        player.addPassenger(part);
        part.addScoreboardTag("playerPart");
    }

    public static void clearPlayerPart(Player player) {
        for(Entity passanger : player.getPassengers()) {
        if(passanger.getScoreboardTags().contains("playerPart"))
            player.removePassenger(passanger);
            passanger.remove();
        }
    }

    @Deprecated
    public void disablePlugin() {
        //remove on disable
        //this is deprecated and will be removed


    }


}
