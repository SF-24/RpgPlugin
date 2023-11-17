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
package com.xpkitty.rpgplugin.manager.data.spell_data;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class MainSpellData {
    Rpg rpg;
    public MainSpellData(Rpg rpg) {
        this.rpg = rpg;
        initialize();
    }

    private File yamlFile;
    private YamlConfiguration modifyYaml;

    public void initialize() {
        String path = rpg.getDataFolder() + File.separator + "Data";
        yamlFile = new File(path, "Spells.yml");
        File dir = yamlFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!yamlFile.exists()) {
            try { yamlFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifyYaml = YamlConfiguration.loadConfiguration(yamlFile);

        if(!modifyYaml.contains("important")) {
            modifyYaml.createSection("important");
            modifyYaml.set("important","do NOT modify 'spell_num' value - it will destroy all guild data");
        }

        if(!modifyYaml.contains("spell_num")) {
            modifyYaml.createSection("spell_num");
            modifyYaml.set("spell_num",0);
        }

        if(!modifyYaml.contains("spells")) {
            modifyYaml.createSection("spells");
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
        System.out.println("[AnotherRpgPlugin] Saved Spell main data file");
    }

    public File getYamlFile() { return yamlFile; }
    public YamlConfiguration getModifyYaml() { modifyYaml = YamlConfiguration.loadConfiguration(yamlFile); return modifyYaml; }

    public int getSpellNum(boolean increaseSpellNum) {
        YamlConfiguration yamlConfiguration = getModifyYaml();

        int spellNum = yamlConfiguration.getInt("spell_num");

        if(increaseSpellNum) {
            int newSpellNum = spellNum+1;

            yamlConfiguration.set("spell_num", newSpellNum);
        }


        saveFile(yamlConfiguration,yamlFile);

        return spellNum;
    }

    public String getSpellIncantation(int id) {
        if(getModifyYaml().contains("spells." + id)) {
            return getModifyYaml().getString("spells." + id + ".incantation");
        } else {
            for(HpSpellsList hpSpellsList : HpSpellsList.values()) {
                if(hpSpellsList.getNumericId() == id) {
                    return hpSpellsList.getDisplay();
                }
            }
        }

        return String.valueOf(-2);
    }

    public String getSpellStringId(int id) {
        String v = getSpellIncantation(id);
        v = v.toLowerCase(Locale.ROOT);
        v = v.replace(" ", "_");

        return v;
    }
}
