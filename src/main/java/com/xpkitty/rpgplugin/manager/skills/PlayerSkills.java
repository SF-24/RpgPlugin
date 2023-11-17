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
package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.manager.AbilityScores;

public enum PlayerSkills {

    ATHLETICS("Athletics", AbilityScores.STR, AbilityScores.CON),

    ACROBATICS("Acrobatics", AbilityScores.DEX, AbilityScores.DEX),
    SLEIGH_OF_HAND("Sleight of hand", AbilityScores.DEX, AbilityScores.DEX),
    STEALTH("Stealth", AbilityScores.DEX, AbilityScores.DEX),

    HISTORY("History", AbilityScores.INT, AbilityScores.INT),
    INVESTIGATION("Investigation", AbilityScores.INT, AbilityScores.WIS),
    NATURE("Nature", AbilityScores.INT, AbilityScores.WIS),

    SPELL_CASTING("Spell casting", AbilityScores.INT, AbilityScores.WIS),
    SPELL_CRAFTING("Spell crafting", AbilityScores.INT, AbilityScores.WIS),

    ANIMAL_HANDLING("Animal handling", AbilityScores.WIS, AbilityScores.INT),
    MEDICINE("Medicine", AbilityScores.WIS, AbilityScores.INT),

    INSIGHT("Insight", AbilityScores.WIS, AbilityScores.CHA),
    PERCEPTION("Perception", AbilityScores.WIS, AbilityScores.WIS),

    DECEPTION("Deception", AbilityScores.CHA, AbilityScores.WIS),
    INTIMIDATION("Intimidation", AbilityScores.CHA, AbilityScores.CHA),
    PERSUASION("Persuasion", AbilityScores.CHA, AbilityScores.CHA);

    final AbilityScores baseAbilityScore, secondaryAbilityScore;
    private final String name;

    PlayerSkills(String name, AbilityScores baseAbilityScore, AbilityScores secondaryAbilityScore) {
        this.baseAbilityScore=baseAbilityScore;
        this.secondaryAbilityScore=secondaryAbilityScore;
        this.name=name;
    }

    public String getName() { return name; }
    public AbilityScores getBaseAbilityScore() { return baseAbilityScore; }
    public AbilityScores getSecondaryAbilityScore() { return secondaryAbilityScore; }

}
