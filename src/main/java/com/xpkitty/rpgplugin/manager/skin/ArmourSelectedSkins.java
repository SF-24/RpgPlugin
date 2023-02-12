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
