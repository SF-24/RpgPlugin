package com.xpkitty.rpgplugin.listener;

import com.google.common.cache.Cache;
import com.jyckos.speechreceiver.events.VoiceEvent;
import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.CastSpell;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.Locale;
import java.util.UUID;

public class SpeechListener implements Listener {

    Rpg rpg;
    public SpeechListener(Rpg rpg) {
        this.rpg=rpg;
    }


    @EventHandler
    public void onSpeech(VoiceEvent e) {
        Player player = e.getPlayer();

        System.out.println("[AnotherRpgPlugin] " + player.getDisplayName() + " said sentence: (" + e.getSentence() + ")");

        Cache<UUID, Long> spellCooldown = rpg.getMiscPlayerManager().getSpellCooldown();

        for(HpSpellsList spell : HpSpellsList.values()) {
            if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("hp_wand")) {
                    if(e.getSentence().startsWith(spell.getDisplay().toLowerCase(Locale.ROOT))) {
                        if(!spellCooldown.asMap().containsKey(player.getUniqueId())) {
                            rpg.getMiscPlayerManager().spellCooldown.put(player.getUniqueId(),System.currentTimeMillis() + 500);

                            Vector newDir = player.getLocation().getDirection().normalize();

                            //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§l" + spell.getDisplay()));

                            for(SpellList element : SpellList.values()) {
                                if(element.name().equalsIgnoreCase(spell.name())) {
                                    CastSpell.castSpell(player,element,rpg,newDir,true);
                                }
                            }

                            for(Player player2 : Bukkit.getOnlinePlayers()) {
                                if(player.getLocation().distance(player2.getLocation()) <= spell.getIncantationVolume()) {
                                    player2.sendMessage("<" + player.getDisplayName() + "> " + spell.getDisplay());
                                }
                            }
                        }
                    }
                }

            }
        }

    }

}
