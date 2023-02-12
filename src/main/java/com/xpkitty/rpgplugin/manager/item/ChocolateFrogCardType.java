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
