package com.xpkitty.rpgplugin.manager.spells.wand;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;


public enum WandType {

    SLEEK(Material.MINECART, 11, false, new ArrayList<>(), 5),
    CURVED_BIRCH(Material.MINECART, 12, false, new ArrayList<>(), 5),
    CURVED_BIRCH_2(Material.MINECART, 13, false, new ArrayList<>(), 5),
    CURVED_DARK_OAK(Material.MINECART, 17, false, new ArrayList<>(), 5),
    CURVED_DARK_OAK_2(Material.MINECART, 16, false, new ArrayList<>(), 5),
    CURVED_OAK(Material.MINECART, 22, false, new ArrayList<>(), 5),
    CURVED_SANDY(Material.MINECART, 23, false, new ArrayList<>(), 5),

    ELDER(Material.MINECART, 14, true, new ArrayList<>(Collections.singleton(WandWood.ELDER)),
            10),

    BONE(Material.MINECART, 18, false, new ArrayList<>(), 5),
    WOLF(Material.MINECART, 15, false, new ArrayList<>(), 3),

    SLIM_LONG_HILT(Material.MINECART, 21, false, new ArrayList<>(), 5),
    BROWN(Material.MINECART, 20, false, new ArrayList<>(), 5),

    UMBRELLA(Material.MINECART, 19, false, new ArrayList<>(), 0);

    Material material;
    int customModelData, weight;
    boolean limitedWoodType;
    ArrayList<WandWood> wood;

    WandType(Material material, int customModelData, boolean limitedWoodTypes, ArrayList<WandWood> wood, int weight) {
        this.material = material;
        this.customModelData = customModelData;
        this.limitedWoodType = limitedWoodTypes;
        this.wood = wood;
        this.weight = weight;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean getLimitedWoodType() {
        return limitedWoodType;
    }

    public ArrayList<WandWood> getWood() {
        return wood;
    }

    public int getWeight() {
        return weight;
    }
}
