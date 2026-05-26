package com.zemleroica.rtsmod.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.zemleroica.rtsmod.common.FactionManager;
import com.zemleroica.rtsmod.data.Faction;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class FactionSelectScreen extends Screen {
    private List<Faction> factions = new ArrayList<>();
    private Faction selectedFaction;

    public FactionSelectScreen() {
        super(Component.literal("Select Faction"));
    }

    @Override
    protected void init() {
        this.clearWidgets();
        Collection<Faction> allFactions = FactionManager.getAllFactions();
        this.factions.addAll(allFactions);

        int startY = 80;
        int buttonWidth = 200;
        int buttonHeight = 20;

        for (int i = 0; i < this.factions.size(); i++) {
            Faction faction = this.factions.get(i);
            int index = i;
            this.addRenderableWidget(Button.builder(Component.literal(faction.name), button -> {
                selectedFaction = faction;
            }).bounds(this.width / 2 - buttonWidth / 2, startY + (i * (buttonHeight + 10)), buttonWidth, buttonHeight)
                .build());
        }

        this.addRenderableWidget(Button.builder(Component.literal("Confirm"), button -> {
            if (this.selectedFaction != null) {
                this.onClose();
            }
        }).bounds(this.width / 2 - 100, this.height - 40, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        if (this.selectedFaction != null) {
            graphics.drawString(this.font, "Selected: " + this.selectedFaction.name, 20, 50, 0xAAAAAA);
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}