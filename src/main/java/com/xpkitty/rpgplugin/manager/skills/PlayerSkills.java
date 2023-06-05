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
