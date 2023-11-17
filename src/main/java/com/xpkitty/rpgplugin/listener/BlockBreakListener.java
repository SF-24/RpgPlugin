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
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockBreakListener implements Listener {
    public BlockBreakListener(Rpg rpg) {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setExpToDrop(0);

        if(e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("utumno")) {
            Block block = e.getBlock();
            Location location = e.getBlock().getLocation();

            if(e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
                if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
                    e.setDropItems(false);
                    e.setExpToDrop(5);
                    ItemStack item = new ItemStack(Material.NETHERITE_INGOT);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.WHITE + "Edhelvir");
                    itemMeta.setCustomModelData(1);
                    item.setItemMeta(itemMeta);

                    Item dropItem = location.getWorld().dropItem(location.clone().add(0.5, 1.2, 0.5), item);
                    dropItem.setVelocity(dropItem.getVelocity().zero());
                }
            }
        }
    }

}
