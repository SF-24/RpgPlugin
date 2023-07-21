// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class.abilities;

import org.bukkit.event.block.Action;

import java.util.ArrayList;
import java.util.Arrays;

public enum ClickType {

    LEFT(new ArrayList<org.bukkit.event.block.Action>(Arrays.asList(Action.LEFT_CLICK_AIR,Action.LEFT_CLICK_BLOCK)),'L'),
    RIGHT(new ArrayList<org.bukkit.event.block.Action>(Arrays.asList(Action.RIGHT_CLICK_AIR,Action.RIGHT_CLICK_BLOCK)),'R');

    final ArrayList<org.bukkit.event.block.Action> clickTypes;
    final char abbreviation;

    ClickType(ArrayList<org.bukkit.event.block.Action> clickTypes, char abbreviation) {
        this.abbreviation=abbreviation;
        this.clickTypes=clickTypes;
    }

    public ArrayList<Action> getClickTypes() {return clickTypes;}
    public char getAbbreviation() {return abbreviation;}
}
