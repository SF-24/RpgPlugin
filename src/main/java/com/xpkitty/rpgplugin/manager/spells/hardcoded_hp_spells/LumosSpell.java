// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.HpSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.UUID;

public class LumosSpell extends HpSpell {
    public LumosSpell(Rpg rpg, SpellList type, UUID uuid, Vector dir) {
        super(rpg, type, uuid, dir);

        onStart(Bukkit.getPlayer(uuid),dir);
    }

    @Override
    public void onStart(Player player, Vector dir) {
        if(Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getLocalizedName().contains("HP_WAND")) {
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            ItemStack item = new ItemStack(Material.LAVA_BUCKET);
            item.setItemMeta(itemMeta);
            player.getInventory().setItemInMainHand(item);
        }
    }
}
