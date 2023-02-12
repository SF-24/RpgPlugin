package com.xpkitty.rpgplugin.manager.spells.spell_ui;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.data.spell_data.MainSpellData;
import com.xpkitty.rpgplugin.manager.spells.enum_list.HpSpellsList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpellIcon {

    public static ItemStack getSpellIconItem(Rpg rpg, String incantation) {


        ItemStack item = new ItemStack(Material.PEONY);

        boolean isHardCoded = false;

        String incantationDisplay = ChatColor.GOLD + incantation;

        for(HpSpellsList hpSpellsList : HpSpellsList.values()) {
            if(hpSpellsList.getDisplay().equalsIgnoreCase(incantation)) {
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(incantationDisplay);
                meta.setCustomModelData(hpSpellsList.getItemModelData());
                meta.setLocalizedName("spell_icon" + hpSpellsList.getNumericId());

                item.setItemMeta(meta);

                isHardCoded=true;
            }
        }



        if(!isHardCoded) {

            MainSpellData mainSpellData = rpg.getMainSpellData();
            YamlConfiguration yamlConfiguration = mainSpellData.getModifyYaml();

            for(String element : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {

                String path = "spells.";
                String inc = yamlConfiguration.getString(path + element + ".incantation");



                if(inc.equalsIgnoreCase(incantation)) {
                    int modelData = yamlConfiguration.getInt(path+element+".display.icon_model_data");

                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(incantationDisplay);
                    meta.setCustomModelData(modelData);
                    meta.setLocalizedName("spell_icon" + element);

                    item.setItemMeta(meta);
                }
            }
        }


        return item;
    }


}
