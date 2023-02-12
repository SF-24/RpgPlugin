package com.xpkitty.rpgplugin.manager.spells.spell_elements;

public enum SpellFunction {

    BLOCK_PLACE("blockType", "count", ""),
    EXPLOSION("explosionType", "explosionStrength", ""),
    ENTITY_DAMAGE("damage", "", ""),
    ENTITY_POTION_EFFECT("effect","effectTimeLength","amplifier"),
    ENTITY_VECTOR("x","y","z"),
    ENTITY_STUN("stunTime","setCrawl","damage"),
    ENTITY_DISARM("disarmStrengthBase","isKnockBack",""),
    ENTITY_KNOCKBACK("strength","isLowerY",""),
    DOOR_OPEN("","",""),
    DOOR_CLOSE("","",""),
    SET_FIRE("isSoulFire","entity_fire_time",""),
    SET_WATER("","","");

    final String val1, val2, val3;

    SpellFunction(String val1, String val2, String val3) {
        this.val1=val1;
        this.val2=val2;
        this.val3=val3;
    }

    public String getVal1() { return val1; }
    public String getVal2() { return val2; }
    public String getVal3() { return val3; }
}
