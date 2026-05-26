package com.zemleroica.rtsmod.common.packets;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import com.zemleroica.rtsmod.RTSMod;

public class NetworkHandler {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
            new net.minecraft.resources.ResourceLocation(RTSMod.MOD_ID, "main"),
            () -> "1.0",
            s -> true,
            s -> true
        );

        INSTANCE.messageBuilder(SelectUnitPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .decoder(SelectUnitPacket::fromBytes)
            .encoder(SelectUnitPacket::toBytes)
            .consumerMainThread(SelectUnitPacket::handle)
            .add();

        INSTANCE.messageBuilder(OrderUnitPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .decoder(OrderUnitPacket::fromBytes)
            .encoder(OrderUnitPacket::toBytes)
            .consumerMainThread(OrderUnitPacket::handle)
            .add();

        INSTANCE.messageBuilder(PlaceBasePacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .decoder(PlaceBasePacket::fromBytes)
            .encoder(PlaceBasePacket::toBytes)
            .consumerMainThread(PlaceBasePacket::handle)
            .add();

        INSTANCE.messageBuilder(PlaceSpawnPointPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
            .decoder(PlaceSpawnPointPacket::fromBytes)
            .encoder(PlaceSpawnPointPacket::toBytes)
            .consumerMainThread(PlaceSpawnPointPacket::handle)
            .add();

        INSTANCE.messageBuilder(SyncFactionPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(SyncFactionPacket::fromBytes)
            .encoder(SyncFactionPacket::toBytes)
            .consumerMainThread(SyncFactionPacket::handle)
            .add();

        INSTANCE.messageBuilder(SyncMinimapPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(SyncMinimapPacket::fromBytes)
            .encoder(SyncMinimapPacket::toBytes)
            .consumerMainThread(SyncMinimapPacket::handle)
            .add();

        INSTANCE.messageBuilder(SyncTeamPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
            .decoder(SyncTeamPacket::fromBytes)
            .encoder(SyncTeamPacket::toBytes)
            .consumerMainThread(SyncTeamPacket::handle)
            .add();
    }

    public static SimpleChannel getInstance() {
        return INSTANCE;
    }
}