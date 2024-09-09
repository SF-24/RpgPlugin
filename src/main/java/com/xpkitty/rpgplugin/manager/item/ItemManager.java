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

package com.xpkitty.rpgplugin.manager.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemManager {

    public static ArrayList<Material> getBanners() {
        ArrayList<Material> banners = new ArrayList();
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
        return banners;
    }

    public static boolean isBanner(Material material) {
        return getBanners().contains(material);
    }
}
