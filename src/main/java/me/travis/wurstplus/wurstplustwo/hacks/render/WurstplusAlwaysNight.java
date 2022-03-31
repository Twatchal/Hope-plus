package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

public class WurstplusAlwaysNight
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventRender> on_render = new Listener(event -> {
        if (WurstplusAlwaysNight.mc.field_71441_e == null) {
            return;
        }
        WurstplusAlwaysNight.mc.field_71441_e.func_72877_b(18000L);
    }
    , new Predicate[0]);

    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }

    public void update() {
        if (WurstplusAlwaysNight.mc.field_71441_e == null) {
            return;
        }
        WurstplusAlwaysNight.mc.field_71441_e.func_72877_b(18000L);
    }
}
