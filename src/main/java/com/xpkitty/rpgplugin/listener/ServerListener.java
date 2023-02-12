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
