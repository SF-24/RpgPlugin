// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.hogwarts;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HogwartsHouseMathManager {

    public static void addHousePoints(Rpg rpg, HogwartsHouseList house, Player pointAdder, int points) {
        int housePoints = HogwartsHouseManager.getHousePoints(rpg, house);
        int newPoints = points + housePoints;

        if(pointAdder!=null) {
            Player player = pointAdder;
            player.sendMessage("Added " + points + " points to " + house.getPrefix() + house.getDisplay());
            player.sendMessage("");
            player.sendMessage(house.getPrefix() + house.getDisplay() + ChatColor.RESET + " now has " + newPoints + " house points");
        }

        HogwartsHouseManager.setHousePoints(rpg, house, newPoints);
    }


    public static void addHousePoints(Rpg rpg, Player player, Player pointAdder, int points) {
        if(HogwartsHouseManager.getHogwartsHouse(rpg,player) != null) {
            addHousePoints(rpg, HogwartsHouseManager.getHogwartsHouse(rpg, player), pointAdder, points);
        } else {
            pointAdder.sendMessage(player.getDisplayName() + " has not been sorted into a Hogwarts house");
        }
    }




    public static void takeHousePoints(Rpg rpg, HogwartsHouseList house, Player taker, int points) {
        Player player = taker;
        int housePoints = HogwartsHouseManager.getHousePoints(rpg,house);

        if(housePoints > 0) {
            if(housePoints - points < 0) {
                player.sendMessage( "Could not take " + points + " from " + house.getPrefix() + house.getDisplay());
                player.sendMessage("Taken " + housePoints + " points instead");
                player.sendMessage("");
                player.sendMessage(house.getPrefix() + house.getDisplay() + ChatColor.RESET + " now has 0 house points");

                HogwartsHouseManager.setHousePoints(rpg, house, 0);
            } else {
                int newPoints = housePoints - points;

                player.sendMessage("Taken " + housePoints + " points from " + house.getDisplay() + house.getDisplay());
                player.sendMessage("");
                player.sendMessage(house.getPrefix() + house.getDisplay() + ChatColor.RESET + " now has " + newPoints + " house points");

                HogwartsHouseManager.setHousePoints(rpg, house, newPoints);
            }
        }
        player.sendMessage(ChatColor.RED + house.getDisplay() + " does not have any points to take");
    }


    public static void takeHousePoints(Rpg rpg, Player player, Player taker, int points) {
        if(HogwartsHouseManager.getHogwartsHouse(rpg,player) != null) {
            takeHousePoints(rpg, HogwartsHouseManager.getHogwartsHouse(rpg, player), taker, points);
        } else {
            taker.sendMessage(player.getDisplayName() + " has not been sorted into a Hogwarts house");
        }
    }
}
