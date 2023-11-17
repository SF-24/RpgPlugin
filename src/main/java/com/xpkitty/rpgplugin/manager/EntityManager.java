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
package com.xpkitty.rpgplugin.manager;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class EntityManager {

    public static boolean isUndead(EntityType entityType) {

        ArrayList<EntityType> undead = new ArrayList<>();
        undead.add(EntityType.ZOMBIE);
        undead.add(EntityType.SKELETON);
        undead.add(EntityType.WITHER_SKELETON);
        undead.add(EntityType.WITHER);
        undead.add(EntityType.STRAY);
        undead.add(EntityType.ZOMBIE_HORSE);
        undead.add(EntityType.ZOMBIE_VILLAGER);
        undead.add(EntityType.ZOMBIFIED_PIGLIN);
        undead.add(EntityType.SKELETON_HORSE);
        undead.add(EntityType.ZOGLIN);
        undead.add(EntityType.PHANTOM);
        undead.add(EntityType.HUSK);

        if(undead.contains(entityType)) {
            return true;
        }

        return false;
    }




}
