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
package com.xpkitty.rpgplugin.manager.player_groups.guilds;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GuildConfig {
    Rpg rpg;
    public GuildConfig(Rpg rpg) {
        this.rpg = rpg;
        initialize();
    }

    private File configFile;
    private YamlConfiguration modifyConfig;

    public void initialize() {
        String path = rpg.getDataFolder() + File.separator + "Data";
        configFile = new File(path, "guild_config.yml");
        File dir = configFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!configFile.exists()) {
            try { configFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifyConfig = YamlConfiguration.loadConfiguration(configFile);

        if(!modifyConfig.contains("current-guild-id")) {
            modifyConfig.createSection("current-guild-id");
            modifyConfig.set("current-guild-id",0);
        }

        if(!modifyConfig.contains("max-guilds-per-player")) {
            modifyConfig.createSection("max-guilds-per-player");
            modifyConfig.set("max-guilds-per-player",2);
        }

        try { modifyConfig.save(configFile); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public int getGuildNum(boolean increaseGuildNum) {
        YamlConfiguration yamlConfiguration = getModifyConfig();

        int guildNum = yamlConfiguration.getInt("current-guild-id");

        if(increaseGuildNum) {
            int newGuildNum = guildNum+1;

            yamlConfiguration.set("current-guild-id", newGuildNum);
        }


        saveFile(yamlConfiguration,getConfigFile());

        return guildNum;
    }


    public void saveFile(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[AnotherRpgPlugin] Saved Guild main config file");
    }

    public File getConfigFile() { return configFile; }
    public YamlConfiguration getModifyConfig() { modifyConfig = YamlConfiguration.loadConfiguration(configFile); return modifyConfig; }

    public int getMaxGuildsPerPlayer() {
        if(modifyConfig.contains("max-guilds-per-player")) {
            return modifyConfig.getInt("max-guilds-per-player");
        }
        return 2;
    }
}
