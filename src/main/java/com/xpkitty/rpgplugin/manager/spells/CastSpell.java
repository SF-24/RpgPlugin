package com.xpkitty.rpgplugin.manager.spells;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.spells_old.FireballSpell;
import com.xpkitty.rpgplugin.manager.spells.spells_old.HealSpell.HealSpell;
import com.xpkitty.rpgplugin.manager.spells.spells_old.SacredFlameSpell.SacredFlameSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.AlohomoraSpell.AlohomoraSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.Colloportus.ColloportusSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.FlipendoSpell.FlipendoSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.IncendioSpell.IncendioSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.LumosSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.NoxSpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.StupefySpell.StupefySpell;
import com.xpkitty.rpgplugin.manager.spells.hardcoded_hp_spells.WingardiumLeviosaSpell.WingariumLeviosaSpell;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CastSpell {

    public static void castSpell(Player player, SpellList spell, Rpg rpg, Vector dir, boolean useVector) {
        switch (spell) {
            case INCENDIO:
                new IncendioSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case FLIPENDO:
                new FlipendoSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case ALOHOMORA:
                new AlohomoraSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case COLLOPORTUS:
                new ColloportusSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case LUMOS:
                new LumosSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case NOX:
                new NoxSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case STUPEFY:
                new StupefySpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case WINGARDIUM_LEVIOSA:
                new WingariumLeviosaSpell(rpg,spell,player.getUniqueId(),dir);
                break;
            case FIREBALL:
                new FireballSpell(rpg,spell,player.getUniqueId());
                break;
            case SACRED_FLAME:
                new SacredFlameSpell(rpg,spell,player.getUniqueId());
                break;
            case HEAL:
                new HealSpell(rpg,spell,player.getUniqueId());
                break;
            default:
                player.sendMessage("NULL POINTER EXCEPTION ERROR! PLEASE CONTACT A SERVER ADMINISTRATOR.");
                return;
        }
    }
}
