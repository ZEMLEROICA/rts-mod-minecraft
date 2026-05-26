package com.zemleroica.rtsmod.gui;

import com.zemleroica.rtsmod.common.packets.NetworkHandler;
import com.zemleroica.rtsmod.common.packets.PlaceBasePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BasePlacementScreen extends Screen {
    private String faction;
    private int selectedX = -1, selectedY = -1, selectedZ = -1;

    public BasePlacementScreen(String faction) {
        super(Component.literal("Place Your Base"));
        this.faction = faction;
    }

    @Override
    protected void init() {
        this.clearWidgets();
        this.addRenderableWidget(Button.builder(Component.literal("Click a block to place base"), button -> {})
            .bounds(this.width / 2 - 100, this.height / 2, 200, 20)
            .build());

        if (selectedX != -1) {
            this.addRenderableWidget(Button.builder(Component.literal("Confirm"), button -> {
                NetworkHandler.getInstance().sendToServer(new PlaceBasePacket(selectedX, selectedY, selectedZ, faction));
                this.onClose();
            }).bounds(this.width / 2 - 100, this.height / 2 + 30, 200, 20).build());
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        HitResult hitResult = this.minecraft.hitResult;
        if (hitResult instanceof BlockHitResult blockHit) {
            this.selectedX = blockHit.getBlockPos().getX();
            this.selectedY = blockHit.getBlockPos().getY();
            this.selectedZ = blockHit.getBlockPos().getZ();
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        if (selectedX != -1) {
            graphics.drawString(this.font, "Position: " + selectedX + ", " + selectedY + ", " + selectedZ, 20, 50, 0xAAAAAA);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}