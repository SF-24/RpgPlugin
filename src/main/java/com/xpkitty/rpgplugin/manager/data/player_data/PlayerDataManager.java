// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.player_data;

import com.google.gson.Gson;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.ResourcePack;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    Player player;
    Rpg rpg;

    File yamlConfiguration;
    YamlConfiguration modifyYamlConfiguration;

    public HashMap<UUID, File> fileMap = new HashMap<>();
    public HashMap<UUID, YamlConfiguration> modifyFileMap = new HashMap<>();
    public HashMap<UUID, ResourcePack> packs = new HashMap<>();
    private YamlConfiguration modifyComboYamlConfiguration;
    private File profileFileYaml;
    private YamlConfiguration modifyProfileYamlConfiguration;

    public PlayerDataManager(Player player, Rpg rpg, String profileString) {
        this.player = player;
        this.rpg = rpg;

        initiateYamlFiles(profileString);
        initiateComboFiles(profileString);
        initiateJsonFiles();
    }

    public ResourcePack getPlayerResourcePack(Player player) {
        if(packs.containsKey(player.getUniqueId())) {
            return packs.get(player.getUniqueId());
        }

        return ResourcePack.NULL;
    }

    public void setPlayerResourcePack(Player player, ResourcePack resourcePack) {
        player.setResourcePack(resourcePack.getUrl());
        packs.put(player.getUniqueId(),resourcePack);
        //player.sendMessage(ChatColor.YELLOW + "Setting resource pack to: " + resourcePack.name());
        //player.sendMessage("LINK: " + resourcePack.getUrl());
    }

    private void initiateYamlFiles(String saveString) {
        String path = rpg.getDataFolder() + File.separator + "Data" +  File.separator + "PlayerData" + File.separator + saveString;
        File fileYaml = new File(path, player.getUniqueId() + ".yml");
        YamlConfiguration modifyYamlFile = YamlConfiguration.loadConfiguration(fileYaml);

        this.modifyYamlConfiguration = modifyYamlFile;

        System.out.println("Yaml path for player " + player.getName() + ": " +path);

        if(!fileYaml.exists()) {
            try {
                System.out.println("Creating yaml file for " + player.getName());

                File yamlDir = fileYaml.getParentFile();
                if(!yamlDir.exists()) {
                    yamlDir.mkdir();
                }

                fileYaml.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }

        if(!modifyYamlFile.contains("player-name")) {
            modifyYamlFile.createSection("player-name");
            modifyYamlFile.set("player-name", player.getDisplayName());
            System.out.println("[DEBUG] 'Creating player-name' section in yml file for " + player.getName());
        }
        if(!modifyYamlFile.contains("skill_points")) {
            modifyYamlFile.createSection("skill_points");
            modifyYamlFile.set("skill_points", rpg.getConfig().getInt("default-skill-points"));
            System.out.println("[DEBUG] 'Creating skill_points' section in yml file for " + player.getName());
        }

        if(!modifyYamlFile.contains("abilities")) {
            modifyYamlFile.createSection("abilities");
            modifyYamlFile.set("abilities",new ArrayList<String>());
        }


        try { modifyYamlFile.save(fileYaml); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}

        for(PlayerDataValues values : PlayerDataValues.values()) {
            System.out.println("[DEBUG] Looping through PlayerDataValues for " + player.getName() + "'s yml file. Configuration section name: " + values.name().toLowerCase());

            String name = values.name();
            String dataTypeString = values.getDataType();
            String data = values.getDefValue();
            String yamlPath = values.getPath();
            String typeValue = values.getType();

            boolean setVal = false;

            String location;
            if(!yamlPath.equals("none")) {
                if(!modifyYamlFile.contains(yamlPath)) {
                    modifyYamlFile.createSection(yamlPath);
                }
                if(!modifyYamlFile.contains(yamlPath + "." + name)) {
                    modifyYamlFile.createSection(yamlPath + "." + name);
                    setVal = true;
                }
                location = yamlPath +"."+ name;
            } else {
                location = name;
                if(!modifyYamlFile.contains(name)) {
                    modifyYamlFile.createSection("." + name);
                    setVal = true;
                }
            }

            if(typeValue.equals("abilityscores")) {
                System.out.println("[DEBUG] Configuration section type is abilityscores.");
                String absPath = yamlPath + "." + name;
                if(!modifyYamlFile.contains(absPath + ".value")) {
                    modifyYamlFile.createSection(absPath + ".value");
                    modifyYamlFile.set(absPath + ".value",rpg.getConfigManager().getConfiguration().getInt("default-ability-score-value"));
                }
                if(!modifyYamlFile.contains(absPath + ".modifier")) {
                    modifyYamlFile.createSection(absPath + ".modifier");
                    modifyYamlFile.set(absPath + ".modifier", rpg.getConfigManager().getConfiguration().getInt("default-ability-score-modifier"));
                }
            }

            if(typeValue.equals("skill")) {
                System.out.println("[DEBUG] Configuration section type is skill.");
                String skillPath = yamlPath + "." + name;
                if(!modifyYamlFile.contains(skillPath + ".level")) {
                    modifyYamlFile.createSection(skillPath + ".level");
                    modifyYamlFile.set(skillPath + ".level",1);
                }
                if(!modifyYamlFile.contains(skillPath + ".experience")) {
                    modifyYamlFile.createSection(skillPath + ".experience");
                    modifyYamlFile.set(skillPath + ".experience", 0);
                }
            }

            System.out.println("File location: "+location);
            if(!modifyYamlFile.contains(location) || setVal) {

                switch (dataTypeString) {
                    case "String":
                    case "string":
                        if(!yamlPath.equalsIgnoreCase("") && !yamlPath.equalsIgnoreCase("none")) {
                            modifyYamlFile.set(yamlPath+"."+name, data);
                        } else {
                            modifyYamlFile.set(name, data);
                        }
                        break;
                    case "int":
                    case "integer":
                        modifyYamlFile.set(name, Integer.parseInt(data));
                        break;
                    case "bool":
                    case "boolean":
                        if(!yamlPath.equalsIgnoreCase("")) {
                            if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("1")) {
                                modifyYamlFile.set(yamlPath+"."+name, true);
                            } else {
                                modifyYamlFile.set(yamlPath+"."+name, false);
                            }
                        } else {
                            if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("1")) {
                                modifyYamlFile.set(name, true);
                            } else {
                                modifyYamlFile.set(name, false);
                            }
                        }
                    case "":
                    case "empty":
                        System.out.println("[DEBUG] configuration section has no data type, setting it empty.");
                        break;
                    default:
                        System.out.println("[DEBUG] configuration type has unrecognised data type!");
                        return;
                }
            }

            /*if(typeValue.equals("") || typeValue.equals("null") || typeValue.equals("none")) {
                System.out.println("[DEBUG] Configuration section has no selected type. No special actions applied.");
            }*/
        }

        HashMap<String, String> implementValues = new HashMap<>();

        if(!fileMap.containsKey(player.getUniqueId())) {
            fileMap.put(player.getUniqueId(), fileYaml);
        }
        modifyFileMap.put(player.getUniqueId(), modifyYamlFile);
        try { modifyYamlFile.save(fileYaml); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}

        this.yamlConfiguration = fileYaml;
    }



    private void initiateComboFiles(String saveString) {
        String comboPath = rpg.getDataFolder() + File.separator + "Data" + File.separator + "PlayerData" + File.separator + saveString + File.separator + "Combos";

        File comboFileYaml = new File(comboPath, player.getUniqueId().toString() + ".yml");
        YamlConfiguration modifyProfileYamlFile = YamlConfiguration.loadConfiguration(comboFileYaml);

        this.modifyComboYamlConfiguration = modifyProfileYamlFile;
        this.profileFileYaml = comboFileYaml;

        System.out.println("Yaml path for player " + player.getName() + ": " +comboPath);


        if(!modifyProfileYamlFile.contains("player-name")) {
            modifyProfileYamlFile.createSection("player-name");
            modifyProfileYamlFile.set("player-name",player.getName());
        }

        if(!profileFileYaml.exists()) {
            try {
                System.out.println("Creating yaml file for " + player.getName());

                File yamlDir = profileFileYaml.getParentFile();
                if(!yamlDir.exists()) {
                    yamlDir.mkdir();
                }

                profileFileYaml.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }

        try { modifyComboYamlConfiguration.save(profileFileYaml); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}



    }


    private void initiateJsonFiles() {

        Data data = new Data(player.getName(), 0, 0, new java.util.Date(), 0);



        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "JsonPlayerData";

        File fileJson = new File(path, player.getUniqueId() + ".json");
        System.out.println("path: " +path);


        if(!fileJson.exists()) {
            try {
                File dir = fileJson.getParentFile();
                dir.mkdir();
                fileJson.createNewFile();

                Gson gson = new Gson();
                Writer writer = new FileWriter(fileJson, false);
                gson.toJson(data, writer);
                writer.flush();
                writer.close();
                System.out.println("saved data for player " + player.getName());
            } catch (IOException e) { e.printStackTrace(); System.out.println("[RpgPlugin] ERROR CREATING " + fileJson.toString()); }
        }

    }

    public File getYamlFile() { return yamlConfiguration; }
    public YamlConfiguration getModifyYaml() { modifyYamlConfiguration = YamlConfiguration.loadConfiguration(yamlConfiguration); return modifyYamlConfiguration; }
    public YamlConfiguration getComboModifyYaml() { return modifyComboYamlConfiguration; }
    public File getComboFileYaml() {return  profileFileYaml;}



}
