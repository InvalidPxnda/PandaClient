package de.pxndamc.client;

import de.pxndamc.Variables;
import de.pxndamc.client.gui.overlays.FPSOverlay;
import de.pxndamc.client.gui.overlays.OverlayManager;
import de.pxndamc.client.gui.screens.WelcomeScreen;
import de.pxndamc.discord.RPC;
import net.minecraft.client.Minecraft;

public class Client {

    private static boolean initialized = false;

    public static String discordUrl = "https://discord.gg/---";

    public static Minecraft minecraft;
    public static OverlayManager overlayManager;

    public static void init(Minecraft minecraft) {
        Client.minecraft = minecraft;
        overlayManager = new OverlayManager();
        minecraft.setScreen(new WelcomeScreen());
        initialized = true;

    }

    public static void tick() {
        if(minecraft != null)
            overlayManager.showOverlay(FPSOverlay.class);
        else
            overlayManager.hideOverlay(FPSOverlay.class);
    }


    public static void resetRPC() {
        RPC.instance().update("Main Menu", "Idling", "", "");
        Variables.server = false;
        System.out.println("DiscordRPC - Updated [MAIN MENU]!");
    }

    public static boolean isInitialized() {
        return initialized;
    }
}




