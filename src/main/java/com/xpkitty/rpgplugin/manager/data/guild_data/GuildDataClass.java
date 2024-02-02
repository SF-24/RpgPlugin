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

import com.xpkitty.rpgplugin.manager.player_groups.guilds.ui.GuildBanner;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GuildDataClass {

    int guildId;
    String guildName;

    UUID guildOwner;
    HashMap<UUID, GuildRank> members = new HashMap<>();

    int guildLevel = 1;
    int guildXp = 0;

    GuildBanner banner = new GuildBanner();

    boolean isSetup = false;


    public void setupGuild(int id, String name, UUID owner) {
        guildId = id;
        guildName = name;
        guildOwner = owner;
        members.put(owner,GuildRank.GUILDMASTER);
        isSetup = true;
    }

    public GuildBanner getBanner() {
        return banner;
    }

    public boolean setBanner(Material material) {
        return banner.setBanner(material);
    }

    public boolean setBanner(ItemStack itemStack) {
        return banner.setBanner(itemStack);
    }

    public void addMember(UUID uuid) {
        members.put(uuid, GuildRank.MEMBER);
    }

    public boolean removeMember(UUID uuid) {
        if(members.containsKey(uuid)) {
            members.remove(uuid);
            return true;
        }
        return false;
    }

    public boolean setMemberRank(UUID uuid, GuildRank rank) {
        if(members.containsKey(uuid) && !members.get(uuid).equals(GuildRank.GUILDMASTER)) {
            members.put(uuid,rank);
            return true;
        }
        return false;
    }

    public String getGuildName() {return guildName;}
    public UUID getGuildOwner() {return  guildOwner;}

    public boolean isSetup() {
        return this.isSetup;
    }
}
