package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.List;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusCityEsp
extends WurstplusHack {
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
        this.mode = this.create("Mode", "CityMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
        this.off_set = this.create("Height", "CityOffSetSide", 0.2, 0.0, 1.0);
        this.range = this.create("Range", "CityRange", 6, 1, 12);
        this.r = this.create("R", "CityR", 0, 0, 255);
        this.g = this.create("G", "CityG", 255, 0, 255);
        this.b = this.create("B", "CityB", 0, 0, 255);
        this.a = this.create("A", "CityA", 50, 0, 255);
        this.blocks = new ArrayList();
        this.outline = false;
        this.solid = false;
        this.name = "CityESP";
        this.tag = "City ESP";
        this.description = "jumpy isnt gonna be happy about this";
    }

    public void update() {
        this.blocks.clear();
        for (EntityPlayer player : WurstplusCityEsp.mc.field_71441_e.field_73010_i) {
            BlockPos p;
            if (WurstplusCityEsp.mc.field_71439_g.func_70032_d((Entity)player) > (float)this.range.get_value(1) || WurstplusCityEsp.mc.field_71439_g == player || (p = WurstplusEntityUtil.is_cityable((EntityPlayer)player, (boolean)this.endcrystal_mode.get_value(true))) == null) continue;
            this.blocks.add((Object)p);
        }
    }

    public void render(WurstplusEventRender event) {
        float off_set_h = (float)this.off_set.get_value(1.0);
        for (BlockPos pos : this.blocks) {
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
                RenderHelp.prepare((String)"quads");
                RenderHelp.draw_cube((BufferBuilder)RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), (float)1.0f, (float)off_set_h, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a.get_value(1), (String)"all");
                RenderHelp.release();
            }
            if (!this.outline) continue;
            RenderHelp.prepare((String)"lines");
            RenderHelp.draw_cube_line((BufferBuilder)RenderHelp.get_buffer_build(), (float)pos.func_177958_n(), (float)pos.func_177956_o(), (float)pos.func_177952_p(), (float)1.0f, (float)off_set_h, (float)1.0f, (int)this.r.get_value(1), (int)this.g.get_value(1), (int)this.b.get_value(1), (int)this.a.get_value(1), (String)"all");
            RenderHelp.release();
        }
    }
}
