package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;

public class SyncFactionPacket {
    private String factionName;

    public SyncFactionPacket(String factionName) {
        this.factionName = factionName;
    }

    public SyncFactionPacket() {}

    public static void toBytes(SyncFactionPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.factionName);
    }

    public static SyncFactionPacket fromBytes(FriendlyByteBuf buf) {
        return new SyncFactionPacket(buf.readUtf());
    }

    public static void handle(SyncFactionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
        });
        ctx.get().setPacketHandled(true);
    }
}