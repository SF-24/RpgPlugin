package com.xpkitty.rpgplugin.manager.spells.shield;

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

    public void addShield(Player player, int health, int armour, ShieldType type, ShieldCategory category) {
        shields.put(player.getUniqueId(),new Shield(health,armour,type,category));
    }

    public void removeShield(Player player) {
        shields.remove(player.getUniqueId());
    }

}
