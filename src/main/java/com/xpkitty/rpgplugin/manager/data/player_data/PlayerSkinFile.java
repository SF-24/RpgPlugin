// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.player_data;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.skin.SkinManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerSkinFile {

    Rpg rpg;
    Player player;
    YamlConfiguration modifyYamlFile;
    File yamlFile;

    public PlayerSkinFile(Player player,Rpg rpg) {
        this.player = player;
        this.rpg = rpg;

        initiateFiles();
    }

    private void initiateFiles() {
        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "PlayerData" + File.separator + rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile() + File.separator + "SkinData";

        File yamlFile = new File(path, player.getUniqueId() + ".yml");
        YamlConfiguration modifyYamlFile = YamlConfiguration.loadConfiguration(yamlFile);

        this.modifyYamlFile = modifyYamlFile;
        this.yamlFile = yamlFile;

        System.out.println("Yaml path for player " + player.getName() + ": " +path);

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

        if(!modifyYamlFile.contains("skins")) {
            modifyYamlFile.createSection("skins");
        }
        if(!modifyYamlFile.contains("active-skin")) {
            modifyYamlFile.createSection("active-skin");
            modifyYamlFile.set("active-skin","null");
        }
        if(!modifyYamlFile.contains("skins.default")) {
            modifyYamlFile.createSection("skins.default");
            modifyYamlFile.createSection("skins.default.texture");
            modifyYamlFile.createSection("skins.default.signature");
            modifyYamlFile.set("skins.default.texture","null");
            modifyYamlFile.set("skins.default.signature","null");
        }
        createSkinIfNotExists("robes_hufflepuff");
        createSkinIfNotExists("robes_gryffindor");
        createSkinIfNotExists("robes_ravenclaw");
        createSkinIfNotExists("robes_slytherin");
        createSkinIfNotExists("robes_green");
        createSkinIfNotExists("robes_blue");
        createSkinIfNotExists("robes_basilisk_hide");


        try { this.modifyYamlFile.save(yamlFile); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}
    }

    public YamlConfiguration getModifySkinFile() { modifyYamlFile=YamlConfiguration.loadConfiguration(yamlFile); return modifyYamlFile; }
    public File getSkinFile() { return yamlFile; }


    public void createSkinIfNotExists(String skinName) {
        if(!modifyYamlFile.contains("skins." + skinName)) {
            modifyYamlFile.createSection("skins."+skinName);
            modifyYamlFile.createSection("skins." + skinName+ ".texture");
            modifyYamlFile.createSection("skins."+ skinName+ ".signature");
            modifyYamlFile.set("skins."+skinName+".texture","null");
            modifyYamlFile.set("skins."+skinName+".signature","null");
        }
    }

    public void createSkin(String skinName, String textureString, String signature) {
        modifyYamlFile = getModifySkinFile();

        if(!modifyYamlFile.contains("skins." + skinName)) {
            modifyYamlFile.createSection("skins." + skinName + ".texture");
            modifyYamlFile.createSection("skins." + skinName + ".signature");


            System.out.println("[DEBUG] 'Creating default profile' section in yml file for " + player.getName());
        }
        modifyYamlFile.set("skins." + skinName + ".signature", signature);
        modifyYamlFile.set("skins." + skinName + ".texture", textureString);

        try {
            modifyYamlFile.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> listSkins() {
        return new ArrayList<>(getModifySkinFile().getConfigurationSection("skins").getKeys(false));
    }

    public boolean activateSkin(String skin) {
        modifyYamlFile = getModifySkinFile();

        if(modifyYamlFile.contains("skins")) {
            if(modifyYamlFile.contains("skins." + skin)) {
                String textureValue = modifyYamlFile.getString("skins." + skin + ".texture");
                String signature = modifyYamlFile.getString("skins." + skin + ".signature");

                if(textureValue == null || signature == null) {
                    return false;
                }

                if(textureValue.equalsIgnoreCase("null") || signature.equalsIgnoreCase("null")) {
                    return false;
                } else {
//                    SkinManager.changeSkin(player,textureValue,signature);
//                    SkinManager.updateSkin(rpg,player);
                    modifyYamlFile.set("active-skin",skin);
                    try {
                        modifyYamlFile.save(getSkinFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }
        }



        return false;
    }

    public boolean verifySkin(String skin) {
        modifyYamlFile = getModifySkinFile();

        if(modifyYamlFile.contains("skins." + skin)) {
            String textureKey = modifyYamlFile.getString("skins." + skin + ".texture");
            String signature = modifyYamlFile.getString("skins." + skin + ".signature");

            if(signature!=null && textureKey != null) {
                if(!(signature.equalsIgnoreCase("null") && textureKey.equalsIgnoreCase("null"))) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getActiveSkin() {
        return getModifySkinFile().getString("active-skin");
    }
}
