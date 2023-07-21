// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private static FileConfiguration config;

    public void setupConfig(Rpg rpg) {
        ConfigManager.config = rpg.getConfig();
        rpg.saveDefaultConfig();
    }

    public String getConfigLineToString(String line) {
        if(config.contains(line)) {
            return config.get(line).toString();
        }
        return null;
    }

    public Configuration getConfiguration() { return config; }

    public void ReloadConfigs() {
        config = YamlConfiguration.loadConfiguration(new File("Config.yml"));
    }
}
