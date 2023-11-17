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
package com.xpkitty.rpgplugin.manager.skin;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSkinFile;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;

public class ArmourSkinManager {


    public static HashMap<SkinChangeStatus, ItemStack> TestArmourSkin(Rpg rpg, Player player, ItemStack equippedItem, Trigger trigger) {

        HashMap<SkinChangeStatus, ItemStack> map = new HashMap<>();

        PlayerSkinFile skinFile = rpg.getConnectListener().getPlayerSkinInstance(player);
        ItemStack newItem = equippedItem;
        LeatherArmorMeta newItemMeta = null;

        boolean cancelDefaultSkin = false;
        SkinChangeStatus hasChangedSkin = SkinChangeStatus.SKIN_UNCHANGED;

        if(skinFile==null) {
            map.put(SkinChangeStatus.SKIN_UNCHANGED,null);
            return map;
        }

        if(skinFile.verifySkin("default")) {
            if (equippedItem != null) {
                newItemMeta = (LeatherArmorMeta) equippedItem.getItemMeta();

                if (equippedItem.getType().equals(Material.LEATHER_CHESTPLATE) && equippedItem.getItemMeta()!=null) {
                    if(equippedItem.getItemMeta().hasCustomModelData()) {

                        for(ArmourSelectedSkins element : ArmourSelectedSkins.values()) {

                            if(element.getModelData() == equippedItem.getItemMeta().getCustomModelData()) {
                                newItemMeta.addItemFlags(ItemFlag.HIDE_DYE);

                                if(skinFile.verifySkin(element.getId())) {
                                    newItemMeta.setColor(Color.fromRGB(254,254,254));

                                    if(trigger.equals(Trigger.ARMOR_TASK_TICK) || trigger.equals(Trigger.CLOSE_INVENTORY)) {
                                        cancelDefaultSkin=true;

                                        if(!skinFile.getActiveSkin().equalsIgnoreCase(element.getId())) {
                                            newItem.setItemMeta(newItemMeta);
                                            boolean value = skinFile.activateSkin(element.getId());
                                            if(value) {
                                                hasChangedSkin = SkinChangeStatus.SKIN_SET_NEW;
                                            }

                                            if(hasChangedSkin.equals(SkinChangeStatus.SKIN_UNCHANGED)) {
                                                player.sendMessage(ChatColor.RED + "Could not update player skin");
                                            }
                                        }
                                    }
                                } else {
                                    newItemMeta.setColor(element.getColor());
                                }
                            }
                        }
                    }
                }
            }

            if(hasChangedSkin.equals(SkinChangeStatus.SKIN_UNCHANGED) && !cancelDefaultSkin) {
                if(!skinFile.getActiveSkin().equalsIgnoreCase("default")) {
                    if(skinFile.verifySkin("default")) {
                        boolean val = skinFile.activateSkin("default");
                        if(val) {
                            hasChangedSkin = SkinChangeStatus.SKIN_SET_DEFAULT;
                        }
                    }
                }
            }
        }

        if(newItemMeta!=null) {
            newItem.setItemMeta(newItemMeta);
        } else {
            newItem=null;
        }

        if(equippedItem != null) {
            if(!equippedItem.getType().equals(Material.LEATHER_CHESTPLATE)) {
                newItem=null;
            }
        }

        map.put(hasChangedSkin, newItem);
        return map;
    }
}
