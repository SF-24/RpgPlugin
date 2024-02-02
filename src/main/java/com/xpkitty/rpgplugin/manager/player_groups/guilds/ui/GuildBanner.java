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


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.Collections;

public class GuildBanner {

    ItemStack banner;
    BannerMeta bannerMeta;

    ArrayList<Material> banners = new ArrayList<>();

    public GuildBanner() {
        banner = new ItemStack(Material.WHITE_BANNER);
        bannerMeta = (BannerMeta) banner.getItemMeta();
        updateBannerItem();

        banners.add(Material.WHITE_BANNER);
        banners.add(Material.BLACK_BANNER);
        banners.add(Material.BLUE_BANNER);
        banners.add(Material.RED_BANNER);
        banners.add(Material.YELLOW_BANNER);
        banners.add(Material.GREEN_BANNER);
        banners.add(Material.LIME_BANNER);
        banners.add(Material.CYAN_BANNER);
        banners.add(Material.ORANGE_BANNER);
        banners.add(Material.BROWN_BANNER);
        banners.add(Material.GRAY_BANNER);
        banners.add(Material.LIGHT_GRAY_BANNER);
        banners.add(Material.PINK_BANNER);
        banners.add(Material.PURPLE_BANNER);
        banners.add(Material.MAGENTA_BANNER);
        banners.add(Material.LIGHT_BLUE_BANNER);
    }


    public boolean setBanner(Material banner) {
        if(banners.contains(banner)) {
            this.banner = new ItemStack(banner);
            this.banner.setItemMeta(bannerMeta);
            updateBannerItem();
            return true;
        }
        return false;
    }

    public boolean setBanner(ItemStack banner) {
        if(banners.contains(banner.getType())) {
            this.banner = banner;
            this.bannerMeta = (BannerMeta) banner.getItemMeta();
            updateBannerItem();
            return true;
        }
        return false;
    }

    public ItemStack getBannerItem() {
        return this.banner;
    }

    public void updateBannerItem() {
        String name = ChatColor.GOLD + "Guild Banner";
        String lore = ChatColor.GRAY + "A banner for a guild";
        bannerMeta.setDisplayName(name);
        bannerMeta.setLore(Collections.singletonList(lore));
        bannerMeta.setLocalizedName("guild_banner");
        banner.setItemMeta(bannerMeta);
    }
}
