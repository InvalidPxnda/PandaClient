package de.pxndamc.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NameOverlay extends Screen {
    protected NameOverlay(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

    }

}
