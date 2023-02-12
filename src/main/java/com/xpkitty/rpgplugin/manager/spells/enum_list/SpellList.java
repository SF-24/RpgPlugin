package com.xpkitty.rpgplugin.manager.spells.enum_list;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum SpellList {
    INCENDIO("Incendio","",5,
            "",new ArrayList<>(),1f,Material.FIRE),
    ALOHOMORA("Alohomora","",5,
            "",new ArrayList<>(),1f,Material.IRON_SWORD),
    COLLOPORTUS("Colloportus","",5,
            "",new ArrayList<>(),1f,Material.IRON_BARS),
    LUMOS("Lumos","",5,
            "",new ArrayList<>(),1f,Material.TORCH),
    FLIPENDO("Flipendo","",5,
            "",new ArrayList<>(),1f,Material.LEATHER_BOOTS),
    NOX("Nox","",5,
            "",new ArrayList<>(),1f,Material.BLACK_WOOL),
    STUPEFY("Stupefy","Stuns target",5,
            "Stun",new ArrayList<>(),5f,Material.SPLASH_POTION),
    WINGARDIUM_LEVIOSA("Wingardium Leviosa","Levitates target",5,
            "Levitate",new ArrayList<>(),1f,Material.FEATHER),
    FIREBALL("Fireball","Creates a fireball", 10,
            "Damage", new ArrayList<String>(Arrays.asList("Fire","Explosion")),
            1.5f,Material.FIRE_CHARGE),
    HEAL("Heal","Heals target",15,
            "heal", new ArrayList<>(Collections.singletonList("Heal")),1f,Material.GOLDEN_APPLE),
    SACRED_FLAME("Sacred Flame","Creates a flame that damages entities",15,
            "Damage",new ArrayList<>(Arrays.asList("holy","fire")),1f,Material.FLINT_AND_STEEL);

    final String display, description, effectType;
    final Integer manaCost;
    final Float power;
    final Material texture;

    SpellList(String display, String description, Integer manaCost, String effectType, ArrayList<String> types, Float Power, Material texture) {

        this.display = display;
        this.description = description;
        this.manaCost = manaCost;
        this.power = Power;
        this.effectType = effectType;
        this.texture = texture;

    }

    public Material getIcon() {return texture; };
    public String getDisplay() {return display;}
    public String getDescription() {return description;}
    public Integer getManaCost() {return manaCost;}
    public String getEffectType() {return  effectType;}
    public Float getPower() {return power;}
}
