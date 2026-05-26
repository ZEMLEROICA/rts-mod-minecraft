package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PlaceSpawnPointPacket {
    private int x, y, z;

    public PlaceSpawnPointPacket(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlaceSpawnPointPacket() {}

    public static void toBytes(PlaceSpawnPointPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
    }

    public static PlaceSpawnPointPacket fromBytes(FriendlyByteBuf buf) {
        return new PlaceSpawnPointPacket(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(PlaceSpawnPointPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                SpawnPointManager.addSpawnPoint(player.getUUID(), msg.x, msg.y, msg.z);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}