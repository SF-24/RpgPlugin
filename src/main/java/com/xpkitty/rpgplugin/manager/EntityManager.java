// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

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
