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

    public static void castCustomSpell(Rpg rpg, Player player, int castPower, int id) {
        YamlConfiguration yamlConfiguration = rpg.getMainSpellData().getModifyYaml();
        String name = yamlConfiguration.getString("spells." + id + ".name");
        String incantation = yamlConfiguration.getString("spells." + id + ".incantation");

        //player.sendMessage(id + "|" + incantation + "|" + name);

        //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§a§l" + incantation));

        HpSpellInstance instance = new HpSpellInstance(rpg, name, incantation, id);

        for (Player player2 : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(player2.getLocation()) <= instance.getModifySpellFile().getInt("volume")) {
                player2.sendMessage("<" + player.getDisplayName() + "> " + incantation);
            }
        }

        yamlConfiguration = instance.getModifySpellFile();

        if (yamlConfiguration.contains("spell_name")) {

            SpellFunction function = SpellFunction.ENTITY_DAMAGE;
            String f = yamlConfiguration.getString("function");

            for (SpellFunction element : SpellFunction.values()) {
                if (element.name().equalsIgnoreCase(f)) {
                    function = element;
                }
            }
            //player.sendMessage(f + "|" + function);

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

            new CastSpellFromTemplate(rpg, player, castPower, function, speed, distance, st, val1, val2, val3, particle, color, size, count);
        } else {
            player.sendMessage("ERROR");
        }
    }

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
