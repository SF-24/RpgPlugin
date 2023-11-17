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

public enum WandLength {

    NINE_HALF("9½"),
    NINE_THREE_QUARTERS("9¾"),
    TEN("10"),
    TEN_QUARTER("10¼"),
    TEN_HALF("10½"),
    TEN_THREE_QUARTERS("10¾"),
    ELEVEN("11"),
    ELEVEN_QUARTER("11¼"),
    ELEVEN_HALF("11½"),
    ELEVEN_THREE_QUARTERS("11¾"),
    TWELVE("12"),
    TWELVE_QUARTER("12¼"),
    TWELVE_HALF("12½"),
    TWELVE_THREE_QUARTERS("12¾"),
    THIRTEEN("13"),
    THIRTEEN_QUARTER("13¼"),
    THIRTEEN_HALF("13½"),
    THIRTEEN_THREE_QUARTERS("13¾"),
    FOURTEEN("14"),
    FOURTEEN_QUARTER("14¼"),
    FOURTEEN_HALF("14½");

    final String display;

    WandLength(String display) {
        this.display=display;
    }

    public String getDisplay() { return display; }
}
