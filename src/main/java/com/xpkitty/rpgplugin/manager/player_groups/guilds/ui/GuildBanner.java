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

package com.xpkitty.rpgplugin.manager.player_groups.guilds.ui;


import com.xpkitty.rpgplugin.manager.item.ItemDataConverter;
import com.xpkitty.rpgplugin.manager.item.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.Collections;

public class GuildBanner {

    String serialisedBanner;

    public GuildBanner() {
        serialisedBanner = ItemDataConverter.encodeItemStackToBase64(new ItemStack(Material.WHITE_BANNER));
        updateBannerItem();
    }


    public boolean setBanner(Material banner) {
        if(ItemManager.isBanner(banner)) {
            serialisedBanner = ItemDataConverter.encodeItemStackToBase64(new ItemStack(banner));
            updateBannerItem();
            return true;
        }
        return false;
    }

    public boolean setBanner(ItemStack banner) {
        if(ItemManager.isBanner(banner.getType())) {
            serialisedBanner = ItemDataConverter.encodeItemStackToBase64(banner);
            updateBannerItem();
            return true;
        }
        return false;
    }

    public ItemStack getBannerItem() {
        return ItemDataConverter.decodeItemStackFromBase64(serialisedBanner);
    }

    public void updateBannerItem() {
        String name = ChatColor.GOLD + "Guild Banner";
        String lore = ChatColor.GRAY + "A banner for a guild";

        ItemStack banner = ItemDataConverter.decodeItemStackFromBase64(serialisedBanner);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
        bannerMeta.setDisplayName(name);
        bannerMeta.setLore(Collections.singletonList(lore));
        banner.setItemMeta(bannerMeta);
        serialisedBanner = ItemDataConverter.encodeItemStackToBase64(banner);
    }
}
