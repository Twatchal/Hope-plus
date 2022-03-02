package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WurstplusViewmodleChanger extends WurstplusHack
{
    WurstplusSetting custom_fov;
    WurstplusSetting items;
    WurstplusSetting viewmodle_fov;
    WurstplusSetting normal_offset;
    WurstplusSetting offset;
    WurstplusSetting offset_x;
    WurstplusSetting offset_y;
    WurstplusSetting main_x;
    WurstplusSetting main_y;
    private float fov;
    
    public WurstplusViewmodleChanger() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.custom_fov = this.create("FOV", "FOVSlider", 130, 110, 170);
        this.items = this.create("Items", "FOVItems", false);
        this.viewmodle_fov = this.create("Items FOV", "ItemsFOVSlider", 130, 110, 170);
        this.normal_offset = this.create("Offset", "FOVOffset", true);
        this.offset = this.create("Offset Main", "FOVOffsetMain", 0.7, 0.0, 1.0);
        this.offset_x = this.create("Offset X", "FOVOffsetX", 0.0, -1.0, 1.0);
        this.offset_y = this.create("Offset Y", "FOVOffsetY", 0.0, -1.0, 1.0);
        this.main_x = this.create("Main X", "FOVMainX", 0.0, -1.0, 1.0);
        this.main_y = this.create("Main Y", "FOVMainY", 0.0, -1.0, 1.0);
        this.name = "Custom Viewmodel";
        this.tag = "CustomViewmodel";
        this.description = "anti chad";
    }
    
    protected void enable() {
        this.fov = WurstplusViewmodleChanger.mc.field_71474_y.field_74334_X;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    protected void disable() {
        WurstplusViewmodleChanger.mc.field_71474_y.field_74334_X = this.fov;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void update() {
        WurstplusViewmodleChanger.mc.field_71474_y.field_74334_X = (float)this.custom_fov.get_value(1);
    }
    
    @SubscribeEvent
    public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
        if (this.items.get_value(true)) {
            m.setFOV((float)this.viewmodle_fov.get_value(1));
        }
    }
}
