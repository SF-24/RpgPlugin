// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spell_crafting.admin;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.HpSpellInstance;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdminSpellManager {

    public static void addSpell(Rpg rpg, String name, String incantation, String ints) {
        ArrayList<Integer> spellMovements = new ArrayList<>();


        for (int i = 0; i < ints.length(); i++) {
            System.out.print(ints.charAt(i) + " ");
            String e = String.valueOf(ints.charAt(i));
            spellMovements.add(Integer.parseInt(e));
        }

        int id = rpg.getMainSpellData().getSpellNum(true);

        addSpellToMainFile(rpg,spellMovements,name,incantation,id);
        new HpSpellInstance(rpg,name,incantation,id);
    }

    public static void addSpellToMainFile(Rpg rpg, ArrayList<Integer> wandMovements, String spellName, String spellIncantation, int id) {
        YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();
        File yamlFile = rpg.getMainSpellData().getYamlFile();

        int num = id;
        String path = "spells." + num + ".";

        yamlConfiguration.createSection(path+"name");
        yamlConfiguration.set(path+"name",spellName);
        yamlConfiguration.createSection(path+"incantation");
        yamlConfiguration.set(path+"name",spellIncantation);
        yamlConfiguration.createSection(path+"movement");
        yamlConfiguration.set(path+"movement",wandMovements);
        yamlConfiguration.createSection(path+"energy_cost");
        yamlConfiguration.set(path+"energy_cost",5);

        yamlConfiguration.createSection(path+"display.icon_model_data");
        yamlConfiguration.set(path+"display.icon_model_data",1001);
        yamlConfiguration.createSection(path+"display.hotbar_name");
        yamlConfiguration.set(path+"display.hotbar_name",1001);

        yamlConfiguration.createSection(path+"exp");
        yamlConfiguration.createSection(path+"exp.learn");
        yamlConfiguration.set(path+"exp.learn",25);
        yamlConfiguration.createSection(path+"difficulty");
        yamlConfiguration.set(path+"difficulty",15);
        yamlConfiguration.createSection(path+"exp.max_level");
        yamlConfiguration.set(path+"exp.max_level",2);
        yamlConfiguration.createSection(path+"exp.level_up");
        yamlConfiguration.createSection(path+"exp.level_up.2");
        yamlConfiguration.set(path+"exp.level_up.2",45);
        try {
            yamlConfiguration.save(yamlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
