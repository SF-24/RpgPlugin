package com.xpkitty.rpgplugin.manager.spells.shield;

public class Shield {

    int health;
    int armour;
    ShieldType type;

    public Shield(int health, int armour, ShieldType type, ShieldCategory category) {
        this.health=health;
        this.armour=armour;
        this.type=type;
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


}
