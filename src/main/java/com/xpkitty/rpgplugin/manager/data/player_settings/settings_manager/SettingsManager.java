// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_data.SettingsReader;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsManager {
    Rpg rpg;
    public SettingsManager(Rpg rpg) {
        this.rpg=rpg;
    }

    public void addPlayer(Player player) {
        SettingsReader.initialiseFile(rpg,player);
    }

    public static ArrayList<SettingsList> getEnumListOfImplementedSettings() {
        ArrayList<SettingsList> list = new ArrayList<>();
        for(SettingsList element : SettingsList.values()) {
            if(element.isImplemented) {
                list.add(element);
            }
        }
        return list;
    }

    public static ArrayList<String> getStringListOfImplementedSettings() {
        ArrayList<String> list = new ArrayList<>();
        for(SettingsList element : SettingsList.values()) {
            if(element.isImplemented) {
                list.add(element.name().toLowerCase(Locale.ROOT));
            }
        }
        return list;
    }

    public static boolean checkStringIsSetting(String element) {
        if(getStringListOfImplementedSettings().contains(element)) {
            return true;
        }
        return false;
    }
}

