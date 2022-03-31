package me.travis.wurstplus.wurstplustwo.hacks.dev;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class WurstplusFakePlayer
extends WurstplusHack {
    private EntityOtherPlayerMP fake_player;

    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Fake Player";
        this.tag = "FakePlayer";
        this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }

    protected void enable() {
        this.fake_player = new EntityOtherPlayerMP((World)WurstplusFakePlayer.mc.field_71441_e, new GameProfile(UUID.fromString((String)"a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "Twatchal"));
        this.fake_player.func_82149_j((Entity)WurstplusFakePlayer.mc.field_71439_g);
        this.fake_player.field_70759_as = WurstplusFakePlayer.mc.field_71439_g.field_70759_as;
        WurstplusFakePlayer.mc.field_71441_e.func_73027_a(-100, (Entity)this.fake_player);
    }

    protected void disable() {
        try {
            WurstplusFakePlayer.mc.field_71441_e.func_72900_e((Entity)this.fake_player);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
