// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.shield;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_template.GenericShieldRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ShieldManager {

    HashMap<UUID, Shield> shields = new HashMap<>();

    public boolean playerHasShield(Player player) {
        return shields.containsKey(player.getUniqueId());
    }
    public boolean playerHasShield(UUID uuid) {
        return shields.containsKey(uuid);
    }

    public int getShieldHealth(Player player) {
        return shields.get(player.getUniqueId()).getHealth();
    }

    public int getShieldHealth(UUID uuid) {
        return shields.get(uuid).getHealth();
    }

    public void addShield(Rpg rpg, Player player, GenericShieldRunnable shieldRunnable, int timeInTicks, int health, int armour, ShieldType type, ShieldCategory category, SpellFunction function) {
        shields.put(player.getUniqueId(),new Shield(health,armour,type,category,function));

        player.sendMessage(ChatColor.GOLD+"You are now shielded");
        Bukkit.getScheduler().runTaskLater(rpg,() -> {
            shields.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GOLD+"You are no longer shielded");
            shieldRunnable.cancel();
        }, timeInTicks);
    }

    public void removeShield(Player player) {
        shields.remove(player.getUniqueId());
    }

    public void damageShield(Player player, int damage) {
        shields.get(player.getUniqueId()).damageShield(damage);
    }

    public Shield getShield(Player player) {
        return shields.get(player.getUniqueId());
    }
}
