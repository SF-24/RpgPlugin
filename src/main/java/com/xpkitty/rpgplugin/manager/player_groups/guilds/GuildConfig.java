// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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

        if(!modifyConfig.contains("max-guilds-per-player")) {
            modifyConfig.createSection("max-guilds-per-player");
            modifyConfig.set("max-guilds-per-player",2);
        }

        try { modifyConfig.save(configFile); }
        catch (IOException e) { e.printStackTrace(); }
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
