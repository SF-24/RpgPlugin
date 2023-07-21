// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.spells.spell_crafting;

import com.xpkitty.rpgplugin.Rpg;
import com.xpkitty.rpgplugin.manager.spells.HpSpellInstance;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellFunction;
import com.xpkitty.rpgplugin.manager.spells.spell_elements.SpellTarget;
import com.xpkitty.rpgplugin.manager.spells.spell_template.CastSpellFromTemplate;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomSpellManager {

    public static void testForSpellPattern(Rpg rpg, Player player, List<Integer> pattern, int castPower) {

        if(!pattern.equals(new ArrayList<>())) {
            YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();

            if(yamlConfiguration.contains("spells")) {

                for (String element : yamlConfiguration.getConfigurationSection("spells").getKeys(false)) {

                    List<Integer> wandMovements = yamlConfiguration.getIntegerList("spells." + element + ".movement");

                    if(wandMovements.equals(pattern)) {
                        castCustomSpell(rpg,player, castPower, Integer.parseInt(element));
                        break;
                    }
                }
            }
        }
    }

    // cast a spell from YAML file
    public static void castCustomSpell(Rpg rpg, Player player, int castPower, int id) {
        // variables
        YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();
        String name = yamlConfiguration.getString("spells." + id + ".name");
        String incantation = yamlConfiguration.getString("spells." + id + ".incantation");

        HpSpellInstance instance = new HpSpellInstance(rpg, name, incantation, id);

        // check for nearby players
        for (Player player2 : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().equals(player2.getWorld())) {
                if (player.getLocation().distance(player2.getLocation()) <= instance.getModifySpellFile().getInt("volume")) {
                    player2.sendMessage("<" + player.getDisplayName() + "> " + incantation);
                }
            }
        }


        yamlConfiguration = instance.getModifySpellFile();

        // check which spell is cast
        if (yamlConfiguration.contains("spell_name")) {

            SpellFunction function = SpellFunction.ENTITY_DAMAGE;
            String f = yamlConfiguration.getString("function");

            for (SpellFunction element : SpellFunction.values()) {
                if (element.name().equalsIgnoreCase(f)) {
                    function = element;
                }
            }

            // calculate values
            float speed = (float) yamlConfiguration.getDouble("speed");
            int distance = yamlConfiguration.getInt("fly_distance");
            String val1 = yamlConfiguration.getString("val1");
            String val2 = yamlConfiguration.getString("val2");
            String val3 = yamlConfiguration.getString("val3");

            String t = yamlConfiguration.getString("target");
            SpellTarget st = SpellTarget.ENTITY;

            for (SpellTarget element : SpellTarget.values()) {
                if (element.name().equalsIgnoreCase(t)) {
                    st = element;
                }
            }

            String particle = yamlConfiguration.getString("particle.type");
            int size = yamlConfiguration.getInt("particle.size");
            int count = yamlConfiguration.getInt("particle.count");
            int r = yamlConfiguration.getInt("particle.color.red");
            int g = yamlConfiguration.getInt("particle.color.green");
            int b = yamlConfiguration.getInt("particle.color.blue");

            Color color = Color.fromRGB(r, g, b);

            // get spell type
            SpellType spellType = SpellType.BEAM;
            if(yamlConfiguration.contains("spell-type")) {
                spellType = SpellType.valueOf(yamlConfiguration.getString("spell-type").toUpperCase(Locale.ROOT));
            }
            if(function.equals(SpellFunction.SHIELD)) {
                spellType=SpellType.SHIELD;
            }

            // cast spell from template
            new CastSpellFromTemplate(rpg, player, castPower, function, speed, distance, st, val1, val2, val3, particle, color, size, count, spellType);
        } else {
            player.sendMessage("ERROR");
        }
    }

    // get spell type from id
    public static String getSpellType(Rpg rpg, int id) {
        YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();
        String name = yamlConfiguration.getString("spells." + id + ".name");
        String incantation = yamlConfiguration.getString("spells." + id + ".incantation");

        HpSpellInstance instance = new HpSpellInstance(rpg, name, incantation, id);
        yamlConfiguration = instance.getModifySpellFile();

        if (yamlConfiguration.contains("spell_name")) {
            String type = yamlConfiguration.getString("spell_type");
            return WordUtils.capitalizeFully(type);
        }
        return null;
    }
}
