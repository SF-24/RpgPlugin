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

import com.xpkitty.rpgplugin.manager.spells.wand.WandCore;
import com.xpkitty.rpgplugin.manager.spells.wand.WandType;
import com.xpkitty.rpgplugin.manager.spells.wand.WandWood;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GetItemCommandTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> wand_cores = new ArrayList<>();
        ArrayList<String> wand_woods = new ArrayList<>();
        ArrayList<String> wand_types = new ArrayList<>();

        for(WandCore element : WandCore.values()) {
            wand_cores.add(element.name().toLowerCase(Locale.ROOT));
        }

        for(WandWood element : WandWood.values()) {
            wand_woods.add(element.name().toLowerCase(Locale.ROOT));
        }

        for(WandType element : WandType.values()) {
            wand_types.add(element.name().toLowerCase(Locale.ROOT));
            wand_types.add("random");
        }

        if(args.length==1) {
            return StringUtil.copyPartialMatches(args[0],Arrays.asList("blaster","chocolate_frog_card","darksaber","disguise_potion_steve","gun","gun1","gun2","gun3","hp_wand","lightsaber","record_book","spawn_stone","spell_scroll","staff","stupefy_wand","teleport_scroll","wand","wandbox","wingardium_wand"),new ArrayList<>());
        } else if(args.length==2 && args[0].equalsIgnoreCase("hp_wand")) {
            return StringUtil.copyPartialMatches(args[1],wand_cores,new ArrayList<>());
        } else if(args.length==3 && args[0].equalsIgnoreCase("hp_wand")) {
            return StringUtil.copyPartialMatches(args[2],wand_woods,new ArrayList<>());
        } else if(args.length==4 && args[0].equalsIgnoreCase("hp_wand")) {
            return StringUtil.copyPartialMatches(args[3],wand_types,new ArrayList<>());
        }

        return null;
    }
}
