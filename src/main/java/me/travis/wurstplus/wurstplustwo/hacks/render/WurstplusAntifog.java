package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.client.renderer.*;

public class WurstplusAntifog extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventSetupFog> setup_fog;
    
    public WurstplusAntifog() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.setup_fog = (Listener<WurstplusEventSetupFog>)new Listener(event -> {
            event.cancel();
            WurstplusAntifog.mc.field_71460_t.func_191514_d(false);
            GlStateManager.func_187432_a(0.0f, -1.0f, 0.0f);
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.func_179104_a(1028, 4608);
        }, new Predicate[0]);
        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }
}
