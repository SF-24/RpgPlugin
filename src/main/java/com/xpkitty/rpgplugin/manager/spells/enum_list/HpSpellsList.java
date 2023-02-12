package com.xpkitty.rpgplugin.manager.spells.enum_list;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum HpSpellsList {
    //ALOHOMORA(Arrays.asList(2, 7, 0, 5), "Alohomora", "Unlocking Charm", "Charm", 2, 15, 25, 1, 15, Collections.emptyList(), 102, "", -11),
    //COLLOPORTUS(Arrays.asList(1,5,7,5),"Colloportus","Locking Spell","Charm",2, 15,30,1, 15, Collections.emptyList(), 103, "", -12),
    //FLIPENDO(Arrays.asList(2,0,1),"Flipendo","Knockback Jinx","Jinx",3,25,30,2, 15, Collections.singletonList(45), 104, "", -13),
    //INCENDIO(Arrays.asList(0,2,7),"Incendio","Fire-Lighting Charm","Charm",8,30,35,2, 25, Collections.singletonList(50), 106, "", -14),
    LUMOS(Arrays.asList(0,2),"Lumos","Wand-Lighting Charm","Charm",1, 15,15,1, 6, Collections.emptyList(), 107, "", -15),
    NOX(Collections.singletonList(5),"Nox","Wand-Extinguishing charm","Charm",0, 15,15,1, 5, Collections.emptyList(), 108, "", -16),
    STUPEFY(Arrays.asList(8,1),"Stupefy","Stunning Spell","Charm",4, 35,50,3, 30, Arrays.asList(65,80), 110, "", -17),
    //WINGARDIUM_LEVIOSA(Arrays.asList(2,0,5),"Wingardium Leviosa","Levitation Charm","Charm",3, 20,25,2, 12, Collections.singletonList(35), 111, "", -18)
    ;

    final String display, description,category,hotbarDisplayName;
    final Integer manaCost, incantationVolume,xpToLearn, castDifficulty, itemModelData;
    private final int maxLevel, id;
    List<Integer> wandMovement;
    List<Integer> levelingXp;

    HpSpellsList(List<Integer> wandMovement , String display, String description, String category, Integer manaCost, Integer incantationVolume,int xpToLearn,int maxLevel, int castDifficulty, List<Integer> levelingXp, int hotbarItemCustomModelData, String hotbarItemDisplayName, int id) {
        this.display = display;
        this.description = description;
        this.manaCost = manaCost;
        this.category = category;
        this.wandMovement = wandMovement;
        this.incantationVolume = incantationVolume;
        this.xpToLearn=xpToLearn;
        this.maxLevel = maxLevel;
        this.castDifficulty = castDifficulty;
        this.levelingXp = levelingXp;
        this.itemModelData=hotbarItemCustomModelData;
        this.hotbarDisplayName=hotbarItemDisplayName;
        this.id=id;
    }

    public List<Integer> getWandMovement() {return wandMovement; }
    public Integer getIncantationVolume() { return incantationVolume; }
    public String getDisplay() {return display;}
    public String getDescription() {return description;}
    public Integer getManaCost() {return manaCost;}
    public String getCategory() {return category;}
    public Integer getXpToLearn() {return xpToLearn;}
    public Integer getMaximumLevel() {return maxLevel;}
    public Integer getCastDifficulty() { return castDifficulty; }
    public List<Integer> getLevelingXp() {return levelingXp;}
    public Integer getItemModelData() {return itemModelData;}
    public String getHotBarDisplayName() {return hotbarDisplayName;}

    public int getNumericId() { return id; }
}
