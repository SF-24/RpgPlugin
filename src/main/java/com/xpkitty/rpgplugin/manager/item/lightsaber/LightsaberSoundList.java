// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item.lightsaber;

public enum LightsaberSoundList {


    DARKSABER_SWING("minecraft:darksaber.swing",0.65f),
    DARKSABER_ON("minecraft:darksaber.darksaber_on",0.65f),
    DARKSABER_OFF("minecraft:darksaber.darksaber_off",0.65f),
    SABER_SWING("minecraft:saber.saberswing",1.0f),
    SABER_ON("minecraft:saber.sfx_saberon",1.0f),
    SABER_OFF("minecraft:saber.sfx_saberoff",1.0f);

    private final String soundId;
    private final float volume;

    LightsaberSoundList(String soundId, float volume) {
        this.soundId = soundId;
        this.volume = volume;
    }

    public String getSoundId() { return soundId; }
    public float getVolume() { return volume; }

}
