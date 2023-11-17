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

public enum LightsaberType {
    SINGLE(1,1,1.75f,false),
    DOUBLE(1,1,1.75f,true),
    CROSSGUARD(1.5f,1.5f,1,false);

    private final float baseKnockback;
    private final float basePower;
    private final float baseSpeed;
    private final boolean slashing;

    LightsaberType(float baseKnockback, float basePower, float baseSpeed, boolean slashing) {
        this.baseKnockback=baseKnockback;
        this.basePower=basePower;
        this.baseSpeed=baseSpeed;
        this.slashing=slashing;
    }

    public boolean isSlashing() {return slashing;}
    public float getBaseKnockback() {return baseKnockback;}
    public float getBasePower() {return basePower;}
    public float getBaseSpeed() {return baseSpeed;}
}
