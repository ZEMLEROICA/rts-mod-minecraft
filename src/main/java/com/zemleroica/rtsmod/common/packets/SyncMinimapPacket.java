package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;

public class SyncMinimapPacket {
    private int unitX, unitY, unitZ;
    private String unitTeam;
    private int unitId;

    public SyncMinimapPacket(int unitId, int x, int y, int z, String team) {
        this.unitId = unitId;
        this.unitX = x;
        this.unitY = y;
        this.unitZ = z;
        this.unitTeam = team;
    }

    public SyncMinimapPacket() {}

    public static void toBytes(SyncMinimapPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.unitId);
        buf.writeInt(msg.unitX);
        buf.writeInt(msg.unitY);
        buf.writeInt(msg.unitZ);
        buf.writeUtf(msg.unitTeam);
    }

    public static SyncMinimapPacket fromBytes(FriendlyByteBuf buf) {
        return new SyncMinimapPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readUtf());
    }

    public static void handle(SyncMinimapPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
        });
        ctx.get().setPacketHandled(true);
    }
}