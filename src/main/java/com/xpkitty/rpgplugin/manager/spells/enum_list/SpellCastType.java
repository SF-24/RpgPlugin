// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.enum_list;

public enum SpellCastType {

    FAILS(1,2),
    FIZZLES(2,3),

    SEMI_SUCCESS(3,4),
    SUCCESS(3,5);

    final int expMin, expMax;

    SpellCastType(int expMin, int expMax) {
        this.expMax=expMax;
        this.expMin=expMin;
    }

    public int getMaximumExp() { return expMax; }
    public int getMinimumExp() { return expMin; }
}
