package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.turok.draw.*;

public class WurstplusCityEsp extends WurstplusHack
{
    WurstplusSetting endcrystal_mode;
    WurstplusSetting mode;
    WurstplusSetting off_set;
    WurstplusSetting range;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    List<BlockPos> blocks;
    boolean outline;
    boolean solid;
    
    public WurstplusCityEsp() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.endcrystal_mode = this.create("EndCrystal", "CityEndCrystal", false);
        this.mode = this.create("Mode", "CityMode", "Pretty", this.combobox(new String[] { "Pretty", "Solid", "Outline" }));
        this.off_set = this.create("Height", "CityOffSetSide", 0.2, 0.0, 1.0);
        this.range = this.create("Range", "CityRange", 6, 1, 12);
        this.r = this.create("R", "CityR", 0, 0, 255);
        this.g = this.create("G", "CityG", 255, 0, 255);
        this.b = this.create("B", "CityB", 0, 0, 255);
        this.a = this.create("A", "CityA", 50, 0, 255);
        this.blocks = new ArrayList<BlockPos>();
        this.outline = false;
        this.solid = false;
        this.name = "CityESP";
        this.tag = "City ESP";
        this.description = "jumpy isnt gonna be happy about this";
    }
    
    public void update() {
        this.blocks.clear();
        for (final EntityPlayer player : WurstplusCityEsp.mc.field_71441_e.field_73010_i) {
            if (WurstplusCityEsp.mc.field_71439_g.func_70032_d((Entity)player) <= this.range.get_value(1)) {
                if (WurstplusCityEsp.mc.field_71439_g == player) {
                    continue;
                }
                final BlockPos p = WurstplusEntityUtil.is_cityable(player, this.endcrystal_mode.get_value(true));
                if (p == null) {
                    continue;
                }
                this.blocks.add(p);
            }
        }
    }
    
    public void render(final WurstplusEventRender event) {
        final float off_set_h = (float)this.off_set.get_value(1.0);
        for (final BlockPos pos : this.blocks) {
            if (this.mode.in("Pretty")) {
                this.outline = true;
                this.solid = true;
            }
            if (this.mode.in("Solid")) {
                this.outline = false;
                this.solid = true;
            }
            if (this.mode.in("Outline")) {
                this.outline = true;
                this.solid = false;
            }
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (this.outline) {
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
        }
    }
}
