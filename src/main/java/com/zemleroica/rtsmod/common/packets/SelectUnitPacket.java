package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class SelectUnitPacket {
    private int unitId;

    public SelectUnitPacket(int unitId) {
        this.unitId = unitId;
    }

    public SelectUnitPacket() {}

    public static void toBytes(SelectUnitPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.unitId);
    }

    public static SelectUnitPacket fromBytes(FriendlyByteBuf buf) {
        return new SelectUnitPacket(buf.readInt());
    }

    public static void handle(SelectUnitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.level().getEntity(msg.unitId);
                if (entity != null) {
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}