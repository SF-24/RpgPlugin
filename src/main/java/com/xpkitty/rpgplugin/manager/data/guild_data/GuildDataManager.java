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

package com.xpkitty.rpgplugin.manager.data.guild_data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

// TODO: WARNING!!! MARKED FOR REMOVAL
// currently deprecated
@Deprecated
public class GuildDataManager {
    Rpg rpg;
    public GuildDataManager(Rpg rpg) {
        this.rpg = rpg;
        initialize();
    }

    private File yamlFile;
    private YamlConfiguration modifyYaml;

    @Deprecated
    public void initialize() {


        String path = rpg.getDataFolder() + File.separator + "Data";
        yamlFile = new File(path, "Guilds.yml");
        File dir = yamlFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!yamlFile.exists()) {
            try { yamlFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifyYaml = YamlConfiguration.loadConfiguration(yamlFile);

        if(!modifyYaml.contains("important")) {
            modifyYaml.createSection("important");
            modifyYaml.set("important","do NOT modify 'guild_num' value - it will destroy all guild data");
        }

        if(!modifyYaml.contains("guild_num")) {
            modifyYaml.createSection("guild_num");
            modifyYaml.set("guild_num",0);
        }

        if(!modifyYaml.contains("guilds")) {
            modifyYaml.createSection("guilds");
        }

        try { modifyYaml.save(yamlFile); }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Deprecated
    public void saveFile(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[AnotherRpgPlugin] Saved Guild main data file");
    }

    public File getYamlFile() { return yamlFile; }
    public YamlConfiguration getModifyYaml() { modifyYaml = YamlConfiguration.loadConfiguration(yamlFile); return modifyYaml; }

    public int getGuildNum(boolean increaseGuildNum) {
        YamlConfiguration yamlConfiguration = getModifyYaml();

        int guildNum = yamlConfiguration.getInt("guild_num");

        if(increaseGuildNum) {
            int newGuildNum = guildNum+1;

            yamlConfiguration.set("guild_num", newGuildNum);
        }


        saveFile(yamlConfiguration,yamlFile);

        return guildNum;
    }
}
