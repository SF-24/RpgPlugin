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

