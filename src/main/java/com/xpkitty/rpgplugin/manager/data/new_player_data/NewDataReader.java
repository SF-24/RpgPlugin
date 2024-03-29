
/*
 *     Copyright (C) 2024 Sebastian Frynas
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

package com.xpkitty.rpgplugin.manager.data.new_player_data;

import com.google.gson.Gson;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.profile_data.ProfileManager;
import com.xpkitty.rpgplugin.manager.skills.PlayerSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class NewDataReader {
    private final Rpg rpg;

    public NewDataReader(Player player, Rpg rpg) {
        this.rpg = rpg;


        try {
            initiateFile(player.getUniqueId(), rpg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initiateFile(UUID uuid, Rpg rpg) throws Exception {

        String path = "plugins" + File.separator + "RpgPlugin" + File.separator + "Data" + File.separator + "PlayerData" + File.separator + rpg.getConnectListener().getPlayerProfileInstance(Objects.requireNonNull(Bukkit.getPlayer(uuid))).getActiveProfile();
        System.out.println("PATH: " + path);

        File file = new File(path, uuid +".json");

        if(!file.exists()) {
            makeNewFile(file);
        }
    }

    // gets player data json file
    public static File getFile(Rpg rpg, Player player) {
        UUID id = player.getUniqueId();

        String path = "plugins" + File.separator + "RpgPlugin" + File.separator + "Data" + File.separator + "PlayerData" + File.separator + ProfileManager.getUniqueProfileId(rpg,player);
        File file = new File(path,id + ".json");

        if(!file.exists()) {
            makeNewFile(file);
        }
        return file;
    }

    public static void makeNewFile(File file) {
        try {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerDataClass playerDataClass = makeEmptyData();
        writeData(playerDataClass, file);
    }


    // write data to a file
    public static void writeData(PlayerDataClass settingsData, File file) {
        Writer writer = null;
        Gson gson = new Gson();

        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writer==null) {
            System.err.println("[rpg] ERROR! Attempted writing to file \"" + file.getName() + "\" Error! Writer == null");
            return;
        }

        //IF WRITER IS NOT NULL
        gson.toJson(settingsData, writer);
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("[rpg] Error in lines 58 and 59 of class SettingsReader in RpgPlugin. Please report this to an administrator or developer of RpgPlugin by XpKitty. Could not flush or close writer.");
        }

        System.out.println("[rpg] written json data of \"" + file.getName() + "\" to file");
    }

    // make empty data file
    public static PlayerDataClass makeEmptyData() {
        return new PlayerDataClass();
    }

    //loads player json data file
    public PlayerDataClass loadData(Rpg rpg, Player player) {
        File file = getFile(rpg, player);
        Gson gson = new Gson();
        Reader reader = null;

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(reader==null) {
            System.err.println("[rpg] Error reading file \"" + file.getName() + "\"");
            return null;
        }

        PlayerDataClass pdc = gson.fromJson(reader, PlayerDataClass.class);
        if(pdc==null) {
            System.out.println("ERROR! PlayerDataClass is null");
        }

        assert pdc != null;
        pdc.updateSkills();

        return pdc;
    }

    public void saveFile(PlayerDataClass data, UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        assert player != null;
        writeData(data, getFile(rpg,player));
    }

    public void saveFile(PlayerDataClass data, Player player) {
        writeData(data, getFile(rpg,player));
    }

    public int getSkillLevel(Player player, PlayerSkills skill) {
        return loadData(rpg, player).getSkillLevel(skill);
    }

    public void addSkillLevel(Player player, PlayerSkills skill, int amount) {
        PlayerDataClass data = loadData(rpg, player);
        int level = data.getSkillLevel(skill);
        level +=amount;
        data.setLevel(skill,level);
        saveFile(data,player);
    }

    public void addXp(Player player, PlayerSkills skill, int amount) {
        PlayerDataClass data = loadData(rpg, player);
        int exp = data.getSkillXp(skill);
        exp+=amount;
        data.setXp(skill,exp);
        saveFile(data,player);
    }

}
