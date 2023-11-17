/*
 *     Copyright (C) 2023 Sebastian Frynas
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Contact: sebastian.frynas@outlook.com
 *
 */
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
