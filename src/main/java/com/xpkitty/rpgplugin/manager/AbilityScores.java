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
package com.xpkitty.rpgplugin.manager;

import org.bukkit.ChatColor;

public enum AbilityScores {
    STR(ChatColor.RED.toString(), ChatColor.DARK_RED.toString()),
    DEX(ChatColor.GREEN.toString(), ChatColor.DARK_GREEN.toString()),
    CON(ChatColor.YELLOW.toString(), ChatColor.GOLD.toString()),
    INT(ChatColor.AQUA.toString(),ChatColor.DARK_AQUA.toString()),
    WIS(ChatColor.BLUE.toString(),ChatColor.DARK_BLUE.toString()),
    CHA(ChatColor.LIGHT_PURPLE.toString(),ChatColor.DARK_PURPLE.toString());

    private final String colour;
    private final String darkColour;

    AbilityScores(String colour, String darkColour) {
        this.colour=colour;
        this.darkColour=darkColour;
    }
    
    public String getColour() {return colour;}
    public String getDarkerColour() {return darkColour;}
}
