package com.xpkitty.rpgplugin.manager.data.profile_data;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ProfileManager {

    Rpg rpg;
    Player player;
    YamlConfiguration modifyProfileYaml;
    File profileFileYaml;

    public ProfileManager(Player player,Rpg rpg) {
        this.player = player;
        this.rpg = rpg;

        initiateProfileFiles();
    }

    private void initiateProfileFiles() {
        String comboPath = rpg.getDataFolder() + File.separator + "Data" + File.separator + "PlayerData";

        File profileFileYaml = new File(comboPath, player.getUniqueId() + ".yml");
        YamlConfiguration modifyProfileYamlFile = YamlConfiguration.loadConfiguration(profileFileYaml);

        this.modifyProfileYaml = modifyProfileYamlFile;
        this.profileFileYaml = profileFileYaml;

        System.out.println("Yaml path for player " + player.getName() + ": " +comboPath);

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

        if(!modifyProfileYaml.contains("player-name")) {
            modifyProfileYaml.createSection("player-name");
            modifyProfileYaml.set("player-name", player.getDisplayName());
            System.out.println("[DEBUG] 'Creating player-name' section in yml file for " + player.getName());
        }
        if(!modifyProfileYaml.contains("active-profile")) {
            modifyProfileYaml.createSection("active-profile");
            modifyProfileYaml.set("active-profile", "default");
            System.out.println("[DEBUG] 'Creating active-profile' section in yml file for " + player.getName());
        }
        if(!modifyProfileYaml.contains("profiles")) {
            modifyProfileYaml.createSection("profiles");
            modifyProfileYaml.createSection("profiles.default");
            modifyProfileYaml.createSection("profiles.default.name");
            modifyProfileYaml.set("profiles.default.name","Default");

            System.out.println("[DEBUG] 'Creating default profile' section in yml file for " + player.getName());
        }

        try { modifyProfileYaml.save(profileFileYaml); } catch (IOException e) { e.printStackTrace();
            System.out.println("Cannot save " + player.getName() + "'s yml file.");}
    }

    public YamlConfiguration getModifyProfileYaml() { return modifyProfileYaml; }
    public File getProfileFileYaml() { return profileFileYaml; }

    public String getActiveProfile() {
        return modifyProfileYaml.getString("active-profile");
    }


    public void createProfile(String profileId) {
        String profileName = profileId;
        profileId = profileId.toLowerCase(Locale.ROOT);
        if(!modifyProfileYaml.contains("profiles." + profileId)) {
            modifyProfileYaml.createSection("profiles." + profileId.toLowerCase(Locale.ROOT));
            modifyProfileYaml.createSection("profiles." + profileId + ".name");
            modifyProfileYaml.set("profiles." + profileId + ".name", profileName);

            System.out.println("[DEBUG] 'Creating default profile' section in yml file for " + player.getName());
        }

        try {
            modifyProfileYaml.save(profileFileYaml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActiveProfile(String profileId) {
        modifyProfileYaml.set("active-profile",profileId);

        createProfile(profileId);

        rpg.getConnectListener().reloadProfile(player);
    }

    public boolean checkIfProfileExists(String profileName) {
        if(modifyProfileYaml.contains("profiles." + profileName)) {
            return true;
        }

        return false;
    }

    public void deleteProfile(String profileName) {

        modifyProfileYaml.set("profiles." + profileName,null);

        try {
            modifyProfileYaml.save(profileFileYaml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUniqueProfileId(Rpg rpg, Player player) {
        ProfileManager profile = rpg.getConnectListener().getPlayerProfileInstance(player);
        String profileKey = profile.getActiveProfile();

        return player.getUniqueId() + "_" + profileKey;
    }
}
