// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
