package com.xpkitty.rpgplugin.manager.spells.shield;

import com.xpkitty.rpgplugin.manager.damage.DamageType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ShieldCategory {

    FIRE(Arrays.asList(DamageType.MAGIC_UNFORGIVABLE,
            DamageType.MAGIC_FORCE,
            DamageType.MAGIC_CURSE,
            DamageType.LIGHTNING,
            DamageType.PHYSICAL_SLASHING,
            DamageType.PHYSICAL_PIERCING,
            DamageType.PHYSICAL_BLUDGEONING),
            Arrays.asList(DamageType.FORCE_WATER,DamageType.FORCE_WIND),
            Arrays.asList(DamageType.HEAT,DamageType.HEAT_FIRE, DamageType.COLD)),
    STANDARD(Collections.singletonList(DamageType.MAGIC_UNFORGIVABLE),
            Arrays.asList(DamageType.MAGIC_CURSE,DamageType.HEAT_FIRE),
            Arrays.asList(DamageType.MAGIC_SPARKS,DamageType.FORCE_WIND));

    private final List<DamageType> passthrough;
    private final List<DamageType> weakness;
    private final List<DamageType> strongAgainst;

    ShieldCategory(List<DamageType> passthrough, List<DamageType> weakness, List<DamageType> strongAgainst) {
        this.passthrough = passthrough;
        this.weakness = weakness;
        this.strongAgainst = strongAgainst;
    }

    public List<DamageType> getPassthrough() {
        return passthrough;
    }

    public List<DamageType> getStrongAgainst() {
        return strongAgainst;
    }

    public List<DamageType> getWeakness() {
        return weakness;
    }
}
