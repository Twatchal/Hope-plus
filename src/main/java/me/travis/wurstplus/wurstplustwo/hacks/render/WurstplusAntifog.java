package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSetupFog;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class WurstplusAntifog
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventSetupFog> setup_fog = new Listener(event -> {
        event.cancel();
        WurstplusAntifog.mc.field_71460_t.func_191514_d(false);
        GlStateManager.func_187432_a((float)0.0f, (float)-1.0f, (float)0.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179104_a((int)1028, (int)4608);
    }
    , new Predicate[0]);

    public WurstplusAntifog() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }
}
