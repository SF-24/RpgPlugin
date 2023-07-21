package com.xpkitty.rpgplugin.manager.spells.shield;

import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;

public class Shield {

    private final SpellFunction function;
    int health;
    int armour;
    ShieldType type;

    public Shield(int health, int armour, ShieldType type, ShieldCategory category, SpellFunction function) {
        this.health=health;
        this.armour=armour;
        this.type=type;
        this.function=function;
    }

    public int getHealth() {return health;}
    public int getArmour() {return armour;}
    public ShieldType getType() {return type;}
    public void setHealth(int health) {this.health=health;}
    public void setArmour(int armour) {this.armour=armour;}


    public boolean damageShield(int amount) {
        health-=amount;
        return amount > 0;
    }


    public SpellFunction getSpellFunction() {
        return function;
    }
}
