package me.travis.wurstplus.wurstplustwo.hacks.dev;

import net.minecraft.client.entity.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class WurstplusFakePlayer extends WurstplusHack
{
    private EntityOtherPlayerMP fake_player;
    
    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Fake Player";
        this.tag = "FakePlayer";
        this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }
    
    protected void enable() {
        (this.fake_player = new EntityOtherPlayerMP((World)WurstplusFakePlayer.mc.field_71441_e, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "Twatchal"))).func_82149_j((Entity)WurstplusFakePlayer.mc.field_71439_g);
        this.fake_player.field_70759_as = WurstplusFakePlayer.mc.field_71439_g.field_70759_as;
        WurstplusFakePlayer.mc.field_71441_e.func_73027_a(-100, (Entity)this.fake_player);
    }
    
    protected void disable() {
        try {
            WurstplusFakePlayer.mc.field_71441_e.func_72900_e((Entity)this.fake_player);
        }
        catch (Exception ex) {}
    }
}
