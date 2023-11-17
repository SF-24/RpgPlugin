/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
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
