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
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseList;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class HogwartsDataManager {

    Rpg rpg;
    public HogwartsDataManager(Rpg rpg) {
        this.rpg = rpg;
        initialize();
    }

    private File yamlFile;
    private YamlConfiguration modifyYaml;

    public void initialize() {
        String path = rpg.getDataFolder() + File.separator + "Data";
        yamlFile = new File(path, "Hogwarts.yml");
        File dir = yamlFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!yamlFile.exists()) {
            try { yamlFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifyYaml = YamlConfiguration.loadConfiguration(yamlFile);

        if(!modifyYaml.contains("house_points")) {
            modifyYaml.createSection("house_points");

            for(HogwartsHouseList house : HogwartsHouseList.values()) {
                modifyYaml.createSection("house_points." + house.name().toLowerCase(Locale.ROOT));
                modifyYaml.set("house_points." + house.name().toLowerCase(Locale.ROOT), 0);
            }
        }

        try { modifyYaml.save(yamlFile); }
        catch (IOException e) { e.printStackTrace(); }
    }


    public void saveFile(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[AnotherRpgPlugin] Saved Hogwarts data file");
    }

    public File getYamlFile() { return yamlFile; }
    public YamlConfiguration getModifyYaml() { modifyYaml = YamlConfiguration.loadConfiguration(yamlFile); return modifyYaml; }
}
