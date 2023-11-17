
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

package com.xpkitty.rpgplugin.manager.data.guild_data;

import com.google.gson.Gson;
import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class GuildDataReader {
    Rpg rpg;
    int guildId;
    String guildName;
    UUID owner;

    public GuildDataReader(Rpg rpg, int guildId) {
        this.rpg=rpg;
        this.guildId=guildId;

        try {
            initiateFile(rpg, guildId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiateVariables(String guildName, UUID owner) {
        this.guildName=guildName;
        this.owner=owner;
    }

    public static String getPath() {
        return ("plugins" + File.separator + "RpgPlugin" + File.separator + "Data" + File.separator + "GuildData");
    }

    private void initiateFile(Rpg rpg, int guildId) throws Exception {

        System.out.println("PATH: " + getPath());

        File file = new File(getPath(), guildId +".json");

        if(!file.exists()) {
            makeNewFile(file);
        }
    }

    // gets player data json file
    public static File getFile(Rpg rpg, int guildId) {

        File file = new File(getPath(), guildId + ".json");

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

        GuildDataClass guildDataClass = makeEmptyData();
        writeData(guildDataClass, file);
    }


    // write data to a file
    public static void writeData(GuildDataClass settingsData, File file) {
        Writer writer = null;
        Gson gson = new Gson();

        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(writer==null) {
            System.err.println("[rpg] ERROR! Attempted writing to guild file \"" + file.getName() + "\" Error! Writer == null");
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

        System.out.println("[rpg] written json data of guild file \"" + file.getName() + "\" to file");
    }

    // make empty data file
    public static GuildDataClass makeEmptyData() { return new GuildDataClass(); }

    //loads player json data file
    public GuildDataClass loadData(Rpg rpg, int guildId) {
        File file = getFile(rpg, guildId);
        Gson gson = new Gson();
        Reader reader = null;

        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(reader==null) {
            System.err.println("[rpg] Error reading guild file \"" + file.getName() + "\"");
            return null;
        }

        GuildDataClass gdc = gson.fromJson(reader, GuildDataClass.class);
        if(gdc==null) {
            System.out.println("ERROR! GuildDataClass is null");
        }

        assert gdc != null;
        //gdc.updateSkills();

        return gdc;
    }

    public void saveFile(GuildDataClass data, int guildId) {
        writeData(data, getFile(rpg,guildId));
    }

    public void saveFile(GuildDataClass data, Player player) {
        writeData(data, getFile(rpg,guildId));
    }

    public void setup(int id,String name, UUID owner) {
        GuildDataClass data = loadData(rpg, id);
        if(!data.isSetup) {
            data.setupGuild(id, name, owner);
            saveFile(data, id);
        }
    }
}
