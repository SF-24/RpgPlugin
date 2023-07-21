package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.UIManager;
import com.xpkitty.rpgplugin.manager.item.PlayerHand;
import com.xpkitty.rpgplugin.manager.item.lightsaber.ExtendableWeaponManager;
import com.xpkitty.rpgplugin.manager.item.lightsaber.LightsaberManager;
import eu.asangarin.arikeys.api.AriKeyPressEvent;
import eu.asangarin.arikeys.api.AriKeyReleaseEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KeyListener implements Listener {
    Rpg rpg;

    public KeyListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onKey(AriKeyPressEvent e) {
        if(e.getId().equals(NamespacedKey.fromString("key:menu"))) {
            e.getPlayer().closeInventory();
            UIManager.openMenu(e.getPlayer(), rpg);
        } else if(e.getId().equals(NamespacedKey.fromString("key:ability_menu"))) {
            e.getPlayer().closeInventory();
            UIManager.openAbilitiesMenu(e.getPlayer(), rpg);
        } else if(e.getId().equals(NamespacedKey.fromString("key:spell_menu"))) {
            e.getPlayer().closeInventory();
            UIManager.openSpellMenu(e.getPlayer(), rpg, true);
        }

        boolean isAbilityKey = e.getId().equals(NamespacedKey.fromString("ab_key:ab_left")) || e.getId().equals(NamespacedKey.fromString("ab_key:ab_right"));

        boolean isLeft = e.getId().equals(NamespacedKey.fromString("ab_key:ab_left"));
        boolean isRight = e.getId().equals(NamespacedKey.fromString("ab_key:ab_right"));

        Player player = e.getPlayer();

        boolean isSaber = LightsaberManager.isLightsaber(player.getInventory().getItemInMainHand());
        boolean isWand = false;
        boolean isExtendableWeapon = ExtendableWeaponManager.isExtendableWeapon(player.getInventory().getItemInMainHand());

        if(isExtendableWeapon && !MiscPlayerManager.getLightsaberRightClickToggleSetting(rpg,player) && isLeft) {
            ExtendableWeaponManager.toggleFoldingWeapon(rpg, player, PlayerHand.MAIN_HAND);
        }
        if(isSaber && !MiscPlayerManager.getLightsaberRightClickToggleSetting(rpg,player) && isLeft) {
            LightsaberManager.toggleLightsaber(rpg,player,PlayerHand.MAIN_HAND);
        }

        if(player.getInventory().getItemInMainHand().getItemMeta() != null) {
            if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("HP_WAND")) {
                isWand = true;
            }
        }

        if(isLeft && isWand) {
            rpg.getSpellHotbarManager().hotBarDown(player);
        } else if(isRight && isWand) {
            rpg.getSpellHotbarManager().hotBarUp(player);
        }

        if(!MiscPlayerManager.getSneakAbilitySetting(rpg,player) && isAbilityKey && !isWand && !isExtendableWeapon && !isSaber) {
            if (isRight) {
                if(player.getInventory().getItemInMainHand().getItemMeta()!=null) {
                    if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("wand")) {

                    } else {
                        rpg.getClickManager().rightClick(player);
                    }
                } else {
                    rpg.getClickManager().rightClick(player);
                }
            }
            if (isLeft) {
                if(player.getInventory().getItemInMainHand().getItemMeta()!=null) {
                    if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().contains("wand")) {

                    } else {
                        rpg.getClickManager().leftClick(player);
                    }

                } else {
                    rpg.getClickManager().leftClick(player);
                }
            }
        }
    }



    @EventHandler
    public void onKeyLetGo(AriKeyReleaseEvent e) {
        if(e.getId().equals(NamespacedKey.fromString("key:menu"))) {
            UIManager.openMenu(e.getPlayer(), rpg);
        } else if(e.getId().equals(NamespacedKey.fromString("key:ability_menu"))) {
            UIManager.openAbilitiesMenu(e.getPlayer(), rpg);
        }
    }
}
