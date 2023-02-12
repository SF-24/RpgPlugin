package com.xpkitty.rpgplugin.manager;

import org.bukkit.Color;

import java.util.ArrayList;

public class MiscManager {
    public static ArrayList<Color> getColorList() {

        ArrayList<Color> colours = new ArrayList<>();
        colours.add(Color.RED);
        colours.add(Color.BLUE);
        colours.add(Color.WHITE);
        colours.add(Color.GRAY);
        colours.add(Color.LIME);
        colours.add(Color.OLIVE);
        colours.add(Color.ORANGE);
        colours.add(Color.PURPLE);
        colours.add(Color.YELLOW);
        colours.add(Color.TEAL);
        colours.add(Color.AQUA);
        colours.add(Color.BLACK);
        colours.add(Color.FUCHSIA);
        colours.add(Color.SILVER);
        colours.add(Color.NAVY);
        colours.add(Color.MAROON);
        colours.add(Color.GREEN);

        return colours;
    }

    public static Color getColor(int r, int g, int b) {
        return Color.fromRGB(r,g,b);
    }
}
