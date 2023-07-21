// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.player_data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PlayerSpellFile {

    Rpg rpg;
    Player player;
    YamlConfiguration modifyYamlFile;
    File yamlFile;

    public PlayerSpellFile(Player player,Rpg rpg) {
        this.player = player;
        this.rpg = rpg;

        initiateFiles();
    }

    private void initiateFiles() {
        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "PlayerData" + File.separator + rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile() + File.separator + "SpellData";

        File yamlFile = new File(path, player.getUniqueId() + ".yml");
        YamlConfiguration modifyYamlFile = YamlConfiguration.loadConfiguration(yamlFile);

        this.modifyYamlFile = modifyYamlFile;
        this.yamlFile = yamlFile;

        System.out.println("Yaml path for player " + player.getName() + ": " +path);

        if(!modifyYamlFile.contains("active_spell")) {
            modifyYamlFile.createSection("active_spell");
            modifyYamlFile.set("active_spell","default");
        }

        if(!modifyYamlFile.contains("hotbars")) {
            modifyYamlFile.createSection("hotbars");
            modifyYamlFile.set("hotbars",3);
        }

        if(!modifyYamlFile.contains("spell_hotbar_num")) {
            modifyYamlFile.createSection("spell_hotbar_num");
            modifyYamlFile.set("spell_hotbar_num",1);
        }


        if(!modifyYamlFile.contains("magic_stat_scores.energy.base")) {
            modifyYamlFile.createSection("magic_stat_scores.energy.base");
            modifyYamlFile.set("magic_stat_scores.energy.base",20);
        }

        if(!modifyYamlFile.contains("magic_stat_scores.energy.regen_speed")) {
            modifyYamlFile.createSection("magic_stat_scores.energy.regen_speed");
            modifyYamlFile.set("magic_stat_scores.energy.regen_speed",1);
        }

        if(!modifyYamlFile.contains("magic_stat_scores.energy.current")) {
            modifyYamlFile.createSection("magic_stat_scores.energy.current");
            modifyYamlFile.set("magic_stat_scores.energy.current",20);
        }

        if(!modifyYamlFile.contains("spells")) {
            modifyYamlFile.createSection("spells");

        }



        if(!yamlFile.exists()) {
            try {
                System.out.println("Creating yaml file for " + player.getName());

                File yamlDir = yamlFile.getParentFile();
                if(!yamlDir.exists()) {
                    yamlDir.mkdir();
                }

                yamlFile.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }


        try { this.modifyYamlFile.save(yamlFile); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}
    }

    public YamlConfiguration getModifySpellFile() { modifyYamlFile=YamlConfiguration.loadConfiguration(yamlFile); return modifyYamlFile; }
    public File getSpellFile() { return yamlFile; }

    public int getActiveSpell() {
        if(Objects.equals(getModifySpellFile().get("active_spell"), "default")) {
            return -2;
        }
        return getModifySpellFile().getInt("active_spell");
    }

    public int getActiveSpellHotbar() { return getModifySpellFile().getInt("spell_hotbar_num"); }

    public void setActiveSpell(int id) { YamlConfiguration yaml = getModifySpellFile(); yaml.set("active_spell", id); try {
        yaml.save(getSpellFile());
    } catch (IOException e) {
        e.printStackTrace();
    } }

    public void setSlot(int spellId, int slotId, int hotbarId) {
        YamlConfiguration yaml = getModifySpellFile();

        for(String element : yaml.getConfigurationSection("spells").getKeys(false)) {
            String path = "spells." + element + ".";
            int slot = yaml.getInt(path + "slot");
            int bar = yaml.getInt(path + "bar");

            if(slot == slotId && bar == hotbarId) {
                yaml.set(path+"slot",0);
                yaml.set(path+"bar",0);
            }
        }

        String id = rpg.getMainSpellData().getSpellStringId(spellId);

        String path = "spells." + id + ".";

        yaml.set(path+"slot",slotId);
        yaml.set(path+"bar", hotbarId);
        try {
            yaml.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSpellHotBar(int id) {
        for(String element : getModifySpellFile().getConfigurationSection("spells").getKeys(false)) {
            String path = "spells." + element + ".";


            if(rpg.getMainSpellData().getSpellStringId(id).equalsIgnoreCase(element)) {
                return getModifySpellFile().getInt(path + "bar");
            }

        }
        return -1;
    }

    public int getSpellSlot(int id) {
        for(String element : getModifySpellFile().getConfigurationSection("spells").getKeys(false)) {
            String path = "spells." + element + ".";

            if(rpg.getMainSpellData().getSpellStringId(id).equalsIgnoreCase(element)) {
                return getModifySpellFile().getInt(path + "slot");
            }
        }

        return -1;
    }

    public void setHotBarNum(int hotBarNum) {
        YamlConfiguration yamlConfiguration = getModifySpellFile();

        yamlConfiguration.set("spell_hotbar_num",hotBarNum);
        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHotbarCount() { return getModifySpellFile().getInt("hotbars"); }
    public int getEditHotbar() { return getModifySpellFile().getInt("edit_spell_hotbar_num"); }

    public void setCurrentEditHotbar(int num) {
        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("edit_spell_hotbar_num", num);
        try {
            yamlConfiguration.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOneToEditHotBar() {
        int count = getHotbarCount();
        int hotbar = getEditHotbar();

        hotbar++;

        if(hotbar>count) {
            hotbar = 1;
        }

        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("edit_spell_hotbar_num", hotbar);

        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subtractOneFromEditHotBar() {
        int count = getHotbarCount();
        int hotbar = getEditHotbar();

        hotbar--;

        if(hotbar<1) {
            hotbar = count;
        }

        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("edit_spell_hotbar_num", hotbar);

        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHotBarCount(int count) {
        if (count > 0) {
            YamlConfiguration yamlConfiguration = getModifySpellFile();
            yamlConfiguration.set("hotbars", count);

            try {
                yamlConfiguration.save(getSpellFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getBaseEnergy() {
        return getModifySpellFile().getInt("magic_stat_scores.energy.base");
    }

    public int getEnergyRegen() {
        return getModifySpellFile().getInt("magic_stat_scores.energy.regen_speed");
    }

    public int getCurrentEnergy() {
        return getModifySpellFile().getInt("magic_stat_scores.energy.current");
    }

    public void setBaseEnergy(int count) {
        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("magic_stat_scores.energy.base",count);
        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEnergyRegen(int count) {
        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("magic_stat_scores.energy.regen_speed",count);
        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentEnergy(int count) {
        YamlConfiguration yamlConfiguration = getModifySpellFile();
        yamlConfiguration.set("magic_stat_scores.energy.current",count);
        try {
            yamlConfiguration.save(getSpellFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
