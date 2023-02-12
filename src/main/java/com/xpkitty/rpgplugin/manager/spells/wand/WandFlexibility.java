package com.xpkitty.rpgplugin.manager.spells.wand;

public enum WandFlexibility {

    UNBENDING("Unbending"),
    REASONABLY_SUPPLE("Reasonably Supple"),
    SURPRISINGLY_SWISHY("Surprisingly Swishy"),
    SLIGHTLY_SPRINGY("Slightly Springy"),
    RIGID("Rigid"),
    SUPPLE("Supple"),
    UNYIELDING("Unyielding"),
    HARD("Hard"),
    QUITE_BENDY("Quite Bendy"),
    BRITTLE("Brittle"),
    SOLID("Solid"),
    PLIANT("Pliant"),
    SLIGHTLY_YIELDING("Slightly Yielding"),
    QUITE_FLEXIBLE("Quite Flexible");

    final String display;

    WandFlexibility(String display) {
        this.display=display;
    }

    public String getDisplay() { return display; }
}
