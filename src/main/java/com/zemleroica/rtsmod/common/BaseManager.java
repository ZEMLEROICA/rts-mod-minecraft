package com.zemleroica.rtsmod.common;

import java.util.*;

import com.zemleroica.rtsmod.entities.BaseEntity;

import net.minecraft.world.phys.Vec3;

public class BaseManager {
    private static Map<UUID, BaseEntity> playerBases = new HashMap<>();
    private static Map<UUID, String> playerFactions = new HashMap<>();

    public static void setPlayerBase(UUID playerId, BaseEntity base, String faction) {
        playerBases.put(playerId, base);
        playerFactions.put(playerId, faction);
    }

    public static BaseEntity getPlayerBase(UUID playerId) {
        return playerBases.get(playerId);
    }

    public static String getPlayerFaction(UUID playerId) {
        return playerFactions.get(playerId);
    }

    public static boolean hasBase(UUID playerId) {
        return playerBases.containsKey(playerId);
    }
}