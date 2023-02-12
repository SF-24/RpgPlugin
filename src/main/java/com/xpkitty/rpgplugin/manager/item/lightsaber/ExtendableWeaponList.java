package com.xpkitty.rpgplugin.manager.item.lightsaber;

public enum ExtendableWeaponList {

    DARKSABER(9,8, false, true, false, 1.0f);



    private final int extendedID, sheathedId;
    private final float soundPitch;
    private final boolean lightsaberSound, allowExtendingWhenDamaged;
    private final boolean darksaberSound;

    ExtendableWeaponList(int sheathedId, int extendedId, boolean lightsaberSound, boolean darksaberSound, boolean allowExtendingWhenDamaged, float soundPitch) {
        this.sheathedId=sheathedId;
        this.extendedID=extendedId;
        this.lightsaberSound = lightsaberSound;
        this.allowExtendingWhenDamaged=allowExtendingWhenDamaged;
        this.soundPitch = soundPitch;
        this.darksaberSound = darksaberSound;
    }

    public int getExtendedID() { return extendedID; }
    public int getSheathedId() { return sheathedId; }
    public boolean isLightsaberSound() { return lightsaberSound; }
    public boolean allowExtendingWhenDamaged() { return allowExtendingWhenDamaged; }
    public float getSoundPitch() { return soundPitch; }
    public boolean isDarksaberSound() { return darksaberSound; }
}
