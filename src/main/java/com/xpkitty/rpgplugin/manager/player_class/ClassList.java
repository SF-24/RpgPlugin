// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class;

import org.bukkit.Material;

public enum ClassList {

    WARRIOR("Warrior","",ClassType.BASE_CLASS, Material.NETHERITE_SWORD,0),
    RANGER("Ranger","",ClassType.BASE_CLASS, Material.BOW,2),
    WIZARD("Wizard","",ClassType.BASE_CLASS, Material.STICK,1),
    ROGUE("Burglar","",ClassType.BASE_CLASS, Material.IRON_SWORD,1),
    CLERIC("Cleric","",ClassType.BASE_CLASS, Material.GOLDEN_APPLE,0),
    BERSERKER("Berserker","",ClassType.BASE_CLASS, Material.IRON_AXE,1),

    HP_WIZARD("Wizard","Yer a wizard Harry", ClassType.SPECIAL_CLASS, Material.STICK,12);

    final String display, description;
    final Material texture;
    final int customModelData;

    ClassList(String display, String description, ClassType classType ,Material texture, int customModelData) {
        this.display=display;
        this.description=description;
        this.texture=texture;
        this.customModelData=customModelData;
    }

    public String getDisplay() {return display;}
    public String getDescription() {return description; }
    public Material getTexture() {return texture;}
    public int getCustomModelData() {return customModelData;}
}
