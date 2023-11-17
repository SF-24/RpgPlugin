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

public enum SpellCastType {

    FAILS(1,2),
    FIZZLES(2,3),

    SEMI_SUCCESS(3,4),
    SUCCESS(3,5);

    final int expMin, expMax;

    SpellCastType(int expMin, int expMax) {
        this.expMax=expMax;
        this.expMin=expMin;
    }

    public int getMaximumExp() { return expMax; }
    public int getMinimumExp() { return expMin; }
}
