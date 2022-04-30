package de.pxndamc.client.cosmetics;

import de.pxndamc.client.cosmetics.capes.CapeManager;
import org.checkerframework.checker.units.qual.C;

public class CosmeticsManager {

    private CapeManager capeManager;

    public void init() {
        this.capeManager = new CapeManager();

    }

    public CapeManager getCapeManager() {
        return capeManager;
    }
}
