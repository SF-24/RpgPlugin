// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
