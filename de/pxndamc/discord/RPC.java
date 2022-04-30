package de.pxndamc.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import de.pxndamc.Variables;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.lwjgl.system.CallbackI;

public class RPC {

    private final  DiscordRPC lib = DiscordRPC.INSTANCE;
    private final String largeImageKey$final;
    private final String largeImageText$final;
    private String largeImageKey;
    private  String largeImageText;
    private RPCCache rpcCache;
    private RPCParty rpcParty;


    private boolean party = false;
    private static boolean created = false;
    private long start;
    private Thread worker;

    public RPC(String largeImageKey, String largeImageText) {
        this.largeImageKey$final = largeImageKey;
        this.largeImageText$final = largeImageText;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.start = -1;
    }

    public RPC create() {
        String applicationID = "968488956310208542";
        String steamID = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("DiscordRPC - Ready!");
        lib.Discord_Initialize(applicationID, handlers, true, steamID);
        worker = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        },"RPC-Callback-Handler");
        worker.start();
        return this;
    }

    public void destroy() {
        worker.interrupt();
        worker = null;
        party = false;
        rpcCache = null;
        rpcParty = null;



        start = -1;
        lib.Discord_Shutdown();
    }

    public void update(String details, String state, String smallImageKey, String smallImageText) {
        DiscordRichPresence presence = new DiscordRichPresence();
        if (start == 1)
            start = System.currentTimeMillis() / 1000;
        presence.startTimestamp = start;
        presence.details = details;
        presence.state = state;
        presence.largeImageKey = largeImageKey;
        presence.largeImageText = largeImageText;
        presence.smallImageKey = smallImageKey;
        presence.smallImageText = smallImageText;
        if (party) {
            presence.partyId = rpcParty.getId();
            presence.partySize = rpcParty.getSize();
            presence.partyMax = rpcParty.getMax();
            presence.joinSecret = rpcParty.getJoinsecret();
        }

        lib.Discord_UpdatePresence(presence);
        rpcCache = new RPCCache(details, state, smallImageKey, smallImageText);
    }


    public RPC setupParty(int max) {
        return setupParty(new RPCParty(max));
    }

    public RPC setupParty(RPCParty rpcParty) {
        this.rpcParty = rpcParty;
        Variables.rpcParty = rpcParty;
        party = true;

        return this;
    }

    public void destroyParty() {
        this.rpcParty = null;
        Variables.rpcParty = null;
        party = false;



    }

    public void setLargeImageKey(String key) {
        this.largeImageKey = key;
    }

    public void resetLargeImageKey() {
        this.largeImageKey = this.largeImageKey$final;
    }

    public void setLargeImageText(String text) {
        this.largeImageText = text;
    }

    public void resetLargeImageText() {
        this.largeImageText = this.largeImageText$final;
    }



    public static RPC instance() {
        if (!created) {
            created = true;
            return Variables.rpc.create();
        }
        return Variables.rpc;
    }


}