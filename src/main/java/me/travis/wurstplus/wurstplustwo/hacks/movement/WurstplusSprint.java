package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;

public class WurstplusSprint extends WurstplusHack
{
    WurstplusSetting rage;
    
    public WurstplusSprint() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.rage = this.create("Rage", "SprintRage", true);
        this.name = "Sprint";
        this.tag = "Sprint";
        this.description = "ZOOOOOOOOM";
    }
    
    public void update() {
        if (WurstplusSprint.mc.field_71439_g == null) {
            return;
        }
        if (this.rage.get_value(true) && (WurstplusSprint.mc.field_71439_g.field_191988_bg != 0.0f || WurstplusSprint.mc.field_71439_g.field_70702_br != 0.0f)) {
            WurstplusSprint.mc.field_71439_g.func_70031_b(true);
        }
        else {
            WurstplusSprint.mc.field_71439_g.func_70031_b(WurstplusSprint.mc.field_71439_g.field_191988_bg > 0.0f || WurstplusSprint.mc.field_71439_g.field_70702_br > 0.0f);
        }
    }
}
