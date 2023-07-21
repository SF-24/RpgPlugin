// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
