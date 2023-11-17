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
