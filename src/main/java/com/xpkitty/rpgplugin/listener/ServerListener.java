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
package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.InventoryView;

public class ServerListener implements Listener {
    Rpg rpg;

    public ServerListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent e) {
        for(Player player : rpg.getServer().getOnlinePlayers()) {
            MiscPlayerManager.saveLocationToFile(player, rpg);
            if(!player.getOpenInventory().getType().equals(InventoryType.CHEST)) {
                MiscPlayerManager.savePlayerInventory(player, rpg);
            }
        }
        System.out.println();
        System.out.println("[RpgPlugin] AUTO-SAVING PLAYER INVENTORY AND LOCATION");
        System.out.println();
    }
}
