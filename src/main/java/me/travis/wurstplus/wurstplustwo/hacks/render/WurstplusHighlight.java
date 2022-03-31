package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import java.util.List;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class WurstplusHighlight
extends WurstplusHack {
    WurstplusSetting mode;
    WurstplusSetting rgb;
    WurstplusSetting r;
    WurstplusSetting g;
    WurstplusSetting b;
    WurstplusSetting a;
    WurstplusSetting l_a;
    int color_r;
    int color_g;
    int color_b;
    boolean outline;
    boolean solid;

    public WurstplusHighlight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "HighlightMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
        this.rgb = this.create("RGB Effect", "HighlightRGBEffect", true);
        this.r = this.create("R", "HighlightR", 255, 0, 255);
        this.g = this.create("G", "HighlightG", 255, 0, 255);
        this.b = this.create("B", "HighlightB", 255, 0, 255);
        this.a = this.create("A", "HighlightA", 100, 0, 255);
        this.l_a = this.create("Outline A", "HighlightLineA", 255, 0, 255);
        this.outline = false;
        this.solid = false;
        this.name = "Block Highlight";
        this.tag = "BlockHighlight";
        this.description = "see what block ur targeting";
    }

    public void disable() {
        this.outline = false;
        this.solid = false;
    }

    public void render(WurstplusEventRender event) {
        if (WurstplusHighlight.mc.field_71439_g != null && WurstplusHighlight.mc.field_71441_e != null) {
            RayTraceResult result;
            float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int color_rgb = Color.HSBtoRGB((float)tick_color[0], (float)1.0f, (float)1.0f);
            if (this.rgb.get_value(true)) {
                this.color_r = color_rgb >> 16 & 255;
                this.color_g = color_rgb >> 8 & 255;
                this.color_b = color_rgb & 255;
                this.r.set_value(this.color_r);
                this.g.set_value(this.color_g);
                this.b.set_value(this.color_b);
            } else {
                this.color_r = this.r.get_value(1);
                this.color_g = this.g.get_value(2);
                this.color_b = this.b.get_value(3);
            }
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
            if ((result = WurstplusHighlight.mc.field_71476_x) != null && result.field_72313_a == RayTraceResult.Type.BLOCK) {
                BlockPos block_pos = result.func_178782_a();
                if (this.solid) {
                    RenderHelp.prepare((String)"quads");
                    RenderHelp.draw_cube((BlockPos)block_pos, (int)this.color_r, (int)this.color_g, (int)this.color_b, (int)this.a.get_value(1), (String)"all");
                    RenderHelp.release();
                }
                if (this.outline) {
                    RenderHelp.prepare((String)"lines");
                    RenderHelp.draw_cube_line((BlockPos)block_pos, (int)this.color_r, (int)this.color_g, (int)this.color_b, (int)this.l_a.get_value(1), (String)"all");
                    RenderHelp.release();
                }
            }
        }
    }
}
