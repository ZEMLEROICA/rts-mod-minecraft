package com.zemleroica.rtsmod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafxmod.FMLJavaModLoadingContext;

import com.zemleroica.rtsmod.client.RTSClientEvents;
import com.zemleroica.rtsmod.common.RTSCommonEvents;
import com.zemleroica.rtsmod.common.packets.NetworkHandler;
import com.zemleroica.rtsmod.entities.EntityRegistry;

@Mod(RTSMod.MOD_ID)
public class RTSMod {
    public static final String MOD_ID = "rtsmod";

    public RTSMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.getInstance().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        EntityRegistry.register(modEventBus);

        forgeEventBus.register(RTSCommonEvents.class);
        forgeEventBus.register(new RTSClientEvents());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RTSClientEvents.class);
    }
}