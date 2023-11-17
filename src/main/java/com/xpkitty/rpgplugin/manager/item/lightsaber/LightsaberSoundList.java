/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
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
