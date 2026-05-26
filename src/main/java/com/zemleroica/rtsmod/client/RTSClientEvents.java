package com.zemleroica.rtsmod.client;

import java.util.*;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.zemleroica.rtsmod.RTSMod;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@Mod.EventBusSubscriber(modid = RTSMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RTSClientEvents {
    private static final Set<Integer> selectedUnits = new HashSet<>();
    private static int selectionStartX;
    private static int selectionStartY;
    private static boolean isSelecting = false;

    @SubscribeEvent
    public static void onMouseClick(InputEvent.MouseClickedEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return;

        if (event.getButton() == 0 && event.getAction() == 1) {
            HitResult hitResult = mc.hitResult;
            if (hitResult instanceof EntityHitResult entityHitResult) {
                Entity entity = entityHitResult.getEntity();
                if (isRTSUnit(entity)) {
                    selectedUnits.clear();
                    selectedUnits.add(entity.getId());
                    entity.setGlowing(true);
                }
            }
            isSelecting = true;
            selectionStartX = event.getX();
            selectionStartY = event.getY();
        }
    }

    private static boolean isRTSUnit(Entity entity) {
        return entity.getType().toString().contains("rts_unit");
    }

    public static Set<Integer> getSelectedUnits() {
        return selectedUnits;
    }

    public static void clearSelection() {
        selectedUnits.clear();
    }
}