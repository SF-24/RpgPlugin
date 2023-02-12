package com.xpkitty.rpgplugin.manager.hogwarts;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class HogwartsHouseManager {

    //SET
    public static void setHogwartsHouse(Rpg rpg, Player player, HogwartsHouseList house) {

        PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
        YamlConfiguration yamlConfiguration = playerDataManager.getModifyYaml();
        File file = playerDataManager.getYamlFile();

        if(!yamlConfiguration.contains("hogwarts.year")) {
            yamlConfiguration.createSection("hogwarts.year");
        }

        if(!yamlConfiguration.contains("hogwarts.house")) {
            yamlConfiguration.createSection("hogwarts.house");
        }

        if(yamlConfiguration.getString("hogwarts.year").equalsIgnoreCase("none")) {
            yamlConfiguration.set("hogwarts.year","year1");
        }

        yamlConfiguration.set("hogwarts.house",house.name().toLowerCase(Locale.ROOT));

        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setHousePoints(Rpg rpg, HogwartsHouseList house, int points) {
        YamlConfiguration yamlConfiguration = rpg.getHogwartsDataManager().getModifyYaml();
        File file = rpg.getHogwartsDataManager().getYamlFile();

        if(yamlConfiguration.contains("house_points." + house.name().toLowerCase(Locale.ROOT))) {
            yamlConfiguration.set("house_points." + house.name().toLowerCase(Locale.ROOT), points);
            rpg.getHogwartsDataManager().saveFile(yamlConfiguration, file);
        } else {
            System.out.println("[AnotherRpgPlugin] ERROR! CANNOT SET HOUSE POINTS FOR " + house.name());
        }
    }


    public static void setHousePoints(Rpg rpg, Player player, int points) {
        if(getHogwartsHouse(rpg, player) != null) {
            setHousePoints(rpg, getHogwartsHouse(rpg,player), points);
        }
    }




    // GET
    public static int getHousePoints(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getHogwartsDataManager().getModifyYaml();
        if(getHogwartsHouse(rpg, player) == null) {
            return -1;
        }

        return getHousePoints(rpg, getHogwartsHouse(rpg,player));
    }

    public static int getHousePoints(Rpg rpg, HogwartsHouseList house) {
        YamlConfiguration yamlConfiguration = rpg.getHogwartsDataManager().getModifyYaml();
        if(yamlConfiguration.contains("house_points." + house.name().toLowerCase(Locale.ROOT))) {
            return yamlConfiguration.getInt("house_points." + house.name().toLowerCase(Locale.ROOT));
        }

        System.out.println("[RpgPlugin] Could not get house points for " + house.name());
        return 0;
    }


    public static HogwartsHouseList getHogwartsHouse(Rpg rpg, Player player) {
        String house = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getString("hogwarts.house");

        for(HogwartsHouseList hogwartsHouse : HogwartsHouseList.values()) {
            if(house.equalsIgnoreCase(hogwartsHouse.name())) {
                return hogwartsHouse;
            }
        }

        return null;
    }


    public static HogwartsHouseList getHouseFromString(String string) {
        for(HogwartsHouseList house : HogwartsHouseList.values()) {
            if(string.equalsIgnoreCase(house.name())) {
                return house;
            }
        }

        return null;
    }
}
