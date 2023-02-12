package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ClassUI {

    public ClassUI(Player player, Rpg rpg) {

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();


    }
}
