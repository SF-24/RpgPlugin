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
package com.xpkitty.rpgplugin.manager.spells.enum_list;

public enum SpellCastTypeForLearnedSpell {

    FIZZLED(0,0,0,false),
    CAST(0,1,1,true),
    SUCCESS(5,1,2,true),
    GOOD_SUCCESS(10,2,3,true),
    BETTER_SUCCESS(15,2,4,true),
    LARGE_SUCCESS(20,3,5,true),
    OUTSTANDING(30,4,6,true);

    final int requiredRoll, minExp, maxExp;
    final boolean cast;

    SpellCastTypeForLearnedSpell(int requiredRoll, int minExp, int maxExp, boolean cast) {
        this.requiredRoll=requiredRoll;
        this.minExp=minExp;
        this.maxExp=maxExp;
        this.cast=cast;
    }

    public int getRequiredRoll() { return requiredRoll; }
    public int getMinExp() { return minExp; }
    public int getMaxExp() { return maxExp; }
    public boolean isCast() { return cast; }
}
