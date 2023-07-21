// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spell_elements;

public enum SpellType {
    CHARM("charm"),
    JINX("jinx"),
    HEX("hex"),
    CURSE("curse"),
    TRANSFIGURATION("transfiguration"),
    HEALING_SPELL("healing spell"),
    COUNTER_SPELL("counter spell");

    private final String display;

    SpellType(String display) {
        this.display=display;
    }

    public String getDisplay() {return display;}
}
