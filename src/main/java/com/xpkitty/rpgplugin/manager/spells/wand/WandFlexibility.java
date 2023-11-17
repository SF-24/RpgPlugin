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
package com.xpkitty.rpgplugin.manager.spells.wand;

public enum WandFlexibility {

    UNBENDING("Unbending"),
    REASONABLY_SUPPLE("Reasonably Supple"),
    SURPRISINGLY_SWISHY("Surprisingly Swishy"),
    SLIGHTLY_SPRINGY("Slightly Springy"),
    RIGID("Rigid"),
    SUPPLE("Supple"),
    UNYIELDING("Unyielding"),
    HARD("Hard"),
    QUITE_BENDY("Quite Bendy"),
    BRITTLE("Brittle"),
    SOLID("Solid"),
    PLIANT("Pliant"),
    SLIGHTLY_YIELDING("Slightly Yielding"),
    QUITE_FLEXIBLE("Quite Flexible");

    final String display;

    WandFlexibility(String display) {
        this.display=display;
    }

    public String getDisplay() { return display; }
}
