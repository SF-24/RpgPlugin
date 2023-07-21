// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.WingardiumLeviosaSpell;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.HpSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class WingariumLeviosaSpell extends HpSpell {

    Rpg rpg;
    SpellList type;
    UUID uuid;

    public WingariumLeviosaSpell(Rpg rpg, SpellList type, UUID uuid, Vector dir) {
        super(rpg, type, uuid, dir);
        this.rpg = rpg;
        this.type=type;
        this.uuid=uuid;
        onStart(Bukkit.getPlayer(uuid), dir);
    }

    @Override
    public void onStart(Player player, Vector dir) {
        WingardiumLeviosaRunnable wingardiumLeviosaRunnable = new WingardiumLeviosaRunnable(rpg,player,dir);
        wingardiumLeviosaRunnable.start();
    }
}
