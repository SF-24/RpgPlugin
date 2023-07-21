// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_groups.guilds;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.profile_data.ProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GuildManager {
    Rpg rpg;

    public HashMap<Integer, Guild> guilds = new HashMap<>();

    public GuildManager(Rpg rpg) {
        this.rpg = rpg;
    }

    public void loadGuilds() {
        System.out.println("[AnotherRpgPlugin] Loading guilds:");

        for(Integer element : GuildManager.getGuildList(rpg)) {
            Guild guild = new Guild(rpg, GuildManager.getGuildName(rpg, element), element);
            guilds.put(element, guild);

            System.out.println("[AnotherRpgPlugin] Loaded guild of id " + element);
        }
    }

    public void loadGuild(Integer id) {
        Guild guild = new Guild(rpg, GuildManager.getGuildName(rpg, id), id);
        guilds.put(id, guild);

        System.out.println("[AnotherRpgPlugin] Loaded new guild of id " + id);

    }

    public Guild getGuildInstance(Integer id) {
        if(guilds.containsKey(id)) {
            return guilds.get(id);
        }
        return null;
    }

    public static void createGuild(Rpg rpg, Player player, String name) {
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
    }

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
    }

    public static ArrayList<Integer> getGuildList(Rpg rpg) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();

        ArrayList<Integer> guilds = new ArrayList<>();

        for(String key : yamlConfiguration.getConfigurationSection("guilds").getKeys(false)) {
            guilds.add(Integer.valueOf(key));

        }

        return guilds;
    }

    public static ArrayList<String> getGuildNameList(Rpg rpg) {
        YamlConfiguration yamlConfiguration = rpg.getGuildDataManager().getModifyYaml();

        ArrayList<String> guilds = new ArrayList<>();

        for(String element : yamlConfiguration.getConfigurationSection("guilds").getKeys(false)) {
            String name = yamlConfiguration.getString("guilds." + element + ".name");
            guilds.add(name);
        }

        return guilds;
    }
}
