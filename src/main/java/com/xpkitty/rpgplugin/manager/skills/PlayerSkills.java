package com.xpkitty.rpgplugin.manager.skills;

import com.xpkitty.rpgplugin.manager.AbilityScores;

public enum PlayerSkills {

    ATHLETICS(AbilityScores.STR, AbilityScores.CON),

    ACROBATICS(AbilityScores.DEX, AbilityScores.DEX),
    SLEIGH_OF_HAND(AbilityScores.DEX, AbilityScores.DEX),
    STEALTH(AbilityScores.DEX, AbilityScores.DEX),

    HISTORY(AbilityScores.INT, AbilityScores.INT),
    INVESTIGATION(AbilityScores.INT, AbilityScores.WIS),
    NATURE(AbilityScores.INT, AbilityScores.WIS),

    SPELL_CASTING(AbilityScores.INT, AbilityScores.WIS),
    SPELL_CRAFTING(AbilityScores.INT, AbilityScores.WIS),

    ANIMAL_HANDLING(AbilityScores.WIS, AbilityScores.INT),
    MEDICINE(AbilityScores.WIS, AbilityScores.INT),

    INSIGHT(AbilityScores.WIS, AbilityScores.CHA),
    PERCEPTION(AbilityScores.WIS, AbilityScores.WIS),

    DECEPTION(AbilityScores.CHA, AbilityScores.WIS),
    INTIMIDATION(AbilityScores.CHA, AbilityScores.CHA),
    PERSUASION(AbilityScores.CHA, AbilityScores.CHA);

    final AbilityScores baseAbilityScore, secondaryAbilityScore;

    PlayerSkills(AbilityScores baseAbilityScore, AbilityScores secondaryAbilityScore) {
        this.baseAbilityScore=baseAbilityScore;
        this.secondaryAbilityScore=secondaryAbilityScore;
    }

    public AbilityScores getBaseAbilityScore() { return baseAbilityScore; }
    public AbilityScores getSecondaryAbilityScore() { return secondaryAbilityScore; }

}
