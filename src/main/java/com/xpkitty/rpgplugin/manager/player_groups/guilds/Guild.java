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
package com.xpkitty.rpgplugin.manager.player_groups.guilds;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.profile_data.ProfileManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Guild {

    String guildName;
    Rpg rpg;
    int id;

    private File guildFile;
    private YamlConfiguration modifyGuildFile;

    public Guild(Rpg rpg, String guildName, int id) {
        this.guildName = guildName;
        this.rpg = rpg;
        this.id = id;
        initialize();
        saveGuild();
    }
/*
    public void addPlayer(Player player) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        String playerPath = "guilds." + id + ".players.";

        if(!yamlConfiguration.contains(playerPath + ProfileManager.getUniqueProfileId(rpg,player))) {
            yamlConfiguration.createSection(playerPath + ProfileManager.getUniqueProfileId(rpg,player));
            yamlConfiguration.createSection(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".rank");
            yamlConfiguration.set(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".rank", 0);
            yamlConfiguration.createSection(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".name");
            yamlConfiguration.set(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".name", player.getName());
            yamlConfiguration.createSection(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".profile");
            yamlConfiguration.set(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".profile", rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile());
        }

        rpg.getGuildDataManager().saveFile(yamlConfiguration, rpg.getGuildDataManager().getYamlFile());
    }

    public ArrayList<String> getPlayers() {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        return new ArrayList<>(yamlConfiguration.getConfigurationSection("guilds." + id + ".players").getKeys(false));
    }

    public HashMap<String,String> getPlayerNames() {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        HashMap<String,String> playerNames = new HashMap<>();

        String playerPath = "guilds." + id + ".players.";

        for(String player : getPlayers()) {
            String playerName = yamlConfiguration.getString(playerPath + player + ".name");
            String playerProfile = yamlConfiguration.getString(playerPath + player + ".profile");
            int rank = yamlConfiguration.getInt(playerPath + player + ".rank");
            //String rankDisplay = GuildManager.convertIntToRankName(rpg,rank);

            //playerNames.put(playerName+" ["+playerProfile+"]", rankDisplay);
        }

        return playerNames;
    }
*/
    public void saveGuild() {
        //TODO

        saveFile(modifyGuildFile, guildFile);
    }









    public void initialize() {
        String path = rpg.getDataFolder() + File.separator + "Data" + File.separator + "Guilds";
        guildFile = new File(path, id +".yml");
        File dir = guildFile.getParentFile();

        if(!dir.exists()) dir.mkdirs();
        if(!guildFile.exists()) {
            try { guildFile.createNewFile(); }
            catch (IOException e) {e.printStackTrace();}
        }

        modifyGuildFile = YamlConfiguration.loadConfiguration(guildFile);


        try { modifyGuildFile.save(guildFile); }
        catch (IOException e) { e.printStackTrace(); }
    }


    public void saveFile(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[AnotherRpgPlugin] Saved Guild data file for id: " + id);
    }

    public File getGuildFile() { return guildFile; }
    public YamlConfiguration getModifyGuildFile() { modifyGuildFile = YamlConfiguration.loadConfiguration(guildFile); return modifyGuildFile; }




}
