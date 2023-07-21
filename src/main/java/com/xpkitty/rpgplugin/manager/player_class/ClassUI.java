// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ClassUI {

    public ClassUI(Player player, Rpg rpg) {

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();


    }
}
