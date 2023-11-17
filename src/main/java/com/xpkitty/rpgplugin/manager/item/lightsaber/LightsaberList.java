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
