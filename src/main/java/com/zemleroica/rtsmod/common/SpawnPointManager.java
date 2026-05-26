package com.zemleroica.rtsmod.common;

import java.util.*;

import net.minecraft.core.BlockPos;

public class SpawnPointManager {
    private static Map<UUID, List<BlockPos>> playerSpawnPoints = new HashMap<>();

    public static void addSpawnPoint(UUID playerId, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        playerSpawnPoints.computeIfAbsent(playerId, k -> new ArrayList<>()).add(pos);
    }

    public static int getSpawnPointCount(UUID playerId) {
        return playerSpawnPoints.getOrDefault(playerId, Collections.emptyList()).size();
    }

    public static List<BlockPos> getSpawnPoints(UUID playerId) {
        return playerSpawnPoints.getOrDefault(playerId, new ArrayList<>());
    }
}