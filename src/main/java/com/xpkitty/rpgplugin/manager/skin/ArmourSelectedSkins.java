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
package com.xpkitty.rpgplugin.manager.skin;

import org.bukkit.Color;

public enum ArmourSelectedSkins {

    GRYFFINDOR_ROBES("robes_gryffindor","Gryffindor Robes",11,Color.fromRGB(255,255,252), 0),
    RAVENCLAW_ROBES("robes_ravenclaw","Ravenclaw Robes",12,Color.fromRGB(255,255,251), 0),
    HUFFLEPUFF_ROBES("robes_hufflepuff","Hufflepuff Robes",13,Color.fromRGB(255,255,253), 0 ),
    SLYTHERIN_ROBES("robes_slytherin","Slytherin Robes",14,Color.fromRGB(255,255,250), 0),
    EMERALD_ROBES("robes_green","Green Robes",15,Color.fromRGB(255,255,249), 4);

    final String id,name;
    final int modelData;
    final Color rgb;
    final double armor;

    ArmourSelectedSkins(String id, String name ,int modelData, Color rgb, double armor) {
        this.name = name;
        this.id = id;
        this.modelData = modelData;
        this.rgb = rgb;
        this.armor = armor;
    }

    public String getId() {return id;}
    public int getModelData() {return modelData;}
    public Color getColor() {return rgb;}
    public String getName() {return name;}
    public double getArmour() {return armor;}
}
