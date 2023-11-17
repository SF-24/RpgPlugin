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
package com.xpkitty.rpgplugin.manager.data.player_settings.settings_data;

import com.google.gson.Gson;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_settings.settings_manager.LightsaberSwitchSetting;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class SettingsReader {

    public static File getFile(Rpg rpg, Player player) {
        UUID id = player.getUniqueId();

        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "PlayerData" + File.separator + "Settings";
        File file = new File(path,id + ".json");

        if(!file.exists()) {
            makeNewFile(file);
        }
        return file;
    }

    public static void initialiseFile(Rpg rpg, Player player) {
        getFile(rpg,player);
    }


    //makes new player setting json file
    public static void makeNewFile(File file) {
        try {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        SettingsData settingsData = makeEmptyData();
        writeData(settingsData, file);
    }

    //writes player settings to file
    public static void writeData(SettingsData settingsData, File file) {
        Writer writer = null;
        Gson gson = new Gson();

        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writer==null) {
            System.err.println("[RpgPlugin] ERROR! Attempted writing to file \"" + file.getName() + "\" Error! Writer == null");
            return;
        }

        //IF WRITER IS NOT NULL
        gson.toJson(settingsData, writer);
        try {
        writer.flush();
        writer.close();
        } catch (IOException e) {
            System.err.println("[RpgPlugin] Error in lines 58 and 59 of class SettingsReader in RpgPlugin. Please report this to an administrator or developer of RpgPlugin by XpKitty. Could not flush or close writer.");
        }

        System.out.println("[RpgPlugin] written json data of \"" + file.getName() + "\" to file");
    }

    public static SettingsData makeEmptyData() {
        return new SettingsData(true, LightsaberSwitchSetting.RIGHT_CLICK);
    }

    //loads player json settings file
    public SettingsData loadData(Rpg rpg, Player player) {
        File file = getFile(rpg, player);
        Gson gson = new Gson();
        Reader reader = null;

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(reader==null) {
            System.err.println("[RpgPlugin] Error reading file \"" + file.getName() + "\"");
            return null;
        }

        return gson.fromJson(reader, SettingsData.class);
    }
}
