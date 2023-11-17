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

import java.util.ArrayList;
import java.util.Arrays;

public enum ResourcePack {

    NULL("https://www.dropbox.com/s/p9j9zr52c7oc1hz/NoResource.zip?dl=1",new ArrayList<>()),
    DEFAULT("https://www.dropbox.com/s/ap3l9czm6amyksy/KittensCraft%20RPG-0.19-release.zip?dl=1", new ArrayList<>(Arrays.asList("Hogwarts","Flat","Utumno"))),
    TATOOINE("https://www.dropbox.com/s/0bezvcscz0qi8qx/KittensCraft%20RPG%20DesertEssentials%200.17.7-release.zip?dl=1",new ArrayList<>(Arrays.asList("Tatooine","Bespin")));

    String url;
    ArrayList<String> worldName;

    ResourcePack(String url, ArrayList<String> worldName) {
        this.url = url;
        this.worldName = worldName;
    }

    public String getUrl() {return url;}
    public ArrayList<String> getWorlds() {return worldName;}
}
