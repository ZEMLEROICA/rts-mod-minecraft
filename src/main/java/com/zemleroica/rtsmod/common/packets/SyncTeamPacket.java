package com.zemleroica.rtsmod.common.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;
import net.minecraft.network.FriendlyByteBuf;

public class SyncTeamPacket {
    private String teamName;
    private int teamColor;
    private String playerName;

    public SyncTeamPacket(String teamName, int teamColor, String playerName) {
        this.teamName = teamName;
        this.teamColor = teamColor;
        this.playerName = playerName;
    }

    public SyncTeamPacket() {}

    public static void toBytes(SyncTeamPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.teamName);
        buf.writeInt(msg.teamColor);
        buf.writeUtf(msg.playerName);
    }

    public static SyncTeamPacket fromBytes(FriendlyByteBuf buf) {
        return new SyncTeamPacket(buf.readUtf(), buf.readInt(), buf.readUtf());
    }

    public static void handle(SyncTeamPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
        });
        ctx.get().setPacketHandled(true);
    }
}