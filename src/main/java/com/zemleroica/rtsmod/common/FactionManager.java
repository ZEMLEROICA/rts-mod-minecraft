package com.zemleroica.rtsmod.common;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import com.google.gson.*;
import com.zemleroica.rtsmod.data.Faction;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

public class FactionManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, Faction> factions = new HashMap<>();

    public static void loadFactions(MinecraftServer server) {
        Path configPath = server.getWorldPath(new LevelResource("rts_mod_data/factions"));
        configPath.toFile().mkdirs();

        File factionsDir = configPath.toFile();
        File[] files = factionsDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    Faction faction = GSON.fromJson(reader, Faction.class);
                    if (faction != null) {
                        factions.put(faction.name, faction);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (factions.isEmpty()) {
            createDefaultFactions(configPath);
        }
    }

    private static void createDefaultFactions(Path configPath) {
        Faction humanFaction = new Faction(
            "Humans",
            "minecraft:villager",
            "minecraft:wither",
            Arrays.asList("minecraft:zombie", "minecraft:skeleton", "minecraft:creeper"),
            0xFF0000
        );

        Faction elfFaction = new Faction(
            "Elves",
            "minecraft:iron_golem",
            "minecraft:ender_dragon",
            Arrays.asList("minecraft:spider", "minecraft:cave_spider", "minecraft:enderman"),
            0x00FF00
        );

        saveFaction(humanFaction, configPath);
        saveFaction(elfFaction, configPath);

        factions.put(humanFaction.name, humanFaction);
        factions.put(elfFaction.name, elfFaction);
    }

    public static void saveFaction(Faction faction, Path configPath) {
        try {
            File file = configPath.resolve(faction.name + ".json").toFile();
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(faction, writer);
            }
            factions.put(faction.name, faction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Faction getFaction(String name) {
        return factions.get(name);
    }

    public static Collection<Faction> getAllFactions() {
        return factions.values();
    }

    public static void addFaction(Faction faction, Path configPath) {
        saveFaction(faction, configPath);
    }

    public static void removeFaction(String name) {
        factions.remove(name);
    }
}