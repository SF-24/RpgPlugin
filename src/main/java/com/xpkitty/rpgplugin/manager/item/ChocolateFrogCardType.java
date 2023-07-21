// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item;

public enum ChocolateFrogCardType {

    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    CRYSTAL("Crystal"),
    EVENT("Event");

    String display;

    ChocolateFrogCardType(String display) {
        this.display=display;
    }

    public String getDisplay() {
        return display;
    }
}
