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
package com.xpkitty.rpgplugin.manager.spells.spell_elements;

import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SpellTarget {
    ALL(null,true,true),
    BLOCK(null,false,true),
    ENTITY(null,true,false),
    ITEM(Collections.singletonList(EntityType.DROPPED_ITEM),false,false),
    PROJECTILE(Arrays.asList(EntityType.ARROW,EntityType.SNOWBALL,EntityType.FIREBALL,EntityType.DRAGON_FIREBALL,EntityType.TRIDENT,EntityType.EGG,EntityType.SHULKER_BULLET,EntityType.SPLASH_POTION,EntityType.FISHING_HOOK),false,false);

    final List<EntityType> entityTypes;
    final boolean affectsAllLivingEntities;
    final boolean affectsBlocks;

    SpellTarget(List<EntityType> entityTypes, boolean affectsAllLivingEntities, boolean affectsBlocks) {
        this.entityTypes=entityTypes;
        this.affectsAllLivingEntities=affectsAllLivingEntities;
        this.affectsBlocks=affectsBlocks;
    }

    public List<EntityType> getEntityTypes() { return entityTypes; }
    public boolean getAffectsAllLivingEntities() { return affectsAllLivingEntities; }
    public boolean getAffectsAllBlocks() { return affectsBlocks; }

}
