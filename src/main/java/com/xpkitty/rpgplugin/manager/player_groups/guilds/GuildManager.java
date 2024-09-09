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
package com.xpkitty.rpgplugin.manager.player_groups.guilds;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.guild_data.GuildDataClass;
import com.xpkitty.rpgplugin.manager.data.guild_data.GuildDataReader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class GuildManager {
    Rpg rpg;

    public GuildManager(Rpg rpg) { this.rpg = rpg; }


    public void loadGuilds() {
        System.out.println("[AnotherRpgPlugin] Loading guilds:");
        for(int guild : GuildManager.getGuildList()) {
            loadGuild(guild);
        }
    }

    public static void sendJoinRequest(int id, Player player) {

        Rpg rpg = Rpg.getRpg();

        if(isGuildOpen(id)) {
            addMemberToGuild(id,player);
        } else {
            player.sendMessage(ChatColor.AQUA + "The guild you are trying to join is not public");
            player.sendMessage(ChatColor.WHITE + "A join request has been sent.");
            sendJoinRequestToClosedGuild(id, player);
        }
    }

    public static void sendJoinRequestToClosedGuild(int id, Player player) {
        Rpg rpg = Rpg.getRpg();
        GuildDataClass guildData = getGuildFile(id);
        guildData.sendJoinRequest(player.getUniqueId());
        saveGuildFile(guildData,id);
    }

    public static void addMemberToGuild(int id, Player player)
    {
        GuildDataClass guildData = getGuildFile(id);
        guildData.addMember(player.getUniqueId());
        saveGuildFile(guildData, id);
    }

    public static boolean isGuildOpen(int id) {
        return getGuildFile(id).isOpen();
    }


    // Check if a player is in a specific guild
    public static boolean isPlayerInGuild(Integer id, UUID playerUUID) {
        Rpg rpg = Rpg.getRpg();
        if(getGuildList().contains(id)) {
            return loadGuildAndReturn(id).loadData(rpg, id).isMember(playerUUID);
        }
        return false;
    }

    // create a guild
    public static void createGuild(Player player, String name) {
        Rpg rpg = Rpg.getRpg();
        int id = rpg.getGuildConfig().getGuildNum(true);

        GuildDataReader guildDataReader = new GuildDataReader(rpg, id);
        guildDataReader.setup(id, name, player.getUniqueId());
    }

    // get the amount of guilds a player is in
    public static int getPlayerGuildCount(Player player) {
        int count = 0;

        if(getGuildList().size()>=1) {
            for (Integer id : getGuildList()) {
                if (GuildManager.isPlayerInGuild(id, player.getUniqueId())) {
                    count++;
                }
            }
        }
        return count;
    }



    // get a list of guild ids
    public static ArrayList<Integer> getGuildList() {
        ArrayList<Integer> guilds = new ArrayList<>();

        File dir = new File(GuildDataReader.getPath());
        if(!dir.exists()) {
            dir.mkdirs();
        }

        File[] fileList = dir.listFiles();

        if(fileList==null) {
            Bukkit.getLogger().log(Level.WARNING, "[RpgPlugin] ERROR in GuildManager.java line: 200 - could not list files of directory");
            return new ArrayList<>();
        }

        for(File file : fileList) {
            guilds.add(Integer.valueOf(trimFileExtension(file.getName())));
        }
        return guilds;
    }

    public static GuildDataClass getGuildFile(int id) {
        Rpg rpg = Rpg.getRpg();
        return GuildManager.loadGuildAndReturn(id).loadData(rpg, id);
    }

    public static void saveGuildFile(GuildDataClass guildData, int id) {
        GuildDataReader guildDataReader = GuildManager.loadGuildAndReturn(id);
        guildDataReader.saveFile(guildData,id);
    }

    public static String trimFileExtension(String str) {
        return str.substring(0, str.lastIndexOf('.'));
    }




    // load a guild
    public void loadGuild(Integer id) {
        GuildDataReader guildDataReader = new GuildDataReader(rpg,id);
        System.out.println("[RpgPlugin] Guild " + id + " has been loaded");
    }

    // load a guild and return a value
    public static GuildDataReader loadGuildAndReturn(Integer id) {
        GuildDataReader guildDataReader = new GuildDataReader(Rpg.getRpg(),id);
        System.out.println("[RpgPlugin] Guild " + id + " has been loaded");
        return guildDataReader;
    }

}







        /*
        int id = rpg.getGuildDataManager().getGuildNum(true);
        player.sendMessage("Created new guild: " + ChatColor.AQUA + name +ChatColor.RESET + " with id of " + id);

        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();

        if(!yamlConfiguration.contains("guilds." + id)) {
            yamlConfiguration.createSection("guilds." + id);
            yamlConfiguration.createSection("guilds."+id+".name");
            yamlConfiguration.set("guilds."+id+".name", name);
            yamlConfiguration.createSection("guilds."+id+".players");
            yamlConfiguration.createSection("guilds."+id+".players." + ProfileManager.getUniqueProfileId(rpg,player) + ".rank");
            yamlConfiguration.set("guilds."+id+".players." + ProfileManager.getUniqueProfileId(rpg,player) + ".rank", -1);
            yamlConfiguration.createSection("guilds."+id+".players."+ ProfileManager.getUniqueProfileId(rpg,player) + ".name");
            yamlConfiguration.set("guilds."+id+".players." + ProfileManager.getUniqueProfileId(rpg,player) + ".name", player.getName());

            String playerPath = "guilds." + id + ".players.";
            yamlConfiguration.createSection(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".profile");
            yamlConfiguration.set(playerPath + ProfileManager.getUniqueProfileId(rpg,player) + ".profile", rpg.getConnectListener().getPlayerProfileInstance(player).getActiveProfile());
        }

        rpg.getGuildDataManager().saveFile(yamlConfiguration,rpg.getGuildDataManager().getYamlFile());

        rpg.getGuildManager().loadGuild(id);
    */


