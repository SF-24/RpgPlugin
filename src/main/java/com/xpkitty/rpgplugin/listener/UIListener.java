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
import com.xpkitty.rpgplugin.manager.AbilityScores;
import com.xpkitty.rpgplugin.manager.MiscPlayerManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.StringManager;
import com.xpkitty.rpgplugin.manager.UIManager;
import com.xpkitty.rpgplugin.manager.player_class.ArmourList;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.ClassManager;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import com.xpkitty.rpgplugin.manager.skin.ArmourSelectedSkins;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.HotbarType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UIListener implements Listener {

    private final Rpg rpg;

    public UIListener(Rpg rpg) {
        this.rpg = rpg;
    }

    // Inventory Click Event
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCursor();

        if(rpg.getSpellHotbarManager().getCurrentHotbar(player).equals(HotbarType.SPELL_HOTBAR)) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                if(e.getCurrentItem().getItemMeta().getLocalizedName().contains("spell_icon") || e.getCurrentItem().getItemMeta().getLocalizedName().contains("HP_WAND")) {
                    e.setCancelled(true);
                }
            }
        }


        if(e.getView().getTitle().contains("tempClear")) {
            e.setCancelled(true);
        }

        if(item.getItemMeta() != null) {

            if (item.getItemMeta().getLocalizedName().contains("hp_wand") && e.getInventory().getType().equals(InventoryType.CRAFTING) && e.getSlot() == 40) {
                e.setCancelled(true);
            }
        }




        //ARMOR EQUIP/UNEQUIP
        if (e.getInventory().getType().equals(InventoryType.CRAFTING) && (e.getSlot() == 36 || e.getSlot() == 37 || e.getSlot() == 38 ||e.getSlot() == 39)) {
            boolean isCanceled = false;

            if(item.getItemMeta() != null) {
                ItemMeta itemMeta = item.getItemMeta();
                if(itemMeta.getLore()!=null) {
                    String armourType = (itemMeta.getLore()).get(0);
                    String armourTypeTranslated = (ChatColor.translateAlternateColorCodes('&',armourType));

                    int STR = MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.STR);
                    int CON = MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.CON);
                    int DEX = MiscPlayerManager.getAbilityScore(rpg,player, AbilityScores.DEX);

                    System.out.println("DEBUG: " + player.getName() + " STR, DEX, CON= " + STR + " " + DEX + " " + CON + " ; for " + player.getName());

                    if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Medium Armour")) {
                        if(!(STR>= ArmourList.MEDIUM_ARMOUR.getStr() && CON>=ArmourList.MEDIUM_ARMOUR.getCon() && DEX>=ArmourList.MEDIUM_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                            isCanceled=true;
                        }
                    } else if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Heavy Armour")) {
                        if(!(STR>=ArmourList.HEAVY_ARMOUR.getStr() && CON>=ArmourList.HEAVY_ARMOUR.getCon() && DEX>=ArmourList.HEAVY_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                            isCanceled=true;
                        }
                    } else if(armourTypeTranslated.equalsIgnoreCase(ChatColor.GRAY +"Light Armour")) {
                        if(!(STR>=ArmourList.LIGHT_ARMOUR.getStr() && CON>=ArmourList.LIGHT_ARMOUR.getCon() && DEX>=ArmourList.LIGHT_ARMOUR.getDex())) {
                            player.sendMessage(ChatColor.RED + "You currently cannot equip this armour");
                            e.setCancelled(true);
                            isCanceled=true;
                        }
                    }
                }
            }

            if(!isCanceled) {
                if(item.getItemMeta()!=null) {
                    if(item.getItemMeta().hasCustomModelData() && item.getType().equals(Material.LEATHER_CHESTPLATE)) {
                        for(ArmourSelectedSkins element : ArmourSelectedSkins.values()) {
                            if(element.getModelData() == item.getItemMeta().getCustomModelData()) {
                                LeatherArmorMeta itemMeta = (LeatherArmorMeta) item.getItemMeta();
                                itemMeta.setColor(element.getColor());
                                item.setItemMeta(itemMeta);
                                e.setCursor(item);
                            }
                        }
                    }
                }

            }
        }






        if(ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.BLACK + "Menu") && e.getClickedInventory()!=null) {
            if(e.getInventory().getHolder() == null) {
                e.setCancelled(true);

                if(e.getCurrentItem()!=null) {
                    Material clickedItem = e.getCurrentItem().getType();
                    String name = "";

                    if(e.getCurrentItem().getItemMeta()!=null) {
                        name = e.getCurrentItem().getItemMeta().getDisplayName();
                    }

                    if(e.getCurrentItem().getItemMeta().getLocalizedName().contains("open_spell_menu")) {
                        if (e.isLeftClick()) {
                            UIManager.openSpellMenu(player, rpg, true);
                        } else {
                            UIManager.openSpellMenu(player, rpg, false);
                        }
                    }

                    if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',ChatColor.AQUA+"Class Menu"))) {
                        UIManager.openClassMenu(player,rpg);
                    }

                    switch (clickedItem) {
                        case EMERALD:
                        case PEONY:
                            e.setCancelled(true);
                            break;
                        case PLAYER_HEAD:
                            e.setCancelled(true);
                            UIManager.openAbilityScoresMenu(player,rpg);
                            break;
                        case ENCHANTED_BOOK:
                        case KNOWLEDGE_BOOK:
                        case NETHER_STAR:
                            if(name.equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Abilities")) {
                                if(MiscPlayerManager.getAbilities(player,rpg).size()>=1) {
                                    UIManager.openAbilitiesMenu(player,rpg);
                                }
                            }
                            break;
                        case WOODEN_HOE:
                            e.setCancelled(true);

                            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',ChatColor.AQUA+"Class Menu"))) {
                                UIManager.openClassMenu(player,rpg);
                            }
                            break;
                        default:
                            return;
                    }
                }
            }
        } else if(e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',ChatColor.WHITE+"Class Menu"))) {


            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
                if(MiscPlayerManager.getPlayerClass(player,rpg) == null) {
                    for(ClassList classList : ClassList.values()) {
                        String localizedName = e.getCurrentItem().getItemMeta().getLocalizedName();
                        if(localizedName.equalsIgnoreCase(classList.name())) {
                            ClassManager.setClass(rpg,player,classList);
                            UIManager.openClassMenu(player,rpg);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You have already chosen a class");
                }

            }
            e.setCancelled(true);

        } else if(e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',ChatColor.BLACK + "Abilities"))) {

            if(e.getCurrentItem() != null) {
                for(AbilityType ability :  AbilityType.values()) {
                    if(ability.getTexture().equals(e.getCurrentItem().getType())) {
                        if(e.getCurrentItem().getItemMeta() != null) {
                            if(e.getCurrentItem().getItemMeta().getCustomModelData() == ability.getModelData()) {
                                e.setCancelled(true);
                                UIManager.openAbilityCustomizeMenu(player,rpg,ability);
                            }
                        }
                    }
                }
            }

        } else if(e.getView().getTitle().contains("skill points remaining")) {
            if(e.getInventory().getHolder() == null) {
                e.setCancelled(true);

                if(e.getCurrentItem()!=null) {
                    Material clickedItem = e.getCurrentItem().getType();

                    switch (clickedItem) {
                        case BOOK:
                        case KNOWLEDGE_BOOK:
                            ItemStack stack = e.getCurrentItem();

                            PlayerDataManager playerDataManager = rpg.getConnectListener().getPlayerDataInstance(player);
                            File file = playerDataManager.getYamlFile();
                            YamlConfiguration modifyFile = playerDataManager.getModifyYaml();

                            int skillPoints = modifyFile.getInt("skill_points");
                            String path = "ability_scores."; String path2 = ".value";
                            int STR = modifyFile.getInt(path + "STR" + path2);
                            int DEX = modifyFile.getInt(path + "DEX" + path2);
                            int CON = modifyFile.getInt(path + "CON" + path2);
                            int INT = modifyFile.getInt(path + "INT" + path2);
                            int WIS = modifyFile.getInt(path + "WIS" + path2);
                            int CHA = modifyFile.getInt(path + "CHA" + path2);

                            String path4 = "ability_scores.";
                            String path5 = ".value";
                            String path6 = ".modifier";
                            int level = modifyFile.getInt("level");
                            int abilityCap = MiscPlayerManager.calculateAbilityScoreCaps(level, rpg);

                            for(int i = level; i == 0; i--) {
                                if(rpg.getConfigManager().getConfiguration().contains("ability-score-caps." + i)) {
                                    abilityCap = rpg.getConfigManager().getConfiguration().getInt("ability-score-caps." + i);
                                    break;
                                }
                            }

                            e.setCancelled(true);
                            switch (stack.getItemMeta().getLocalizedName()) {

                                case "str":
                                    if(STR<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            STR++;
                                            int strMod = MiscPlayerManager.calculateAbilityScoreModifier(STR);
                                            if(strMod==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"STR"+path5, STR);
                                            modifyFile.set(path4+"STR"+path6, strMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                case "dex":
                                    if(DEX<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            DEX++;
                                            int dexMod = MiscPlayerManager.calculateAbilityScoreModifier(DEX);
                                            if(dexMod==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"DEX"+path5, DEX);
                                            modifyFile.set(path4+"DEX"+path6, dexMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                case "con":
                                    if(CON<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            CON++;
                                            int conMod = MiscPlayerManager.calculateAbilityScoreModifier(CON);
                                            if(conMod==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"CON"+path5, CON);
                                            modifyFile.set(path4+"CON"+path6, conMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                case "int":
                                    if(INT<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            INT++;
                                            int intMod = MiscPlayerManager.calculateAbilityScoreModifier(INT);
                                            if(INT==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"INT"+path5, INT);
                                            modifyFile.set(path4+"INT"+path6, intMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                case "wis":
                                    if(WIS<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            WIS++;
                                            int wisMod = MiscPlayerManager.calculateAbilityScoreModifier(WIS);
                                            if(wisMod==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"WIS"+path5, WIS);
                                            modifyFile.set(path4+"WIS"+path6, wisMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                case "cha":
                                    if(CHA<abilityCap) {
                                        if(skillPoints > 0) {
                                            skillPoints--;
                                            CHA++;
                                            int chaMod = MiscPlayerManager.calculateAbilityScoreModifier(CHA);
                                            if(chaMod==-100) {
                                                StringManager.debugMessage(player,"ERROR! Could not calculate modifier");
                                            }
                                            modifyFile.set("skill_points",skillPoints);
                                            modifyFile.set(path4+"CHA"+path5, CHA);
                                            modifyFile.set(path4+"CHA"+path6, chaMod);
                                        } else {
                                            player.sendMessage("You do not have enough skill points");
                                        }
                                    }
                                    break;
                                default:
                                    return;
                            }
                            try {
                                modifyFile.save(file);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            UIManager.openAbilityScoresMenu(player,rpg);
                        case PLAYER_HEAD:
                            e.setCancelled(true);


                        default:
                            return;
                    }
                }
            }
        } else if(e.getView().getTitle().contains("mainSpellMenu")) {

            if(e.getCurrentItem()!=null) {
                if (e.getCurrentItem().getType().equals(Material.PEONY) && e.getCurrentItem().getItemMeta() != null) {
                    String check = e.getCurrentItem().getItemMeta().getLocalizedName();

                    int num = 0;

                    try {
                        num = Integer.parseInt(check);

                    } catch (NumberFormatException ex) {
                        //string is not an integer
                        player.sendMessage("check result:" + check);
                        player.sendMessage("ERROR numberFormatException");
                    }

                    if (num < -10 || num > -1) {
                        UIManager.openSingleSpellMenu(rpg, player, num);
                    }
                }
            }

        } else if(e.getView().getTitle().contains("localSpellMenu")) {


            if(e.getCurrentItem()!=null) {
                ItemStack itemStack = e.getCurrentItem();

                if(itemStack.getItemMeta()!=null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    String localizedName = itemMeta.getLocalizedName();

                    if(localizedName.equalsIgnoreCase("refresh")) {
                        int id = Integer.parseInt(e.getView().getTitle().substring(23));


                        rpg.getConnectListener().getPlayerSpellFile(player).setSlot(id,0,0);

                        UIManager.openSingleSpellMenu(rpg,player,id);
                    }

                    if(localizedName.contains("plus")) {
                        int id = Integer.parseInt(e.getView().getTitle().substring(23));
                        int slot = Integer.parseInt(localizedName.substring(4));
                        slot--;


                        rpg.getConnectListener().getPlayerSpellFile(player).setSlot(id,slot,rpg.getConnectListener().getPlayerSpellFile(player).getEditHotbar());
                    
                        UIManager.openSingleSpellMenu(rpg,player,id);
                    }

                    int id = Integer.parseInt(e.getView().getTitle().substring(23));

                    if(localizedName.contains("arrowDown")) {
                        rpg.getConnectListener().getPlayerSpellFile(player).subtractOneFromEditHotBar();
                        UIManager.openSingleSpellMenu(rpg,player,id);

                    }
                    if(localizedName.contains("arrowUp")) {
                        rpg.getConnectListener().getPlayerSpellFile(player).addOneToEditHotBar();
                        UIManager.openSingleSpellMenu(rpg,player,id);

                    }
                    if(localizedName.contains("tick")) {
                        UIManager.openSpellMenu(player,rpg,true);
                    }
                }
            }

        } else {
            for(AbilityType ability : AbilityType.values()) {
                if(e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&',ChatColor.BLACK + "Customize " + ability.getName()))) {

                    e.setCancelled(true);

                    if(e.getCurrentItem()!=null) {
                        Material texture = e.getCurrentItem().getType();
                        int modelData = e.getCurrentItem().getItemMeta().getCustomModelData();

                        if (texture.equals(Material.PEONY)) {
                            switch (modelData) {
                                case 26:
                                    // ADD CLICK
                                    if (e.getClick().isLeftClick()) {
                                        rpg.getClickManager().addSetLeftClick(player);
                                        UIManager.openAbilityCustomizeMenu(player, rpg, ability);
                                    } else if (e.getClick().isRightClick()) {
                                        rpg.getClickManager().addSetRightClick(player);
                                        UIManager.openAbilityCustomizeMenu(player, rpg, ability);
                                    }
                                    break;
                                case 25:
                                    // RESET COMBO - REFRESH
                                    rpg.getClickManager().resetSetClicks(player);
                                    UIManager.openAbilityCustomizeMenu(player, rpg, ability);
                                    break;
                                case 23:
                                    // CONFIRM
                                    boolean closeMenu = rpg.getClickManager().saveSetClicks(player, ability);
                                    if(closeMenu) {
                                        UIManager.openMenu(player, rpg);
                                        rpg.getClickManager().resetSetClicks(player);
                                    }

                                    break;
                            }
                        }
                    }
                }
            }
        }


    }




}
