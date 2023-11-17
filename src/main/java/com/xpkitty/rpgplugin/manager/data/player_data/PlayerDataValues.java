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
package com.xpkitty.rpgplugin.manager.data.player_data;

public enum PlayerDataValues {



    level("int","1","none", ""),
    experience("int","0","none", ""),
    player_class("String","default","none", ""),

    mining("empty","no","skills", "skill"),
    combat("empty","no","skills", "skill"),
    trading("empty","no","skills", "skill"),
    farming("empty","no","skills", "skill"),
    building("empty","no","skills", "skill"),
    crafting("empty","no","skills", "skill"),

    ability_scores("empty","no","none",""),

    STR("empty","no","ability_scores","abilityscores"),
    DEX("empty","no","ability_scores","abilityscores"),
    CON("empty","no","ability_scores","abilityscores"),
    INT("empty","no","ability_scores","abilityscores"),
    WIS("empty","no","ability_scores","abilityscores"),
    CHA("empty","no","ability_scores","abilityscores"),

    skinData("String","def","skin",""),
    skinSignature("String","def","skin",""),

    guild("empty","no","none",""),

    hogwarts("empty","no","none",""),
    house("String","none","hogwarts",""),
    year("String","none","hogwarts",""),

    sneak_ability_mode("boolean","true","ability_config",""),
    right_click_lightsaber_toggle_mode("boolean","true","ability_config",""),


    ACHIEVEMENTS("empty","no","none",""),

    KILL_GOBLIN("boolean","false","ACHIEVEMENTS",""),
    KILL_UNDEAD("boolean","false","ACHIEVEMENTS",""),
    EAT_ENT_STEW("boolean","false","ACHIEVEMENTS",""),
    DRINK_ATHELAS("boolean","false","ACHIEVEMENTS",""),
    DRINK_MIRUVOR("boolean","false","ACHIEVEMENTS","");


    private String dataType;
    private String defValue;
    private String path;
    private String valueType;


    PlayerDataValues(String dataType, String defValue, String path, String valueType) {
        this.dataType = dataType;
        this.defValue = defValue;
        this.path = path;
        this.valueType = valueType;
    }

    public String getDataType() { return dataType; }
    public String getDefValue() { return defValue; }
    public String getPath() { return path; }
    public String getType() { return valueType; }

}
