package com.xpkitty.rpgplugin.manager.spells.wand;

public enum WandLength {

    NINE_HALF("9½"),
    NINE_THREE_QUARTERS("9¾"),
    TEN("10"),
    TEN_QUARTER("10¼"),
    TEN_HALF("10½"),
    TEN_THREE_QUARTERS("10¾"),
    ELEVEN("11"),
    ELEVEN_QUARTER("11¼"),
    ELEVEN_HALF("11½"),
    ELEVEN_THREE_QUARTERS("11¾"),
    TWELVE("12"),
    TWELVE_QUARTER("12¼"),
    TWELVE_HALF("12½"),
    TWELVE_THREE_QUARTERS("12¾"),
    THIRTEEN("13"),
    THIRTEEN_QUARTER("13¼"),
    THIRTEEN_HALF("13½"),
    THIRTEEN_THREE_QUARTERS("13¾"),
    FOURTEEN("14"),
    FOURTEEN_QUARTER("14¼"),
    FOURTEEN_HALF("14½");

    final String display;

    WandLength(String display) {
        this.display=display;
    }

    public String getDisplay() { return display; }
}
