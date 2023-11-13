// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item.lightsaber;

public enum LightsaberList {

    CROSS_GUARD(LightsaberType.CROSSGUARD,10,11,12,13,14),
    SABER_STAFF(LightsaberType.DOUBLE,20,21,22,23,24),
    SABER_1(LightsaberType.SINGLE,30,31,32,33,34),
    SABER_2(LightsaberType.SINGLE,40,41,42,43,44),
    SABER_5(LightsaberType.SINGLE,50,51,52,53,54);

    final private int sheathedId, redId, greenId, blueId, purpleId;
    private final LightsaberType lightsaberType;

    LightsaberList(LightsaberType lightsaberType, int sheathedId, int redId, int greenId, int blueId, int purpleId) {
        this.sheathedId=sheathedId;
        this.redId=redId;
        this.greenId=greenId;
        this.blueId=blueId;
        this.purpleId=purpleId;
        this.lightsaberType=lightsaberType;
    }

    public int getSheathedId() {return sheathedId;}

    public int getRedId() {return redId;}
    public int getBlueId() {
        return blueId;
    }
    public int getGreenId() {
        return greenId;
    }
    public int getPurpleId() {return purpleId;}

    public LightsaberType getLightsaberType() {return lightsaberType;}
}
