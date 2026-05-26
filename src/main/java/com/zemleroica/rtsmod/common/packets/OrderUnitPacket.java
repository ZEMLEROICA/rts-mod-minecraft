package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import com.zemleroica.rtsmod.entities.RTSUnitEntity;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class OrderUnitPacket {
    private int unitId;
    private String order;
    private double targetX, targetY, targetZ;

    public OrderUnitPacket(int unitId, String order, double x, double y, double z) {
        this.unitId = unitId;
        this.order = order;
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
    }

    public OrderUnitPacket() {}

    public static void toBytes(OrderUnitPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.unitId);
        buf.writeUtf(msg.order);
        buf.writeDouble(msg.targetX);
        buf.writeDouble(msg.targetY);
        buf.writeDouble(msg.targetZ);
    }

    public static OrderUnitPacket fromBytes(FriendlyByteBuf buf) {
        return new OrderUnitPacket(buf.readInt(), buf.readUtf(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(OrderUnitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.level().getEntity(msg.unitId);
                if (entity instanceof RTSUnitEntity unit) {
                    RTSUnitEntity.OrderType orderType = RTSUnitEntity.OrderType.valueOf(msg.order);
                    unit.setOrder(orderType);

                    if (orderType == RTSUnitEntity.OrderType.MOVE) {
                        unit.moveToTarget(new Vec3(msg.targetX, msg.targetY, msg.targetZ));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}