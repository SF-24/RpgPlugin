// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.UUID;

public abstract class HpSpell implements Listener {

    protected SpellList type;
    protected UUID uuid;

    public HpSpell(Rpg rpg, SpellList type, UUID uuid, Vector dir) {
        this.type = type;
        this.uuid = uuid;

        Bukkit.getPluginManager().registerEvents(this, rpg);
    }

    public UUID getUuid() { return uuid;}
    public SpellList getType() { return type; }

    public abstract void onStart(Player player, Vector dir);

    public void remove() {
        HandlerList.unregisterAll(this);
    }

}
