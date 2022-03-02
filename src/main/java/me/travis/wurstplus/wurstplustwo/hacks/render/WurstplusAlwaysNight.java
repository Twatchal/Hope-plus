package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;

public class WurstplusAlwaysNight extends WurstplusHack
{
    @EventHandler
    private Listener<WurstplusEventRender> on_render;
    
    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.on_render = (Listener<WurstplusEventRender>)new Listener(event -> {
            if (WurstplusAlwaysNight.mc.field_71441_e == null) {
                return;
            }
            WurstplusAlwaysNight.mc.field_71441_e.func_72877_b(18000L);
        }, new Predicate[0]);
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
