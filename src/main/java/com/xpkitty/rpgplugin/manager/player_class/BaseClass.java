// 2023. Author: S.Frynas (XpKitty), e-mail: sebastian.frynas@outlook.com, licence: GNU GPL v3

package com.xpkitty.rpgplugin.manager.player_class;

import com.xpkitty.rpgplugin.Rpg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public abstract class BaseClass {

    protected ClassList type;
    protected UUID uuid;

    public BaseClass(Rpg rpg, UUID uuid, ClassList type) {
        this.type = type;
        this.uuid = uuid;

        Player player = Bukkit.getPlayer(uuid);

        assert player != null;
        rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().set("player_class",type.name().toLowerCase(Locale.ROOT));

        try {
            rpg.getConnectListener().getPlayerDataInstance(player).getModifyYaml().save(rpg.getConnectListener().getPlayerDataInstance(player).getYamlFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(rpg,player,type);
    }

    public UUID getUuid() { return uuid;}
    public ClassList getType() { return type; }

    public abstract void onStart(Rpg rpg, Player player, ClassList type);

}
