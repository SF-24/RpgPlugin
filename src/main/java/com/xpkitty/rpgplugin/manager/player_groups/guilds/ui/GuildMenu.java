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
package com.xpkitty.rpgplugin.manager.player_groups.guilds.ui;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuildMenu {

    public static void openGuildMenu(Rpg rpg, Player player) {

        Inventory ui = Bukkit.createInventory(null, 9, ChatColor.BLACK + "");

        ItemStack guiTop = new ItemStack(Material.PEONY);
        ItemStack guiBottom = new ItemStack(Material.PEONY);

        ItemMeta guiTopMeta = guiTop.getItemMeta();
        ItemMeta guiBottomMeta = guiBottom.getItemMeta();

        guiTopMeta.setDisplayName("");
        guiBottomMeta.setDisplayName("");


    }


}
