package com.zemleroica.rtsmod.common;

import java.util.*;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.zemleroica.rtsmod.RTSMod;

import net.minecraft.server.level.ServerPlayer;

@Mod.EventBusSubscriber(modid = RTSMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RTSCommonEvents {
    private static final Set<UUID> playersWithBases = new HashSet<>();

    @SubscribeEvent
    public static void onServerStart(ServerStartedEvent event) {
        FactionManager.loadFactions(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide) {
            if (!playersWithBases.contains(player.getUUID())) {
                playersWithBases.add(player.getUUID());
            }
        }
    }
}