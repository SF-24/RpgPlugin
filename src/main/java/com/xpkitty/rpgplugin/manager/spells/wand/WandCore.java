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
package com.xpkitty.rpgplugin.manager.spells.wand;

public enum WandCore {

    KNEAZLE_WHISKER("Kneazle Whisker", 5, "1e53ffe9-5e0d-4833-a458-f4310f1bf63b"),
    DITTANY_STALK("Dittany Stalk",5, "0693ec11-e9a2-4d69-8dae-6bb7074bbe8f"),
    TROLL_WHISKER("Troll Whisker", 5, "f54a811d-09a6-497d-9242-41144c487d8f"),

    KELPIE_HAIR("Kelpie Hair",4, "fd4c6879-a116-48ab-97c8-df1212f3ec02"),
    WHITE_RIVER_MONSTER_SPINE("White River Monster Spine", 4, "ba927405-57a5-4d1f-959b-9d6497ac7f1e"),
    SNALLYGASTER_HEARTSTRING("Snallygaster Heartstring", 4, "562f9c24-9894-4f1a-90e7-aa2933ae1eac"),
    ROUGARU_HAIR("Rougaru Hair", 4, "a731bad5-7800-4539-b74a-e2bd3dcf6d83"),
    CORAL("Coral", 4, "0fa51cc9-6339-44b4-861e-6f7f6a0f86a8"),
    JACKALOPE_ANTLER("Jackalope Antler", 4, "2301adc3-3329-4764-bb31-82a13d407a2a"),

    VEELA_HAIR("Veela Hair", 3, "48d91d5e-1882-4a43-8e7b-8988a02c761f"),

    BASILISK_HORN("Basilisk Horn", 2, "15f0555b-88ae-420c-8992-5eef62cc66b6"),
    HORNED_SERPENT_HORN("Horned Serpent Horn", 2, "2f9ac019-4f3d-4308-970b-20c5ed6278dc"),
    WAMPUS_CAT_HAIR("Wampus Cat Hair", 2, "79ea16e7-248b-4560-9536-9e5f4088ba90"),
    THUNDERBIRD_TAIL_FEATHER("Thunderbird Tail Feather", 2, "1540976a-5a50-47ed-8094-ce206d0d6168"),
    THESTRAL_TAIL_HAIR("Thestral Tail Hair",2, "12291a47-73bc-4326-842e-21673afc149e"),

    PHEONIX_FEATHER("Pheonix Feather", 1, "e307420b-5c47-4840-95de-4996a5571a5c"),
    DRAGON_HEARTSTRING("Dragon Heartstring", 1, "9a8510fa-3ee6-4487-b73c-c670e947723e"),
    UNICORN_HAIR("Unicorn Hair", 1, "fb4ac1cd-bc03-4153-a335-d4d5554cf5e4");

    String display,id;
    int rating;

    WandCore(String display, int rating, String id) {
        this.display = display;
        this.rating = rating;
        this.id=id;
    }

    public String getDisplay() { return display; }
    public int getRating() { return rating; }
    public String getId() { return id; }
}
