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
package com.xpkitty.rpgplugin.manager;

import org.bukkit.Color;

import java.util.ArrayList;

public class MiscManager {
    public static ArrayList<Color> getColorList() {

        ArrayList<Color> colours = new ArrayList<>();
        colours.add(Color.RED);
        colours.add(Color.BLUE);
        colours.add(Color.WHITE);
        colours.add(Color.GRAY);
        colours.add(Color.LIME);
        colours.add(Color.OLIVE);
        colours.add(Color.ORANGE);
        colours.add(Color.PURPLE);
        colours.add(Color.YELLOW);
        colours.add(Color.TEAL);
        colours.add(Color.AQUA);
        colours.add(Color.BLACK);
        colours.add(Color.FUCHSIA);
        colours.add(Color.SILVER);
        colours.add(Color.NAVY);
        colours.add(Color.MAROON);
        colours.add(Color.GREEN);

        return colours;
    }

    public static Color getColor(int r, int g, int b) {
        return Color.fromRGB(r,g,b);
    }
}
