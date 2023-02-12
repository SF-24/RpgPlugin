package com.xpkitty.rpgplugin.manager.player_class.class_list;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.player_class.BaseClass;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

public class WizardClass extends BaseClass {
    public WizardClass(Rpg rpg, UUID uuid, ClassList type) {
        super(rpg, uuid, type);
    }

    @Override
    public void onStart(Rpg rpg, Player player, ClassList type) {
        MiscPlayerManager.learnSpell(player,rpg, SpellList.FIREBALL,false);
        MiscPlayerManager.learnSpell(player,rpg, SpellList.STUPEFY,false);

        ItemStack is = new ItemStack(Material.STICK);
        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Wizard's Staff");
        itemMeta.setCustomModelData(1);
        itemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "A wizard's staff"));
        itemMeta.setLocalizedName("WAND");
        is.setItemMeta(itemMeta);
        player.getInventory().addItem(is);
    }
}
