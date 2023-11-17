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
package com.xpkitty.rpgplugin.manager.hogwarts;

import org.bukkit.ChatColor;

public enum HogwartsHouseList {
    GRYFFINDOR(ChatColor.RED.toString(), ChatColor.DARK_RED.toString(),"Gryffindor"),
    RAVENCLAW(ChatColor.BLUE.toString(), ChatColor.DARK_BLUE.toString(), "Ravenclaw"),
    HUFFLEPUFF(ChatColor.YELLOW.toString(), ChatColor.GOLD.toString(), "Hufflepuff"),
    SLYTHERIN(ChatColor.GREEN.toString(), ChatColor.DARK_GREEN.toString(), "Slytherin");

    final private String prefix, darkPrefix, display;

    HogwartsHouseList(String prefix, String darkPrefix, String display) {
        this.prefix=prefix;
        this.darkPrefix=darkPrefix;
        this.display = display;
    }

    public String getPrefix() { return prefix; }

    public String getDarkPrefix() { return darkPrefix; }

    public String getDisplay() { return display; }
}
