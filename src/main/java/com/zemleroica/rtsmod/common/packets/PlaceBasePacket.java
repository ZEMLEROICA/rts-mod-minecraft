package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import com.zemleroica.rtsmod.entities.EntityRegistry;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

public class PlaceBasePacket {
    private int x, y, z;
    private String faction;

    public PlaceBasePacket(int x, int y, int z, String faction) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.faction = faction;
    }

    public PlaceBasePacket() {}

    public static void toBytes(PlaceBasePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.x);
        buf.writeInt(msg.y);
        buf.writeInt(msg.z);
        buf.writeUtf(msg.faction);
    }

    public static PlaceBasePacket fromBytes(FriendlyByteBuf buf) {
        return new PlaceBasePacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readUtf());
    }

    public static void handle(PlaceBasePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
            }
        });
        ctx.get().setPacketHandled(true);
    }
}