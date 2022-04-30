package de.pxndamc.client.gui.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sun.jdi.InvocationException;
import de.pxndamc.client.Client;
import de.pxndamc.client.utils.Touch;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class OverlayManager {

    private final ConcurrentHashMap<Class<?>, IOverlay> overlays = new  ConcurrentHashMap<>();
    private Touch<IOverlay> touch;

    public OverlayManager init() {
        touch = new Touch<>();
        return this;
    }

    public void showOverlay(Class<?> clazz) {
        try {
            overlays.put(clazz, Objects.requireNonNull(touch.touch(clazz)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideOverlay(Class<?> clazz) {
        overlays.remove(clazz);
    }

    public void render(PoseStack poseStack, int pMouseX, int pMouseY, float pPartialTick) {
        for(Class<?> clazz : overlays.keySet())
            overlays.get(clazz).render(poseStack, Client.minecraft.font, pMouseX, pMouseY, pPartialTick);

    }

}

