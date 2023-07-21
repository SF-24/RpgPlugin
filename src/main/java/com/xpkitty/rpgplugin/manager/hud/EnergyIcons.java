// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.hud;

public enum EnergyIcons {

    PREFIX(-1,"\uF82B\uF82A\uF825"),

    b20(20,"\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b19(19,"\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b18(18,"\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b17(17,"\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b16(16,"\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b15(15,"\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b14(14,"\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b13(13,"\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b12(12,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b11(11,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b10(10,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b9(9,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b8(8,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000\uF802\uE000"),
    b7(7,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000\uF802\uE000"),
    b6(6,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000\uF802\uE000"),
    b5(5,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000\uF802\uE000"),
    b4(4,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000\uF802\uE000"),
    b3(3,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001\uF802\uE000"),
    b2(2,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE000"),
    b1(1,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE001"),
    b0(0,"\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002\uF802\uE002");

    private final String display;
    final int count;

    EnergyIcons(int count, String display) {
        this.display=display; this.count=count;
    }

    public String getDisplay() { return display; }
    public int getCount() { return count; }

}
