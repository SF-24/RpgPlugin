// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_groups;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InviteManager {

    public ArrayList<UUID> partyInvites = new ArrayList<>();

    public ArrayList<String> guildInvites = new ArrayList<>();

    public boolean hasPartyInviteFromPlayer(Player player) {
        if(partyInvites.contains(player.getUniqueId())) {
            return true;
        }
        return false;
    }

    public boolean hasGuildInviteFromPlayer(Player invitedPlayerVariable, String guildName, Rpg rpg) {

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(invitedPlayerVariable).getModifyYaml();

        if(yamlConfiguration.contains("guild_invites")) {
            List<String> guildInvites = yamlConfiguration.getStringList("guild_invites");
            if(guildInvites.contains(guildName)) {
                return true;
            }

        } else {
            return false;
        }

        return false;
    }

    public void addGuildInviteFromPlayer(Player invitedPlayerVariable, String guildName, Rpg rpg) {
        Player player = invitedPlayerVariable;

        YamlConfiguration yamlConfiguration = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml();
        File file = rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile();


        if(!yamlConfiguration.contains("guild_invites")) {
            ArrayList<String> guildInvites = new ArrayList<>();
            guildInvites.add(guildName);
            yamlConfiguration.set("guild_invites",guildInvites);
        } else {
            List<String> guildInvites = yamlConfiguration.getStringList("guild_invites");
            if(!guildInvites.contains(guildName)) {
                guildInvites.add(guildName);
            }
            yamlConfiguration.set("guild_invites",guildInvites);
        }
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addPartyInviteFromPlayer(Player player) {
        if(!partyInvites.contains(player.getUniqueId())) {
            partyInvites.add(player.getUniqueId());
        }
    }

    public void removePartyInviteFromPlayer(Player player) {
        partyInvites.remove(player.getUniqueId());
    }
}
