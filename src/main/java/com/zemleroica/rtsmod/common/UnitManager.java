package com.zemleroica.rtsmod.common;

import java.util.*;

import com.zemleroica.rtsmod.entities.RTSUnitEntity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class UnitManager {
    private static Map<UUID, Queue<String>> productionQueues = new HashMap<>();
    private static Map<UUID, Vec3> baseLocations = new HashMap<>();
    private static Map<UUID, Long> lastSpawnTime = new HashMap<>();
    private static final long BASE_SPAWN_DELAY = 5000;

    public static void addToProductionQueue(UUID playerId, String unitType) {
        productionQueues.computeIfAbsent(playerId, k -> new LinkedList<>()).offer(unitType);
    }

    public static void setBaseLocation(UUID playerId, Vec3 location) {
        baseLocations.put(playerId, location);
    }

    public static void spawnUnit(ServerLevel level, UUID playerId, String unitType, String faction) {
        long currentTime = System.currentTimeMillis();
        Long lastSpawn = lastSpawnTime.getOrDefault(playerId, 0L);

        long spawnDelay = BASE_SPAWN_DELAY - (SpawnPointManager.getSpawnPointCount(playerId) * 2000);
        spawnDelay = Math.max(spawnDelay, 1000);

        if (currentTime - lastSpawn >= spawnDelay) {
            Vec3 basePos = baseLocations.get(playerId);
            if (basePos != null) {
                Vec3 spawnOffset = basePos.add(Math.random() * 3 - 1.5, 0, Math.random() * 3 - 1.5);

                RTSUnitEntity unit = new RTSUnitEntity(null, level);
                unit.moveTo(spawnOffset.x, spawnOffset.y, spawnOffset.z, 0, 0);
                unit.setFaction(faction);
                level.addFreshEntity(unit);

                lastSpawnTime.put(playerId, currentTime);
            }
        }
    }

    public static Queue<String> getProductionQueue(UUID playerId) {
        return productionQueues.getOrDefault(playerId, new LinkedList<>());
    }
}