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

package com.xpkitty.rpgplugin.manager.data.guild_data;

import com.xpkitty.rpgplugin.manager.player_groups.guilds.ui.GuildBanner;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GuildDataClass {

    // guild variables

    int guildId;
    String guildName;

    UUID guildOwner;
    HashMap<UUID, GuildRank> members = new HashMap<>();

    ArrayList<UUID> pendingJoinRequests = new ArrayList<>();

    boolean isPublic = true;

    int guildLevel = 1;
    int guildXp = 0;

    GuildBanner banner = new GuildBanner();

    // has the guild been setup
    boolean isSetup = false;

    // setup the guild
    public void setupGuild(int id, String name, UUID owner) {
        guildId = id;
        guildName = name;
        guildOwner = owner;
        members.put(owner,GuildRank.GUILDMASTER);
        isSetup = true;
    }

    // get the banner
    public GuildBanner getBanner() {
        return banner;
    }

    // set banner item as a base banner with a colour
    public boolean setBanner(Material material) {
        return banner.setBanner(material);
    }

    // set banner item
    public boolean setBanner(ItemStack itemStack) {
        return banner.setBanner(itemStack);
    }

    // is a player a member of this guild
    public boolean isMember(UUID uuid) {
        return members.containsKey(uuid) || guildOwner.equals(uuid);
    }

    // add a member
    public void addMember(UUID uuid) {
        members.put(uuid, GuildRank.MEMBER);
    }

    public void sendJoinRequest(UUID uuid) {
        if(pendingJoinRequests.contains(uuid)) return;
        pendingJoinRequests.add(uuid);
    }

    // remove a member
    public boolean removeMember(UUID uuid) {
        if(members.containsKey(uuid)) {
            members.remove(uuid);
            return true;
        }
        return false;
    }

    // set a rank of a member
    public boolean setMemberRank(UUID uuid, GuildRank rank) {
        if(members.containsKey(uuid) && !members.get(uuid).equals(GuildRank.GUILDMASTER)) {
            members.put(uuid,rank);
            return true;
        }
        return false;
    }

    // add xp and test level
    public void updateLevel() {}
    public void addXp(int xp) {guildXp+=xp;}

    public int getXp() {return guildXp;}
    public int getLevel() {return guildLevel;}

    // get name of guild
    public String getGuildName() {return guildName;}

    // get uuid of guild owner
    public UUID getGuildOwner() {return  guildOwner;}

    // has guild been setup
    public boolean isSetup() {
        return this.isSetup;
    }

    // is guild public/open i.e. anyone can join
    public boolean isOpen() { return this.isPublic; }
}
