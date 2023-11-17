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
