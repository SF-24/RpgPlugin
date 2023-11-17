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
package com.xpkitty.rpgplugin.manager.player_class.abilities;

import com.xpkitty.rpgplugin.manager.AbilityScores;
import org.bukkit.Material;

public enum AbilityType {

    DASH("Dash",AbilityScores.DEX, Material.PEONY, 40),
    LEAP("Leap",AbilityScores.DEX,Material.PEONY,42),
    ANVIL_STRIKE("Anvil Strike",AbilityScores.STR, Material.PEONY, 41),
    REND("Rend",AbilityScores.STR, Material.PEONY, 43),
    SAP("Sap",AbilityScores.STR, Material.PEONY, 44);

    final int modelData;
    final Material texture;
    final String displayName;
    final AbilityScores mainAbilityScore;

    AbilityType(String displayName, AbilityScores mainAbilityScore, Material texture, int modelData) {
        this.mainAbilityScore = mainAbilityScore;
        this.displayName = displayName;
        this.texture = texture;
        this.modelData = modelData;
    }

    public String getName() { return displayName; }
    public AbilityScores getMainAbilityScore() { return mainAbilityScore; }
    public Material getTexture() {return texture;}
    public int getModelData() {return modelData;}

}

