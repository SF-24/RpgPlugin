package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.ExperienceManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class DataListener implements Listener {

    Rpg rpg;
    public DataListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onExp(PlayerExpChangeEvent e) {
        Player player = e.getPlayer();
        ExperienceManager experienceManager = new ExperienceManager();
        experienceManager.addXp(player, rpg, e.getAmount());
    }


}
