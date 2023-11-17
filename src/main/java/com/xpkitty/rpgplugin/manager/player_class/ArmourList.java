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
package com.xpkitty.rpgplugin.manager.player_class;

public enum ArmourList {

    CLOTHES("Clothes","Clothes",0,0,0),
    LIGHT_ARMOUR("Light Armour","Light Armour",0,0,0),
    MEDIUM_ARMOUR("Medium Armour","Medium Armour",13,0,0),
    HEAVY_ARMOUR("Heavy Armour","Heavy Armour",15,0,0);

    String display,id;
    Integer str,dex,con;

    ArmourList(String display, String id, Integer str, Integer con, Integer dex) {
        this.display=display;
        this.id=id;
        this.str=str;
        this.con=con;
        this.dex=dex;
    }

    public String getDisplay() {return display;}
    public String getId() {return id;}
    public Integer getStr() {return str;}
    public Integer getCon() {return con;}
    public Integer getDex() {return dex;}
}
