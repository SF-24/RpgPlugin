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

