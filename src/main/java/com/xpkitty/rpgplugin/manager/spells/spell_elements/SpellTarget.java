// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
