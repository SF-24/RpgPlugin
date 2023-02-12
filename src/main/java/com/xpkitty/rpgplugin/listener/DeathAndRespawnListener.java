package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.ExperienceManager;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.SpellHotbarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathAndRespawnListener implements Listener {
    Rpg rpg;

    public DeathAndRespawnListener(Rpg rpg) {
        this.rpg=rpg;
    }

    @EventHandler
    void PlayerRespawnEvent(PlayerRespawnEvent e) {
        ExperienceManager.updateXpBar(rpg, e.getPlayer());
    }

    @EventHandler
    void PlayerDeathEvent(PlayerDeathEvent e) {
        rpg.getSpellHotbarManager().deadctivateSpellHotbar(e.getEntity());
    }
}
