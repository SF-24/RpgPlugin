// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.food;

import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum FoodContainer {
    COFFEE_CUP(Material.GLASS_BOTTLE, 1, ChatColor.WHITE+"Coffee Cup"),
    GLASS_MUG(Material.GLASS_BOTTLE, 3, ChatColor.WHITE+"Glass Mug"),
    PAPER_CUP(Material.GLASS_BOTTLE, 2, ChatColor.WHITE+"Paper Cup"),
    CAN(Material.GLASS_BOTTLE, 4, ChatColor.WHITE+"Empty Can"),
    SILVER_BOWL(Material.BOWL, 5, ChatColor.WHITE+"Bowl"),
    BOWL(Material.BOWL, 0, null),
    GLASS_BOTTLE(Material.GLASS_BOTTLE, 0, null);

    ItemStack item;

    FoodContainer(Material material, int modelData, String displayName) {
        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            if (modelData > 0) {
                meta.setCustomModelData(modelData);
            }
            item.setItemMeta(meta);
        }
    }

    public ItemStack getItem() { return item; }
}
