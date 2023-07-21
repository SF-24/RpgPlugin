// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
