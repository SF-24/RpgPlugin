package com.xpkitty.rpgplugin.manager.item.lightsaber;

public enum LightsaberList {

    CROSS_GUARD(10,11,12,13,14),
    SABER_STAFF(20,21,22,23,24),
    SABER_1(30,31,32,33,34),
    SABER_2(40,41,42,43,44),
    SABER_5(50,51,52,53,54);

    final private int sheathedId, redId, greenId, blueId, purpleId;

    LightsaberList(int sheathedId, int redId, int greenId, int blueId, int purpleId) {
        this.sheathedId=sheathedId;
        this.redId=redId;
        this.greenId=greenId;
        this.blueId=blueId;
        this.purpleId=purpleId;
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
}
