package com.zemleroica.rtsmod.client;

import com.zemleroica.rtsmod.common.TeamManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class MinimapRenderer {
    private static final int MINIMAP_SIZE = 150;
    private static final int MINIMAP_SCALE = 1;
    private static DynamicTexture minimapTexture;
    private static Map<Integer, MinimapUnit> minimapUnits = new HashMap<>();

    public static class MinimapUnit {
        public int x, z;
        public int color;
        public String teamName;

        public MinimapUnit(int x, int z, int color, String teamName) {
            this.x = x;
            this.z = z;
            this.color = color;
            this.teamName = teamName;
        }
    }

    public static void addUnit(int unitId, Entity entity, String teamName) {
        int color = TeamManager.getTeamColor(entity.getUUID());
        minimapUnits.put(unitId, new MinimapUnit(
            (int) entity.getX(),
            (int) entity.getZ(),
            color,
            teamName
        ));
    }

    public static void updateUnit(int unitId, double x, double z) {
        MinimapUnit unit = minimapUnits.get(unitId);
        if (unit != null) {
            unit.x = (int) x;
            unit.z = (int) z;
        }
    }

    public static void removeUnit(int unitId) {
        minimapUnits.remove(unitId);
    }

    public static void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight, Minecraft minecraft) {
        int minimapX = screenWidth - MINIMAP_SIZE - 10;
        int minimapY = 10;

        guiGraphics.fill(minimapX, minimapY, minimapX + MINIMAP_SIZE, minimapY + MINIMAP_SIZE, 0xFF000000);
        guiGraphics.drawString(minecraft.font, "Minimap", minimapX + 5, minimapY - 12, 0xFFFFFF);

        Entity playerEntity = minecraft.player;
        if (playerEntity != null) {
            int playerX = (int) playerEntity.getX();
            int playerZ = (int) playerEntity.getZ();

            for (MinimapUnit unit : minimapUnits.values()) {
                int relX = (unit.x - playerX) / MINIMAP_SCALE;
                int relZ = (unit.z - playerZ) / MINIMAP_SCALE;

                int screenX = minimapX + MINIMAP_SIZE / 2 + relX;
                int screenY = minimapY + MINIMAP_SIZE / 2 + relZ;

                if (screenX >= minimapX && screenX < minimapX + MINIMAP_SIZE &&
                    screenY >= minimapY && screenY < minimapY + MINIMAP_SIZE) {
                    guiGraphics.fill(screenX, screenY, screenX + 2, screenY + 2, unit.color);
                }
            }

            guiGraphics.fill(minimapX + MINIMAP_SIZE / 2 - 1, minimapY + MINIMAP_SIZE / 2 - 1,
                minimapX + MINIMAP_SIZE / 2 + 1, minimapY + MINIMAP_SIZE / 2 + 1, 0xFFFFFF);
        }
    }
}