/*
    public static ArrayList<Integer> getGuildsByPlayer(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();

        ArrayList<Integer> guilds = new ArrayList<>();

        for(String key : yamlConfiguration.getConfigurationSection("guilds").getKeys(false)) {
            String playerPrefix = "guilds." + key + ".players.";
            if(yamlConfiguration.contains(playerPrefix + ProfileManager.getUniqueProfileId(rpg,player))) {
                guilds.add(Integer.valueOf(key));
            }
        }

        return guilds;
    }


    public static int getPlayerRankInGuild(Rpg rpg, Player player, Integer guildId) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        String guildPath = "guilds." + guildId + ".players." + ProfileManager.getUniqueProfileId(rpg,player);
        int rank = -2;

        if(yamlConfiguration.contains(guildPath)) {
            rank = yamlConfiguration.getInt(guildPath+".rank");
        }

        return rank;
    }


    public static String getPlayerRankInGuildAsString(Rpg rpg, Player player, Integer guildId) {
        int rankInt = getPlayerRankInGuild(rpg, player, guildId);

        return convertIntToRankName(rpg,rankInt);
    }

    public static String convertIntToRankName(Rpg rpg, Integer rankInt) {

        if(rankInt != -2) {
            if(rankInt == -1) {
                return "Guild Leader";
            }
            if(rankInt == 0) {
                return "Member";
            }
            if(rankInt == 1) {
                return "Officer";
            }
            return "Member";

        }

        return null;
    }

    public static String getGuildName(Rpg rpg, Integer guildId) {
        String prefix = "guilds." + guildId + ".";
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        String name = yamlConfiguration.getString(prefix + "name");
        return name;
    }


    public static int getPlayerGuildCount(Rpg rpg, Player player) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();
        int count = 0;

        for(String element : yamlConfiguration.getConfigurationSection("guilds").getKeys(false)) {
            String path = "guilds." + element + ".players." + ProfileManager.getUniqueProfileId(rpg,player);
            if(yamlConfiguration.contains(path)) {
                count++;
            }
        }

        return count;
    }

    public static Integer getGuildIdByName(Rpg rpg, String guildName) {
        for(Integer id : GuildManager.getGuildList(rpg)) {
            if(GuildManager.getGuildName(rpg,id).equalsIgnoreCase(guildName)) {
                return id;
            }
        }

        return -1;
    }/*

    public static ArrayList<String> getGuildNameList(Rpg rpg) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();

        ArrayList<String> guilds = new ArrayList<>();

        for(String element : yamlConfiguration.getConfigurationSection("guilds").getKeys(false)) {
            String name = yamlConfiguration.getString("guilds." + element + ".name");
            guilds.add(name);
        }

        return guilds;
    }
}*/
