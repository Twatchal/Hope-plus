package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;

public class WurstplusCapes extends WurstplusHack
{
    WurstplusSetting cape;
    
    public WurstplusCapes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.cape = this.create("Cape", "CapeCape", "New", this.combobox(new String[] { "New", "OG" }));
        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }
}
