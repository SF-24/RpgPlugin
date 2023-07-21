// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.item.lightsaber;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.item.PlayerHand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ExtendableWeaponManager {

    public static boolean isExtended(ItemStack itemStack) {

        if(itemStack !=null) {
            if(itemStack.getType().equals(Material.NETHERITE_SWORD) && itemStack.getItemMeta()!=null) {
                ItemMeta saberMeta = itemStack.getItemMeta();

                if(saberMeta.hasCustomModelData()) {
                    int c = saberMeta.getCustomModelData();

                    for(ExtendableWeaponList element : ExtendableWeaponList.values()) {
                        if(c == element.getExtendedID()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isExtendableWeapon(ItemStack itemStack) {
        ItemStack saber = itemStack;

        if(saber!=null) {
            if(saber.getType().equals(Material.NETHERITE_SWORD) && saber.getItemMeta()!=null) {
                ItemMeta saberMeta = saber.getItemMeta();

                if(saberMeta.hasCustomModelData()) {
                    int c = saberMeta.getCustomModelData();

                    for(ExtendableWeaponList element : ExtendableWeaponList.values()) {
                        if(c == element.getSheathedId()) {
                            return true;
                        } else if(c == element.getExtendedID()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    public static void toggleFoldingWeapon(Rpg rpg, Player player, PlayerHand hand) {
        ItemStack saber = null;

        if(hand.equals(PlayerHand.MAIN_HAND)) {
            saber = player.getInventory().getItemInMainHand();
        } else if(hand.equals(PlayerHand.OFF_HAND)) {
            saber = player.getInventory().getItemInOffHand();
        }

        if(saber!=null) {
            if(saber.getType().equals(Material.NETHERITE_SWORD) && saber.getItemMeta()!=null) {
                if (
                        ( hand.equals(PlayerHand.OFF_HAND) && !rpg.getMiscPlayerManager().isOffHandLightsaberCoolDown(player) ) ||
                        ( hand.equals(PlayerHand.MAIN_HAND) && !rpg.getMiscPlayerManager().isMainHandLightsaberCoolDown(player) )
                ) {
                    ItemMeta saberMeta = saber.getItemMeta();

                    if (saberMeta.hasCustomModelData()) {
                        int c = saberMeta.getCustomModelData();

                        for (ExtendableWeaponList element : ExtendableWeaponList.values()) {
                            if (c == element.getSheathedId()) {

                                if(saberMeta instanceof Damageable) {
                                    int damage = ((Damageable) saberMeta).getDamage();
                                    if(damage>0) {
                                        if(element.allowExtendingWhenDamaged()) {
                                            extendFoldingWeapon(rpg, player, hand);
                                        }
                                    } else {
                                        extendFoldingWeapon(rpg, player, hand);
                                    }
                                } else {
                                    extendFoldingWeapon(rpg, player, hand);
                                }
                            } else if (c == element.getExtendedID()) {
                                sheatheFoldingWeapon(rpg, player, hand);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void extendFoldingWeapon(Rpg rpg, Player player, PlayerHand hand) {

        ItemStack saber = null;

        if(hand.equals(PlayerHand.MAIN_HAND)) {
            saber = player.getInventory().getItemInMainHand();
        } else if(hand.equals(PlayerHand.OFF_HAND)) {
            saber = player.getInventory().getItemInOffHand();
        }

        if(saber!=null) {
            if(saber.getType().equals(Material.NETHERITE_SWORD) && saber.getItemMeta()!=null) {
                ItemMeta saberMeta = saber.getItemMeta();

                if(saberMeta.hasCustomModelData()) {
                    int c = saberMeta.getCustomModelData();

                    for(ExtendableWeaponList element : ExtendableWeaponList.values()) {
                        if(c == element.getSheathedId()) {
                            saberMeta.setCustomModelData(element.getExtendedID());
                            saber.setItemMeta(saberMeta);
                            if(hand.equals(PlayerHand.MAIN_HAND)) {
                                player.getInventory().setItemInMainHand(saber);
                                rpg.getMiscPlayerManager().putPlayerInMainHandLightsaberCoolDown(player);
                            } else {
                                player.getInventory().setItemInOffHand(saber);
                            }
                            if(element.isDarksaberSound()) {
                                LightsaberManager.playSound(player, LightsaberSoundList.DARKSABER_ON, element.getSoundPitch());
                            } else if(element.isLightsaberSound()) {
                                LightsaberManager.playSound(player, LightsaberSoundList.SABER_ON, element.getSoundPitch());
                            }
                        }
                    }
                }
            }
        }

    }


    public static void sheatheFoldingWeapon(Rpg rpg, Player player, PlayerHand hand) {

        ItemStack saber = null;

        if(hand.equals(PlayerHand.MAIN_HAND)) {
            saber = player.getInventory().getItemInMainHand();
        } else if(hand.equals(PlayerHand.OFF_HAND)) {
            saber = player.getInventory().getItemInOffHand();
        }

        if(saber!=null) {
            if(saber.getType().equals(Material.NETHERITE_SWORD) && saber.getItemMeta()!=null) {
                ItemMeta saberMeta = saber.getItemMeta();

                if(saberMeta.hasCustomModelData()) {
                    int c = saberMeta.getCustomModelData();

                    for(ExtendableWeaponList element : ExtendableWeaponList.values()) {
                        if(c == element.getExtendedID()) {
                            saberMeta.setCustomModelData(element.getSheathedId());
                            saber.setItemMeta(saberMeta);
                            if(hand.equals(PlayerHand.MAIN_HAND)) {
                                player.getInventory().setItemInMainHand(saber);
                                rpg.getMiscPlayerManager().putPlayerInMainHandLightsaberCoolDown(player);
                            } else {
                                player.getInventory().setItemInOffHand(saber);
                            }
                            if(element.isDarksaberSound()) {
                                LightsaberManager.playSound(player, LightsaberSoundList.DARKSABER_OFF, element.getSoundPitch());
                            } else if(element.isLightsaberSound()) {
                                LightsaberManager.playSound(player, LightsaberSoundList.SABER_OFF, element.getSoundPitch());
                            }
                        }
                    }
                }
            }
        }

    }

    public static ExtendableWeaponList getType(ItemStack saber) {
        if(saber!=null) {
            if(saber.getType().equals(Material.NETHERITE_SWORD) && saber.getItemMeta()!=null) {
                ItemMeta saberMeta = saber.getItemMeta();

                if (saberMeta.hasCustomModelData()) {
                    int c = saberMeta.getCustomModelData();

                    for (ExtendableWeaponList element : ExtendableWeaponList.values()) {
                        if (c == element.getSheathedId()) {
                            return element;

                        } else if (c == element.getExtendedID()) {
                            return element;
                        }
                    }
                }
            }
        }
        return null;
    }
}
