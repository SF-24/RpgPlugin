// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item.lightsaber;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.item.PlayerHand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LightsaberManager {

    public static LightsaberList getLightsaberType(int modelData) {
        for(LightsaberList element : LightsaberList.values()) {
            if(element.getSheathedId()==modelData || element.getBlueId()==modelData || element.getGreenId()==modelData || element.getPurpleId()==modelData || element.getRedId()==modelData) {
                return element;
            }
        }
        return null;
    }

    public static boolean isExtended(ItemStack item) {
        if(isLightsaber(item)) {
            for(LightsaberList lightsaberList : LightsaberList.values()) {
                if(item.getItemMeta().getCustomModelData() == lightsaberList.getSheathedId()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isLightsaber(ItemStack item) {

        if(item.getType().equals(Material.NETHERITE_SWORD) && item.getItemMeta()!=null) {
            if(item.getItemMeta().hasCustomModelData()) {
                if (item.getItemMeta().getLocalizedName().contains("LIGHTSABER")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void toggleLightsaber(Rpg rpg, Player player, PlayerHand hand) {

        if((!rpg.getMiscPlayerManager().isMainHandLightsaberCoolDown(player) && hand.equals(PlayerHand.MAIN_HAND) || (!rpg.getMiscPlayerManager().isOffHandLightsaberCoolDown(player) && hand.equals(PlayerHand.OFF_HAND)))) {
            ItemStack saber;

            if (hand.equals(PlayerHand.MAIN_HAND)) {
                saber = player.getInventory().getItemInMainHand();
            } else {
                saber = player.getInventory().getItemInOffHand();
            }

            if (isLightsaber(saber)) {
                if (!isExtended(saber)) {
                    // IS SHEATHED
                    extendLightsaber(rpg,player,hand);
                } else {
                    // IS NOT SHEATHED
                    sheatheLightsaber(rpg,player,hand);
                }
            }


        }
    }



    public static void sheatheLightsaber(Rpg rpg, Player player, PlayerHand hand) {

        ItemStack saber;
        if(hand.equals(PlayerHand.MAIN_HAND)) {
            saber=player.getInventory().getItemInMainHand();
        } else {
            saber = player.getInventory().getItemInOffHand();
        }
        ItemMeta saberMeta = saber.getItemMeta();

        int id = Integer.parseInt(saberMeta.getLocalizedName().substring(10, 13));
        saberMeta.setCustomModelData(id);

        saber.setItemMeta(saberMeta);

        playSound(player,LightsaberSoundList.SABER_OFF,1.0f);

        if(hand.equals(PlayerHand.MAIN_HAND)) {
            player.getInventory().getItemInMainHand().setItemMeta(saberMeta);
        } else {
            player.getInventory().getItemInOffHand().setItemMeta(saberMeta);
        }
    }




    public static void extendLightsaber(Rpg rpg, Player player, PlayerHand hand) {
        if(isLightsaber(player.getInventory().getItemInMainHand()) && !isExtended(player.getInventory().getItemInMainHand())) {
            ItemStack saber;

            playSound(player,LightsaberSoundList.SABER_ON,1.0f);

            if(hand.equals(PlayerHand.MAIN_HAND)) {
                saber = player.getInventory().getItemInMainHand();
            } else {
                saber = player.getInventory().getItemInOffHand();
            }
            int modelData = saber.getItemMeta().getCustomModelData();
            ItemMeta saberMeta = saber.getItemMeta();

            for (LightsaberList lightsaberList : LightsaberList.values()) {

                if (modelData == lightsaberList.getSheathedId()) {
                    String colour = saber.getItemMeta().getLocalizedName().substring(13, 16);


                    for (LightsaberColour lightsaberColour : LightsaberColour.values()) {
                        if (colour.equalsIgnoreCase(lightsaberColour.name().substring(0, 3))) {
                            switch (lightsaberColour) {
                                case RED:
                                    saberMeta.setCustomModelData(lightsaberList.getRedId());
                                    break;
                                case GREEN:
                                    saberMeta.setCustomModelData(lightsaberList.getGreenId());
                                    break;
                                case BLUE:
                                    saberMeta.setCustomModelData(lightsaberList.getBlueId());
                                    break;
                                case PURPLE:
                                    saberMeta.setCustomModelData(lightsaberList.getPurpleId());
                                    break;
                                default:
                                    return;
                            }
                        }
                    }
                }

            }
            saber.setItemMeta(saberMeta);

            if(hand.equals(PlayerHand.MAIN_HAND)) {
                player.getInventory().setItemInMainHand(saber);//.setItemMeta(saberMeta);
            } else {
                player.getInventory().getItemInOffHand().setItemMeta(saberMeta);
            }
        }
    }



    public static void playSound(Player player, LightsaberSoundList sound, float pitch) {
        player.getWorld().playSound(player.getLocation(), sound.getSoundId(), sound.getVolume(), pitch);
    }

}
