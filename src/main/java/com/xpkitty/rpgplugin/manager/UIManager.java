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
package com.xpkitty.rpgplugin.manager;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.listener.ConnectListener;
import com.xpkitty.rpgplugin.manager.data.new_player_data.NewDataReader;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerDataManager;
import com.xpkitty.rpgplugin.manager.data.player_data.PlayerSpellFile;
import com.xpkitty.rpgplugin.manager.data.spell_data.MainSpellData;
import com.xpkitty.rpgplugin.manager.player_class.ClassList;
import com.xpkitty.rpgplugin.manager.player_class.abilities.AbilityType;
import com.xpkitty.rpgplugin.manager.skills.PlayerSkills;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import com.xpkitty.rpgplugin.manager.spells.spell_crafting.CustomSpellManager;
import com.xpkitty.rpgplugin.manager.spells.spell_ui.SpellIcon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class UIManager {


    // OPEN MAIN PLAYER MENU

    public static void openMenu(Player player, Rpg rpg) {

        // CREATE MENU INVENTORY
        Inventory ui = Bukkit.createInventory(null, 9, ChatColor.BLACK + "Menu");

        ConnectListener connectListener = rpg.getConnectListener();
        PlayerDataManager playerDataManager = connectListener.getPlayerDataInstance(player);
        YamlConfiguration modifyFile = playerDataManager.getModifyYaml();

        // level, xp, skill point and attribute variables
        int level = modifyFile.getInt("level");
        int exp = modifyFile.getInt("experience");
        int skillPoints = modifyFile.getInt("skill_points");
        String path = "ability_scores."; String path2 = ".value"; String path3 = ".modifier";
        int STR = modifyFile.getInt(path + "STR" + path2);
        int DEX = modifyFile.getInt(path + "DEX" + path2);
        int CON = modifyFile.getInt(path + "CON" + path2);
        int INT = modifyFile.getInt(path + "INT" + path2);
        int WIS = modifyFile.getInt(path + "WIS" + path2);
        int CHA = modifyFile.getInt(path + "CHA" + path2);
        int mSTR = modifyFile.getInt(path + "STR" + path3);
        int mDEX = modifyFile.getInt(path + "DEX" + path3);
        int mCON = modifyFile.getInt(path + "CON" + path3);
        int mINT = modifyFile.getInt(path + "INT" + path3);
        int mWIS = modifyFile.getInt(path + "WIS" + path3);
        int mCHA = modifyFile.getInt(path + "CHA" + path3);
        String modSTR;
        String modDEX;
        String modCON;
        String modINT;
        String modWIS;
        String modCHA;
        if(mSTR >= 0) {modSTR = "+" + mSTR;} else {modSTR= String.valueOf(mSTR);}
        if(mDEX >= 0) {modDEX = "+" + mDEX;} else {modDEX= String.valueOf(mDEX);}
        if(mCON >= 0) {modCON = "+" + mCON;} else {modCON= String.valueOf(mCON);}
        if(mINT >= 0) {modINT = "+" + mINT;} else {modINT= String.valueOf(mINT);}
        if(mWIS >= 0) {modWIS = "+" + mWIS;} else {modWIS= String.valueOf(mWIS);}
        if(mCHA >= 0) {modCHA = "+" + mCHA;} else {modCHA= String.valueOf(mCHA);}

        // ui texture
        ItemStack menuItem = new ItemStack(Material.PEONY);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemMeta.setCustomModelData(19);
        menuItem.setItemMeta(menuItemMeta);
        ui.setItem(0, menuItem);

        ItemStack menuItemDown = new ItemStack(Material.PEONY);
        ItemMeta menuItemDownMeta = menuItemDown.getItemMeta();
        menuItemDownMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemDownMeta.setCustomModelData(20);
        menuItemDown.setItemMeta(menuItemDownMeta);
        ui.setItem(8, menuItemDown);

        // level item
        ItemStack levelItem = new ItemStack(Material.PEONY);
        ItemMeta levelItemMeta = levelItem.getItemMeta();
        levelItemMeta.setCustomModelData(10);
        levelItemMeta.setDisplayName(ChatColor.WHITE + "Information:");
        ArrayList<String> levelItemLore = new ArrayList<>();
        levelItemLore.add(ChatColor.GRAY + "Level " + ChatColor.AQUA + level);
        levelItemLore.add(ChatColor.GRAY + "Exp " + ChatColor.GREEN + exp);
        levelItemMeta.setLore(levelItemLore);
        levelItem.setItemMeta(levelItemMeta);
        ui.setItem(1, levelItem);

        // ability scores item
        ItemStack abilityScoreItem = new ItemStack((Material.PLAYER_HEAD));
        SkullMeta abilityScoreItemMeta = (SkullMeta) abilityScoreItem.getItemMeta();
        abilityScoreItemMeta.setDisplayName(ChatColor.AQUA + "Ability Scores:");
        ArrayList<String> abilityScoreItemLore = new ArrayList<>();
        abilityScoreItemLore.add(ChatColor.WHITE + "STR: " + ChatColor.RED + STR + ChatColor.DARK_RED +" (" + modSTR + ")");
        abilityScoreItemLore.add(ChatColor.WHITE + "DEX: " + ChatColor.RED + DEX + ChatColor.DARK_RED +" (" + modDEX + ")");
        abilityScoreItemLore.add(ChatColor.WHITE + "CON: " + ChatColor.RED + CON + ChatColor.DARK_RED +" (" + modCON + ")");
        abilityScoreItemLore.add(ChatColor.WHITE + "INT: " + ChatColor.AQUA + INT + ChatColor.BLUE +" (" + modINT + ")");
        abilityScoreItemLore.add(ChatColor.WHITE + "WIS: " + ChatColor.AQUA + WIS + ChatColor.BLUE +" (" + modWIS + ")");
        abilityScoreItemLore.add(ChatColor.WHITE + "CHA: " + ChatColor.AQUA + CHA + ChatColor.BLUE +" (" + modCHA + ")");
        abilityScoreItemLore.add(ChatColor.WHITE.toString());
        abilityScoreItemLore.add(ChatColor.WHITE + "Skill points: " + ChatColor.GREEN + skillPoints);
        abilityScoreItemLore.add("");
        abilityScoreItemLore.add(ChatColor.WHITE + "Click for more information");
        abilityScoreItemMeta.setLore(abilityScoreItemLore);
        abilityScoreItemMeta.setOwningPlayer(player);
        abilityScoreItemMeta.setCustomModelData(1);
        abilityScoreItem.setItemMeta(abilityScoreItemMeta);
        ui.setItem(2,abilityScoreItem);

        // skills
        ItemStack skills = new ItemStack(Material.IRON_SWORD);
        ItemMeta skillMeta = skills.getItemMeta();

        skillMeta.setDisplayName(ChatColor.WHITE + "Skills");
        skillMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ArrayList<String> lore = new ArrayList<>();

        NewDataReader newDataReader = new NewDataReader(player,rpg);

        for(AbilityScores abilityScore : AbilityScores.values()) {
            for(PlayerSkills skill : newDataReader.loadData(rpg,player).getSkillMap().keySet()) {
                if (abilityScore.equals(skill.getBaseAbilityScore())) {
                    lore.add(skill.getBaseAbilityScore().getColour() + skill.getName() + " " + skill.getBaseAbilityScore().getDarkerColour() + newDataReader.getSkillLevel(player, skill));
                }
            }
        }

        skillMeta.setLore(lore);
        skills.setItemMeta(skillMeta);
        ui.addItem(skills);

        // abilities
        ItemStack abilityItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta abilityItemMeta = abilityItem.getItemMeta();
        abilityItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Abilities");
        if(MiscPlayerManager.getAbilities(player,rpg).size()<1) {
            abilityItemMeta.setLore(Collections.singletonList(ChatColor.RED + "Learn an ability to unlock this"));
        }
        abilityItem.setItemMeta(abilityItemMeta);
        ui.addItem(abilityItem);

        // spells
        ItemStack spellItem = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta spellItemMeta = spellItem.getItemMeta();
        spellItemMeta.setCustomModelData(1);
        spellItemMeta.setDisplayName(ChatColor.WHITE + "Spells");
        spellItemMeta.setLocalizedName("open_spell_menu");
        spellItem.setItemMeta(spellItemMeta);
        ui.addItem(spellItem);


        // OPEN INVENTORY FOR PLAYER
        player.openInventory(ui);
    }












    public static void openAbilityCustomizeMenu(Player player, Rpg rpg, AbilityType ability) {
        Inventory ui = Bukkit.createInventory(null, 27, ChatColor.BLACK + "Customize " + ability.getName() + "tempClear");

        ItemStack menuItemDown = new ItemStack(Material.PEONY);
        ItemMeta menuItemDownMeta = menuItemDown.getItemMeta();
        menuItemDownMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemDownMeta.setCustomModelData(18);
        menuItemDown.setItemMeta(menuItemDownMeta);
        ui.setItem(26, menuItemDown);

        ItemStack background = new ItemStack(Material.PEONY);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.setDisplayName("");
        backgroundMeta.setCustomModelData(13);
        background.setItemMeta(backgroundMeta);

        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantedBookMeta = enchantedBook.getItemMeta();
        enchantedBookMeta.setDisplayName(ChatColor.WHITE + "Customizing Ability");
        enchantedBook.setItemMeta(enchantedBookMeta);

        ItemStack abilityItem = new ItemStack(ability.getTexture());
        ItemMeta abilityMeta = abilityItem.getItemMeta();
        abilityMeta.setDisplayName(ChatColor.WHITE + ability.getName());
        abilityMeta.setCustomModelData(ability.getModelData());
        abilityItem.setItemMeta(abilityMeta);

        ItemStack plus = new ItemStack(Material.PEONY);
        ItemMeta plusMeta = plus.getItemMeta();
        plusMeta.setDisplayName(ChatColor.WHITE + "Add click");
        plusMeta.setCustomModelData(26);
        plusMeta.setLore(rpg.getClickManager().getSetClicksAsStringList(player,ChatColor.GRAY.toString()));
        plus.setItemMeta(plusMeta);

        ItemStack refresh = new ItemStack(Material.PEONY);
        ItemMeta refreshMeta = refresh.getItemMeta();
        refreshMeta.setDisplayName(ChatColor.WHITE + "Reset clicks");
        refreshMeta.setCustomModelData(25);
        refresh.setItemMeta(refreshMeta);

        ItemStack confirm = new ItemStack(Material.PEONY);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setCustomModelData(23);
        confirmMeta.setDisplayName(ChatColor.WHITE + "Save combo");
        confirm.setItemMeta(confirmMeta);

        ui.setItem(10,enchantedBook);
        ui.setItem(11,abilityItem);
        ui.setItem(13,plus);
        ui.setItem(15,confirm);
        ui.setItem(16,refresh);
        ui.setItem(18, background);

        player.openInventory(ui);
    }



    public static void openAbilitiesMenu(Player player, Rpg rpg) {


        ArrayList<AbilityType> abilities = MiscPlayerManager.getAbilities(player,rpg);

        Inventory ui = Bukkit.createInventory(null,27,ChatColor.BLACK +"Abilities " + "tempClear");

        ItemStack menuItemDown = new ItemStack(Material.PEONY);
        ItemMeta menuItemDownMeta = menuItemDown.getItemMeta();
        menuItemDownMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemDownMeta.setCustomModelData(18);
        menuItemDown.setItemMeta(menuItemDownMeta);
        ui.setItem(26, menuItemDown);

        ItemStack gui = new ItemStack(Material.PEONY);
        ItemMeta guiMeta = gui.getItemMeta();
        guiMeta.setCustomModelData(13);
        guiMeta.setDisplayName("");
        gui.setItemMeta(guiMeta);
        ui.setItem(18,gui);

        for(AbilityType ability : abilities) {
            ItemStack abilityItem = new ItemStack(ability.getTexture());
            ItemMeta abilityItemMeta = abilityItem.getItemMeta();
            abilityItemMeta.setDisplayName(ChatColor.WHITE + ability.getName());
            abilityItemMeta.setCustomModelData(ability.getModelData());

            if(rpg.getClickManager().getAbilityClicksAsString(player, String.valueOf(ChatColor.GRAY), ability) != null) {
                abilityItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + String.valueOf(rpg.getClickManager().getAbilityClicksAsString(player, String.valueOf(ChatColor.GRAY), ability))));
            } else {
                abilityItemMeta.setLore(Collections.singletonList(ChatColor.GRAY + "You have not set a combo"));
            }

            abilityItem.setItemMeta(abilityItemMeta);
            ui.addItem(abilityItem);
        }
        player.openInventory(ui);
    }


    //OPEN PLAYER CLASS MENU

    public static void openClassMenu(Player player,Rpg rpg) {

        Inventory ui = Bukkit.createInventory(null,9, ChatColor.WHITE+"Class Menu" + "tempClear");

        ItemStack menuItemDown = new ItemStack(Material.PEONY);
        ItemMeta menuItemDownMeta = menuItemDown.getItemMeta();
        menuItemDownMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemDownMeta.setCustomModelData(20);
        menuItemDown.setItemMeta(menuItemDownMeta);
        ui.setItem(8, menuItemDown);

        ItemStack peony = new ItemStack(Material.PEONY);
        ItemMeta peonyMeta = peony.getItemMeta();
        peonyMeta.setDisplayName("");
        peonyMeta.setCustomModelData(4);
        peony.setItemMeta(peonyMeta);

        ui.addItem(peony);

        boolean hasClass = false;
        ClassList currentClass = MiscPlayerManager.getPlayerClass(player,rpg);

        if(currentClass != null) {
            hasClass = true;
        }


        for(ClassList classList : ClassList.values()) {

            ItemStack classItem = new ItemStack(classList.getTexture());
            ItemMeta classItemMeta = classItem.getItemMeta();
            classItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_DYE,ItemFlag.HIDE_POTION_EFFECTS);
            classItemMeta.setDisplayName(ChatColor.WHITE + classList.getDisplay());
            classItemMeta.setLocalizedName(classList.name());
            classItemMeta.setCustomModelData(classList.getCustomModelData());

            if(hasClass) {
                if(currentClass.equals(classList)) {
                    classItemMeta.setLore(Collections.singletonList(ChatColor.RED + "You have already chosen a class"));

                } else {
                    classItemMeta.setLore(Collections.singletonList(ChatColor.RED + "You have already chosen a class"));
                }
            } else {
                classItemMeta.setLore(Collections.singletonList(ChatColor.GOLD + "Click to choose class"));
            }

            classItem.setItemMeta(classItemMeta);

            ui.addItem(classItem);
        }

        player.openInventory(ui);
    }






    public static void openAbilityScoresMenu(Player player, Rpg rpg) {

        ConnectListener connectListener = rpg.getConnectListener();
        PlayerDataManager playerDataManager = connectListener.getPlayerDataInstance(player);
        YamlConfiguration modifyFile = playerDataManager.getModifyYaml();

        int skillPoints = modifyFile.getInt("skill_points");
        String path = "ability_scores."; String path2 = ".value"; String path3 = ".modifier";
        int STR = modifyFile.getInt(path + "STR" + path2);
        int DEX = modifyFile.getInt(path + "DEX" + path2);
        int CON = modifyFile.getInt(path + "CON" + path2);
        int INT = modifyFile.getInt(path + "INT" + path2);
        int WIS = modifyFile.getInt(path + "WIS" + path2);
        int CHA = modifyFile.getInt(path + "CHA" + path2);
        int mSTR = modifyFile.getInt(path + "STR" + path3);
        int mDEX = modifyFile.getInt(path + "DEX" + path3);
        int mCON = modifyFile.getInt(path + "CON" + path3);
        int mINT = modifyFile.getInt(path + "INT" + path3);
        int mWIS = modifyFile.getInt(path + "WIS" + path3);
        int mCHA = modifyFile.getInt(path + "CHA" + path3);
        String modSTR;
        String modDEX;
        String modCON;
        String modINT;
        String modWIS;
        String modCHA;
        if(mSTR >= 0) {modSTR = "+" + mSTR;} else {modSTR= String.valueOf(mSTR);}
        if(mDEX >= 0) {modDEX = "+" + mDEX;} else {modDEX= String.valueOf(mDEX);}
        if(mCON >= 0) {modCON = "+" + mCON;} else {modCON= String.valueOf(mCON);}
        if(mINT >= 0) {modINT = "+" + mINT;} else {modINT= String.valueOf(mINT);}
        if(mWIS >= 0) {modWIS = "+" + mWIS;} else {modWIS= String.valueOf(mWIS);}
        if(mCHA >= 0) {modCHA = "+" + mCHA;} else {modCHA= String.valueOf(mCHA);}

        String skillPointsString = String.valueOf(skillPoints);
        String windowName = ChatColor.RED + skillPointsString + ChatColor.DARK_RED + " skill points remaining" + "tempClear";

        // CREATE MENU INVENTORY
        Inventory ui = Bukkit.createInventory(null, 9, ChatColor.BLACK + windowName);


        ItemStack menuItemDown = new ItemStack(Material.PEONY);
        ItemMeta menuItemDownMeta = menuItemDown.getItemMeta();
        menuItemDownMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemDownMeta.setCustomModelData(20);
        menuItemDown.setItemMeta(menuItemDownMeta);
        ui.setItem(8, menuItemDown);


        ItemStack strItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta strMeta = strItem.getItemMeta();
        ItemStack dexItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta dexMeta = dexItem.getItemMeta();
        ItemStack conItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta conMeta = conItem.getItemMeta();
        ItemStack intItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta intMeta = intItem.getItemMeta();
        ItemStack wisItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta wisMeta = wisItem.getItemMeta();
        ItemStack chaItem = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta chaMeta = chaItem.getItemMeta();
        ItemStack point = new ItemStack(Material.PEONY);
        ItemMeta pointMeta = point.getItemMeta();
        pointMeta.setDisplayName(ChatColor.RED + skillPointsString + ChatColor.DARK_RED + " skill points remaining");
        pointMeta.setCustomModelData(10);
        point.setItemMeta(pointMeta);


        int level = rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().getInt("level");
        int abilityCap = MiscPlayerManager.calculateAbilityScoreCaps(level, rpg);




        strMeta.setDisplayName(ChatColor.WHITE + "Strength");
        ArrayList<String> strLore = new ArrayList<>();
        strLore.add(ChatColor.RED.toString() + STR + ChatColor.DARK_RED +" (" + modSTR + ")");
        strMeta.setLocalizedName("str");

        if(STR>=abilityCap) {
            strLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if (skillPoints > 0){
            strLore.add(ChatColor.YELLOW + "Click to increase");
        }


        strMeta.setLore(strLore);

        dexMeta.setDisplayName(ChatColor.WHITE + "Dexterity");
        ArrayList<String> dexLore = new ArrayList<>();
        dexLore.add(ChatColor.RED.toString() + DEX + ChatColor.DARK_RED +" (" + modDEX + ")");
        dexMeta.setLocalizedName("dex");

        if(DEX>=abilityCap) {
            dexLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if(skillPoints > 0) {
            dexLore.add(ChatColor.YELLOW + "Click to increase");
        }

        dexMeta.setLore(dexLore);

        conMeta.setDisplayName(ChatColor.WHITE + "Constitution");
        ArrayList<String> conLore = new ArrayList<>();
        conLore.add(ChatColor.RED.toString() + CON + ChatColor.DARK_RED +" (" + modCON + ")");
        conMeta.setLocalizedName("con");
        if(CON>=abilityCap) {
            conLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if(skillPoints > 0) {
            conLore.add(ChatColor.YELLOW + "Click to increase");
        }

        conMeta.setLore(conLore);

        intMeta.setDisplayName(ChatColor.WHITE + "Intelligence");
        ArrayList<String> intLore = new ArrayList<>();
        intLore.add(ChatColor.AQUA.toString() + INT + ChatColor.BLUE +" (" + modINT + ")");
        intMeta.setLocalizedName("int");

        if(INT>=abilityCap) {
            intLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if(skillPoints > 0) {
            intLore.add(ChatColor.YELLOW + "Click to increase");
        }

        intMeta.setLore(intLore);

        wisMeta.setDisplayName(ChatColor.WHITE + "Wisdom");
        ArrayList<String> wisLore = new ArrayList<>();
        wisLore.add(ChatColor.AQUA.toString() + WIS + ChatColor.BLUE +" (" + modWIS + ")");
        wisMeta.setLocalizedName("wis");

        if(WIS>=abilityCap) {
            wisLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if(skillPoints > 0) {
            wisLore.add(ChatColor.YELLOW + "Click to increase");
        }

        wisMeta.setLore(wisLore);

        chaMeta.setDisplayName(ChatColor.WHITE + "Charisma");
        ArrayList<String> chaLore = new ArrayList<>();
        chaLore.add(ChatColor.AQUA.toString() + CHA + ChatColor.BLUE +" (" + modCHA + ")");
        chaMeta.setLocalizedName("cha");

        if(CHA>=abilityCap) {
            chaLore.add(ChatColor.GOLD + "Can no longer be increased");
        } else if(skillPoints > 0) {
            chaLore.add(ChatColor.YELLOW + "Click to increase");
        }

        chaMeta.setLore(chaLore);

        strItem.setItemMeta(strMeta);
        dexItem.setItemMeta(dexMeta);
        conItem.setItemMeta(conMeta);
        intItem.setItemMeta(intMeta);
        wisItem.setItemMeta(wisMeta);
        chaItem.setItemMeta(chaMeta);


        ItemStack menuItem = new ItemStack(Material.PEONY);
        ItemMeta menuItemMeta = menuItem.getItemMeta();
        menuItemMeta.setDisplayName(ChatColor.WHITE.toString());
        menuItemMeta.setCustomModelData(4);
        menuItem.setItemMeta(menuItemMeta);
        ui.setItem(0, menuItem);

        ui.setItem(1,strItem);
        ui.setItem(2,dexItem);
        ui.setItem(3,conItem);
        ui.setItem(4,intItem);
        ui.setItem(5,wisItem);
        ui.setItem(6,chaItem);
        ui.setItem(7,point);

        // OPEN INVENTORY FOR PLAYER
        player.openInventory(ui);

    }









    public static void openSpellMenu(Player player, Rpg rpg, boolean showLearned) {
        //Set windowName variable - tempClear stands for tem clearing of inventory
        String windowName = "mainSpellMenu" + "tempClear";

        if(!showLearned) {
            windowName = "mainNotLearnedSpellMenu" + "tempClear";
        }

        // CREATE MENU INVENTORY
        Inventory ui = Bukkit.createInventory(null, 54, ChatColor.BLACK + windowName);

        //setup view
        ItemStack i49 = new ItemStack(Material.PEONY);
        ItemMeta m49 = i49.getItemMeta();
        m49.setDisplayName(" ");
        m49.setCustomModelData(12);
        i49.setItemMeta(m49);

        ItemStack i53 = new ItemStack(Material.PEONY);
        ItemMeta m53 = i53.getItemMeta();
        m53.setDisplayName(" ");
        m53.setCustomModelData(18);
        i53.setItemMeta(m53);

        ItemStack empty = new ItemStack(Material.PEONY);
        ItemMeta emptyM = empty.getItemMeta();
        emptyM.setDisplayName(" ");
        emptyM.setCustomModelData(99);
        empty.setItemMeta(emptyM);

        ui.setItem(45,empty);
        ui.setItem(46,empty);
        ui.setItem(47,empty);
        ui.setItem(48,empty);
        ui.setItem(49,i49);
        ui.setItem(50,empty);
        ui.setItem(51,empty);
        ui.setItem(52,empty);
        ui.setItem(53,i53);



        //Set playerSpellFile and YamlConfiguration
        PlayerSpellFile playerSpellFile = rpg.getConnectListener().getPlayerSpellFile(player);
        YamlConfiguration yamlConfiguration = playerSpellFile.getModifySpellFile();

        //Set root for yamlPath
        String root = "spells.";



        if(yamlConfiguration.contains("spells")) {

            //Foreach spell in player spell sheet
            for (String element : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {

                //Create ItemStack
                ItemStack itemStack = new ItemStack(Material.PEONY);
                ItemMeta itemMeta = itemStack.getItemMeta();

                //Set yamlPath
                String path = root + element + ".";

                //Check if player has learned spell
                boolean isLearned = yamlConfiguration.getBoolean(path + "learned");

                //if spell meets menu type criteria
                if (showLearned == isLearned) {

                    //Declare important variables

                    //boolean
                    boolean isCustom = yamlConfiguration.getBoolean(path + "custom");
                    boolean isHardCoded = !isCustom;
                    boolean isPlayerMade = yamlConfiguration.getBoolean(path + "player_made");
                    boolean isTnl = false;


                    //integer
                    int level = 1;
                    int progress = 0;
                    int maxLevel = 1;
                    int expTnl = 25;
                    int learnProgress = 0;
                    int difficulty = 20;
                    int energyCost = 0;
                    int id = -2;

                    //string
                    String incantation = "";
                    String name = "";
                    String type = "";


                    //If spell is hard coded
                    if (isHardCoded) {

                        //Find spell
                        for (HpSpellsList hpSpellsList : HpSpellsList.values()) {
                            if (hpSpellsList.name().toLowerCase(Locale.ROOT).equalsIgnoreCase(element)) {

                                //get spell data and player spell data
                                level = yamlConfiguration.getInt(path + "level");
                                progress = yamlConfiguration.getInt(path + "progress");
                                learnProgress = yamlConfiguration.getInt(path + "learning_progress");
                                name = hpSpellsList.getDescription();
                                type = hpSpellsList.getCategory();
                                incantation = hpSpellsList.getDisplay();
                                maxLevel = hpSpellsList.getMaximumLevel();
                                difficulty = hpSpellsList.getCastDifficulty();
                                energyCost = hpSpellsList.getManaCost();
                                id = hpSpellsList.getNumericId();

                                if (level < maxLevel) {
                                    expTnl = hpSpellsList.getLevelingXp().get(level - 1);
                                    if (expTnl > 0) {
                                        isTnl = true;
                                    }
                                }

                                itemStack = SpellIcon.getSpellIconItem(rpg, incantation);
                                itemMeta = itemStack.getItemMeta();
                            }
                        }


                    } else {

                        MainSpellData mainSpellData = rpg.getMainSpellData();
                        YamlConfiguration yamlSpellData = mainSpellData.getModifyYaml();

                        for (String spell : yamlSpellData.getConfigurationSection("spells").getKeys(false)) {
                            String rawIncantation = element;
                            rawIncantation = rawIncantation.replace("_", " ");


                            //path and incantation
                            String pathSpell = "spells." + spell + ".";
                            String spellIncantation = yamlSpellData.getString(pathSpell + "incantation");


                            // if spell found
                            if (rawIncantation.equalsIgnoreCase(spellIncantation)) {

                                //ID
                                id = Integer.parseInt(spell);

                                // get spell data
                                incantation = spellIncantation;
                                name = yamlSpellData.getString(pathSpell + "name");
                                maxLevel = yamlSpellData.getInt(pathSpell + "exp.max_level");
                                difficulty = yamlSpellData.getInt(pathSpell + "exp.learn");
                                energyCost = yamlSpellData.getInt(pathSpell + "energy_cost");

                                // get player spell data
                                level = yamlConfiguration.getInt(path + "level");
                                learnProgress = yamlConfiguration.getInt(path + "learning_progress");
                                progress = yamlConfiguration.getInt(path + "progress");

                                type = CustomSpellManager.getSpellType(rpg, id);

                                if (level < maxLevel) {
                                    int lv = level + 1;
                                    if (yamlSpellData.contains(pathSpell + "exp.level_up." + lv)) {
                                        expTnl = yamlSpellData.getInt(pathSpell + "exp.level_up." + lv);
                                    }
                                    if (expTnl > 0) {
                                        isTnl = true;
                                    }
                                }

                                // get spell icon and display
                                itemStack = SpellIcon.getSpellIconItem(rpg, incantation);
                                itemMeta = itemStack.getItemMeta();
                            }
                        }

                    }


                    //Apply variables
                    ArrayList<String> lore = new ArrayList<>();

                    //set lore

                    //display spell name if player INT >= 13
                    if (MiscPlayerManager.getAbilityScore(rpg, player, AbilityScores.INT) >= 13) {
                        lore.add(ChatColor.WHITE + name);
                    }

                    //display spell type if player INT >= 13
                    if (MiscPlayerManager.getAbilityScore(rpg, player, AbilityScores.INT) >= 13) {
                        lore.add(ChatColor.WHITE + type);
                    }

                    //add empty lore line
                    lore.add("");

                    //Display player spell LVL
                    lore.add(ChatColor.WHITE + "Level " + ChatColor.GOLD + level + ChatColor.WHITE + "/" + ChatColor.GOLD + maxLevel);

                    //if player has learned spell
                    if (showLearned) {
                        //add spell EXP progress display to lore
                        if (level < maxLevel) {
                            if (isTnl) {
                                lore.add(ChatColor.WHITE + "EXP " + ChatColor.AQUA + progress + ChatColor.WHITE + "/" + ChatColor.RED + expTnl);
                            } else {
                                lore.add(ChatColor.WHITE + "EXP " + ChatColor.AQUA + progress);
                            }
                        }
                    } else {
                        lore.add(ChatColor.WHITE + "Learning EXP " + ChatColor.AQUA + learnProgress);
                        lore.add(ChatColor.WHITE + "Difficulty " + ChatColor.RED + difficulty);
                        lore.add(ChatColor.WHITE + "Energy Cost " + ChatColor.RED + energyCost);

                    }

                    //Set item lore
                    assert itemMeta != null;
                    itemMeta.setLore(lore);

                    //set item localized name
                    itemMeta.setLocalizedName(String.valueOf(id));

                    //Set itemStack meta to itemMeta and add ItemStack to menu
                    itemStack.setItemMeta(itemMeta);

                    ui.addItem(itemStack);

                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not know any spells");
        }



        //open menu for player
        player.openInventory(ui);
    }

    public static void openSingleSpellMenu(Rpg rpg, Player player, int numericId) {

        // 21 Red tick
        // 22 Yellow tick
        // 23 Green tick
        // 24 Grey tick
        // 25 Yellow reset
        // 26 Green plus
        // 27 Red plus
        // 28 Yellow plus
        // 29 Grey plus

        String incantation = "";

        if(numericId<=11) {
            for(HpSpellsList element : HpSpellsList.values()) {
                if(element.getNumericId() == numericId) {
                    incantation = element.getDisplay();
                }
            }
        }
        if(numericId>=0) {
            YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();

            if(yamlConfiguration.contains("spells." + numericId)) {
                incantation = yamlConfiguration.getString("spells." + numericId + ".incantation");
            }
        }

        String title = "localSpellMenu" + "tempClear" + numericId;

        Inventory ui = Bukkit.createInventory(null, 27, title);

        // background
        ItemStack menu = new ItemStack(Material.PEONY);
        ItemMeta menuMeta = menu.getItemMeta();
        menuMeta.setDisplayName(" ");
        menuMeta.setCustomModelData(60);
        menu.setItemMeta(menuMeta);
        ui.setItem(26,menu);

        //top slots
        ItemStack empty = new ItemStack(Material.PEONY);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        emptyMeta.setCustomModelData(99);
        empty.setItemMeta(emptyMeta);

        for(int i = 0; i<9; i++) {
            ui.setItem(i,empty);
        }

        // spell icon
        ItemStack spellIcon = SpellIcon.getSpellIconItem(rpg,incantation);

        if(spellIcon.getItemMeta()!=null) {
            ItemMeta im = spellIcon.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();

            int bar = rpg.getConnectListener().getPlayerSpellFile(player).getSpellHotBar(numericId);
            int slot = rpg.getConnectListener().getPlayerSpellFile(player).getSpellSlot(numericId);


            if(bar>0 && slot>-1 && slot < 9) {

                int displaySlot = slot+1;

                lore.add(ChatColor.WHITE + "Slot: " + displaySlot);
                lore.add(ChatColor.WHITE + "Hotbar: " + bar);

            } else {
                lore.add(ChatColor.WHITE + "Spell is unbound");
            }
            im.setLore(lore);
            spellIcon.setItemMeta(im);
        }

        ui.setItem(4,spellIcon);

        // arrows /\ \/
        ItemStack arrow = new ItemStack(Material.PEONY);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName(ChatColor.WHITE + "Selected hotbar: " + rpg.getConnectListener().getPlayerSpellFile(player).getEditHotbar());
        arrowMeta.setCustomModelData(50);
        arrowMeta.setLocalizedName("arrowDown");
        arrow.setItemMeta(arrowMeta);
        ui.setItem(18,arrow);
        arrowMeta.setCustomModelData(52);
        arrowMeta.setLocalizedName("arrowUp");
        arrow.setItemMeta(arrowMeta);
        ui.setItem(0,arrow);

        ItemStack refresh = new ItemStack(Material.PEONY);
        ItemMeta refreshMeta = refresh.getItemMeta();
        refreshMeta.setDisplayName(ChatColor.WHITE + "Reset");
        refreshMeta.setCustomModelData(25);
        refreshMeta.setLocalizedName("refresh");
        refresh.setItemMeta(refreshMeta);
        ui.setItem(17,refresh);


        // pluses
        ItemStack plus = new ItemStack(Material.PEONY);

        for(int i = 1; i<9; i++) {
            ItemMeta plusMeta = plus.getItemMeta();
            plus.setAmount(i);
            plusMeta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Bind to slot " + i);
            plusMeta.setCustomModelData(26);
            plusMeta.setLocalizedName("plus" + i);
            plus.setItemMeta(plusMeta);
            ui.setItem(i+8,plus);
        }

        ItemStack tick = new ItemStack(Material.PEONY);
        ItemMeta tickMeta = tick.getItemMeta();
        tickMeta.setDisplayName(ChatColor.WHITE+"Accept");
        tickMeta.setCustomModelData(23);
        tickMeta.setLocalizedName("tick");
        tick.setItemMeta(tickMeta);
        ui.setItem(22,tick);

        player.openInventory(ui);


    }

}
