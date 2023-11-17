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
package com.xpkitty.rpgplugin.manager.spells.spell_elements;

public enum SpellFunction {

    SHIELD_REFLECT("shieldTime","shieldHealth","shieldProtection"),
    SHIELD("shieldTime","shieldHealth","shieldProtection"),
    BLOCK_PLACE("blockType", "count", ""),
    EXPLOSION("explosionType", "explosionStrength", ""),
    ENTITY_DAMAGE("damage", "", ""),
    ENTITY_POTION_EFFECT("effect","effectTimeLength","amplifier"),
    ENTITY_VECTOR("x","y","z"),
    ENTITY_STUN("stunTime","setCrawl","damage"),
    ENTITY_DISARM("disarmStrengthBase","isKnockBack",""),
    ENTITY_KNOCKBACK("strength","isLowerY",""),
    DOOR_OPEN("","",""),
    DOOR_CLOSE("","",""),
    SET_FIRE("isSoulFire","entity_fire_time",""),
    SET_WATER("","","");

    final String val1, val2, val3;

    SpellFunction(String val1, String val2, String val3) {
        this.val1=val1;
        this.val2=val2;
        this.val3=val3;
    }

    public String getVal1() { return val1; }
    public String getVal2() { return val2; }
    public String getVal3() { return val3; }
}
