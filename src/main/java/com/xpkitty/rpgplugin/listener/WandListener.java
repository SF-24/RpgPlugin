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
package com.xpkitty.rpgplugin.listener;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.enum_list.SpellList;
import com.xpkitty.rpgplugin.manager.spells.SpellManager;
import com.xpkitty.rpgplugin.manager.spells.OldWand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class WandListener implements Listener {
    Rpg rpg;

    HashMap<UUID, SpellList> activeSpell = new HashMap<>();

    public WandListener(Rpg rpg) {
        this.rpg = rpg;
    }

    @EventHandler
    public void onCLick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(player.getInventory().getItemInMainHand().getType() != Material.AIR && player.getInventory().getItemInMainHand().getItemMeta()!=null) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equalsIgnoreCase("wand")) {
                    SpellList spell;
                    if(player.isSneaking()) {
                        spell = new SpellManager().getActiveSpellSneak(player,rpg);
                    } else {
                        spell = new SpellManager().getActiveSpell(player,rpg);
                    }


                    if(spell != null) {
                        SpellManager.castSpell(player,spell,rpg);
                    } else {
                        OldWand.sendNoSpellsMessage(player);
                    }

                }
            }

        }

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equalsIgnoreCase("wand")) {
                    if(player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equalsIgnoreCase("wand")) {
                        SpellList spell;
                        if(player.isSneaking()) {
                            spell = new SpellManager().getActiveSpellRightSneak(player,rpg);
                        } else {
                            spell = new SpellManager().getActiveSpellRight(player,rpg);
                        }

                        if(spell != null) {
                            SpellManager.castSpell(player,spell,rpg);
                        } else {
                            OldWand.sendNoSpellsMessage(player);
                        }

                    }
                }
            }

        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if(e.getView().getTitle().contains("Spell List") && !e.getInventory().getType().equals(InventoryType.CRAFTING)) {
            Player player = (Player) e.getWhoClicked();

            if(e.getCurrentItem().getType() != Material.AIR) {
                if(e.getCurrentItem().getItemMeta() != null) {
                    for(SpellList spell : SpellList.values()) {
                        if(spell.name().toUpperCase(Locale.ROOT).equalsIgnoreCase(e.getCurrentItem().getItemMeta().getLocalizedName().toUpperCase(Locale.ROOT))) {
                            e.setCancelled(true);
                            if(e.getClick().isLeftClick()) {
                                if(!e.getClick().isShiftClick()) {
                                    new SpellManager().setActiveSpell(player,rpg,spell);
                                } else {
                                    new SpellManager().setActiveSpellSneak(player,rpg,spell);
                                }
                            }
                            if(e.getClick().isRightClick()) {
                                if(!e.getClick().isShiftClick()) {
                                    new SpellManager().setActiveSpellRight(player,rpg,spell);
                                } else {
                                    new SpellManager().setActiveSpellRightSneak(player,rpg,spell);
                                }
                            }

                            player.sendMessage("Readied spell " + ChatColor.AQUA + spell.getDisplay());
                            player.closeInventory();
                        }
                    }



                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getType() != Material.AIR) {
            if(e.getItemDrop().getItemStack().getItemMeta() != null) {
                if(e.getItemDrop().getItemStack().getItemMeta().getLocalizedName().equalsIgnoreCase("WAND") && !e.getPlayer().isSneaking()) {

                    Player player=e.getPlayer();
                    e.setCancelled(true);

                    if(rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().contains("spell_list")) {
                        List<String> spells = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getStringList("spell_list");
                        OldWand wand = new OldWand();
                        wand.OpenUI(rpg,player,spells);
                    } else {
                        OldWand.sendNoSpellsMessage(player);
                        rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().createSection("spell_list");
                        try {
                            rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }


        }
    }
}
