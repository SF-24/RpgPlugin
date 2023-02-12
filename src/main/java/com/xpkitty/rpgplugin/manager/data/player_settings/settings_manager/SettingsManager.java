package com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_data.SettingsReader;
import org.bukkit.entity.Player;

public class SettingsManager {
    Rpg rpg;
    public SettingsManager(Rpg rpg) {
        this.rpg=rpg;
    }

    public void addPlayer(Player player) {
        SettingsReader.initialiseFile(rpg,player);
    }
}
