// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class OldWand {
    public void OpenUI(Rpg rpg, Player player, List<String> spells) {

        if(spells.size()==0) {
            OldWand.sendNoSpellsMessage(player);
        } else {

            Inventory ui = Bukkit.createInventory(null, 27, ChatColor.BLACK + "Spell List");

            for(String spell : spells) {
                for(SpellList spellList : SpellList.values()) {
                    if(spellList.name().equalsIgnoreCase(spell)) {

                        ItemStack is = new ItemStack(spellList.getIcon());
                        ItemMeta im = is.getItemMeta();
                        im.setDisplayName(ChatColor.WHITE + spellList.getDisplay());
                        im.setLore(Arrays.asList(ChatColor.GRAY + spellList.getDescription()));
                        im.setLocalizedName(spellList.name());
                        im.addItemFlags(ItemFlag.HIDE_DYE);
                        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        is.setItemMeta(im);
                        ui.addItem(is);


                    }
                }
            }

            /*ItemStack is = new ItemStack(Material.BARRIER);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(ChatColor.WHITE + "No Spell");
            im.setLore(Arrays.asList(ChatColor.GRAY + "Resets Spell"));
            im.setLocalizedName("SPELL_NONE");
            im.addItemFlags(ItemFlag.HIDE_DYE);
            im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            is.setItemMeta(im);
            ui.addItem(is);*/

            player.openInventory(ui);
        }
    }

    public static void sendNoSpellsMessage(Player player) {
        player.sendMessage(ChatColor.RED + "You do not know any spells");
    }

    public void giveWand(Player player) {

        player.sendMessage("Wand");
        ItemStack staff = new ItemStack(Material.STICK);
        ItemMeta staffMeta = staff.getItemMeta();
        staffMeta.setDisplayName(ChatColor.GOLD + "Wand");
        staffMeta.setLocalizedName("WAND");
        staff.setItemMeta(staffMeta);
        player.getInventory().addItem(staff);


    }
}
