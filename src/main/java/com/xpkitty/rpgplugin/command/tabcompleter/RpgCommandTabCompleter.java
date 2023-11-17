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
package com.xpkitty.rpgplugin.command.tabcompleter;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsHouseList;
import com.xpkitty.rpgplugin.manager.hogwarts.HogwartsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RpgCommandTabCompleter implements TabCompleter {
    Rpg rpg;
    public RpgCommandTabCompleter(Rpg rpg) {
        this.rpg=rpg;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> hogwartsHouses = new ArrayList<>();
        for(HogwartsHouseList element: HogwartsHouseList.values()) {
            hogwartsHouses.add(element.getDisplay());
        }
        ArrayList<String> skinsList = new ArrayList<>();
        if(sender instanceof Player) {
            for(String element : rpg.getConnectListener().getPlayerSkinInstance((Player) sender).listSkins()) {
                skinsList.add(element);
            }
        }



        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("ability","energy","setname","set_ability_sneak_setting","set_hotbar_count_setting","set_lightsaber_right_click_eject_setting","skin","skins","playerskin","playerskins","ability_scores","hogwarts","setitemname","getitemname","getlocalizeditemname","setlocalizeditemname","setskin","setskillpoints"),new ArrayList<>());
        } else if(args.length==2 && args[1].equalsIgnoreCase("energy")) {
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("set"),new ArrayList<>());
        } else if(args.length==2 && args[0].equalsIgnoreCase("ability_scores")) {
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("set"),new ArrayList<>());
        } else if(args.length==2 && ((args[0].equalsIgnoreCase("skin") || args[0].equalsIgnoreCase("skins")) || args[0].equalsIgnoreCase("playerskin") || args[0].equalsIgnoreCase("playerskins"))) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("list","create","set","load"),new ArrayList<>());
        } else if(args.length==3 && args[1].equalsIgnoreCase("load") && ((args[0].equalsIgnoreCase("skin") || args[0].equalsIgnoreCase("skins")) || args[0].equalsIgnoreCase("playerskin") || args[0].equalsIgnoreCase("playerskins"))) {
            return StringUtil.copyPartialMatches(args[2],skinsList,new ArrayList<>());
        } else if(args.length==3 && args[0].equalsIgnoreCase("ability_scores")) {
            return StringUtil.copyPartialMatches(args[2], Arrays.asList("STR","DEX","CON","INT","WIS","CHA"),new ArrayList<>());
        } else if(args.length==2 && args[0].equalsIgnoreCase("ability")) {
            return StringUtil.copyPartialMatches(args[1], Arrays.asList("add","remove"), new ArrayList<>());
        } else if(args.length==2 && args[0].equalsIgnoreCase("hogwarts")) {
            return StringUtil.copyPartialMatches(args[1], Collections.singletonList("house"),new ArrayList<>());
        } else if(args.length==3 && args[1].equalsIgnoreCase("house")) {
            return StringUtil.copyPartialMatches(args[2], Arrays.asList("list", "points", "join","get"), new ArrayList<>());
        } else if(args.length==4 && args[1].equalsIgnoreCase("house") && args[2].equalsIgnoreCase("join")) {
            return StringUtil.copyPartialMatches(args[3], hogwartsHouses, new ArrayList<>());
        } else if(args.length==4 && args[1].equalsIgnoreCase("house") && args[2].equalsIgnoreCase("points")) {
            return StringUtil.copyPartialMatches(args[3], Arrays.asList("give", "list", "take", "set"), new ArrayList<>());
        }



        return null;
    }
}
