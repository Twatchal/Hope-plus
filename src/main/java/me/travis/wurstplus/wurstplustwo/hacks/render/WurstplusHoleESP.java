package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.travis.turok.draw.*;

public class WurstplusHoleESP extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting off_set;
    WurstplusSetting range;
    WurstplusSetting hide_own;
    WurstplusSetting bedrock_view;
    WurstplusSetting bedrock_enable;
    WurstplusSetting rb;
    WurstplusSetting gb;
    WurstplusSetting bb;
    WurstplusSetting ab;
    WurstplusSetting obsidian_view;
    WurstplusSetting obsidian_enable;
    WurstplusSetting ro;
    WurstplusSetting go;
    WurstplusSetting bo;
    WurstplusSetting ao;
    WurstplusSetting line_a;
    ArrayList<WurstplusPair<BlockPos, Boolean>> holes;
    boolean outline;
    boolean solid;
    boolean docking;
    int color_r_o;
    int color_g_o;
    int color_b_o;
    int color_r_b;
    int color_g_b;
    int color_b_b;
    int color_r;
    int color_g;
    int color_b;
    int color_a;
    int safe_sides;
    
    public WurstplusHoleESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.mode = this.create("Mode", "HoleESPMode", "Pretty", this.combobox(new String[] { "Pretty", "Solid", "Outline" }));
        this.off_set = this.create("Height", "HoleESPOffSetSide", 0.25, 0.0, 1.0);
        this.range = this.create("Range", "HoleESPRange", 6, 1, 12);
        this.hide_own = this.create("Hide Own", "HoleESPHideOwn", true);
        this.bedrock_view = this.create("info", "HoleESPbedrock", "Bedrock");
        this.bedrock_enable = this.create("Bedrock Holes", "HoleESPBedrockHoles", true);
        this.rb = this.create("R", "HoleESPRb", 32, 0, 255);
        this.gb = this.create("G", "HoleESPGb", 255, 0, 255);
        this.bb = this.create("B", "HoleESPBb", 255, 0, 255);
        this.ab = this.create("A", "HoleESPAb", 50, 0, 255);
        this.obsidian_view = this.create("info", "HoleESPObsidian", "Obsidian");
        this.obsidian_enable = this.create("Obsidian Holes", "HoleESPObsidianHoles", true);
        this.ro = this.create("R", "HoleESPRo", 255, 0, 255);
        this.go = this.create("G", "HoleESPGo", 0, 0, 255);
        this.bo = this.create("B", "HoleESPBo", 0, 0, 255);
        this.ao = this.create("A", "HoleESPAo", 50, 0, 255);
        this.line_a = this.create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);
        this.holes = new ArrayList<WurstplusPair<BlockPos, Boolean>>();
        this.outline = false;
        this.solid = false;
        this.docking = false;
        this.name = "Hole ESP";
        this.tag = "HoleESP";
        this.description = "lets you know where holes are";
    }
    
    public void update() {
        this.color_r_b = this.rb.get_value(1);
        this.color_g_b = this.gb.get_value(1);
        this.color_b_b = this.bb.get_value(1);
        this.color_r_o = this.ro.get_value(1);
        this.color_g_o = this.go.get_value(1);
        this.color_b_o = this.bo.get_value(1);
        this.holes.clear();
        if (WurstplusHoleESP.mc.field_71439_g != null || WurstplusHoleESP.mc.field_71441_e != null) {
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
            final int colapso_range = (int)Math.ceil(this.range.get_value(1));
            final List<BlockPos> spheres = this.sphere(this.player_as_blockpos(), (float)colapso_range, colapso_range);
            for (final BlockPos pos : spheres) {
                if (!WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                    continue;
                }
                if (!WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                    continue;
                }
                if (!WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                    continue;
                }
                boolean possible = true;
                this.safe_sides = 0;
                for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                    final Block block = WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                    if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                        possible = false;
                        break;
                    }
                    if (block == Blocks.field_150357_h) {
                        ++this.safe_sides;
                    }
                }
                if (!possible) {
                    continue;
                }
                if (this.safe_sides == 5) {
                    if (!this.bedrock_enable.get_value(true)) {
                        continue;
                    }
                    this.holes.add((WurstplusPair<BlockPos, Boolean>)new WurstplusPair((Object)pos, (Object)true));
                }
                else {
                    if (!this.obsidian_enable.get_value(true)) {
                        continue;
                    }
                    this.holes.add((WurstplusPair<BlockPos, Boolean>)new WurstplusPair((Object)pos, (Object)false));
                }
            }
        }
    }
    
    public void render(final WurstplusEventRender event) {
        float off_set_h = 0.0f;
        if (!this.holes.isEmpty()) {
            off_set_h = (float)this.off_set.get_value(1.0);
            for (final WurstplusPair<BlockPos, Boolean> hole : this.holes) {
                if (hole.getValue()) {
                    this.color_r = this.color_r_b;
                    this.color_g = this.color_g_b;
                    this.color_b = this.color_b_b;
                    this.color_a = this.ab.get_value(1);
                }
                else {
                    if (hole.getValue()) {
                        continue;
                    }
                    this.color_r = this.color_r_o;
                    this.color_g = this.color_g_o;
                    this.color_b = this.color_b_o;
                    this.color_a = this.ao.get_value(1);
                }
                if (this.hide_own.get_value(true) && ((BlockPos)hole.getKey()).equals((Object)new BlockPos(WurstplusHoleESP.mc.field_71439_g.field_70165_t, WurstplusHoleESP.mc.field_71439_g.field_70163_u, WurstplusHoleESP.mc.field_71439_g.field_70161_v))) {
                    continue;
                }
                if (this.solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.color_a, "all");
                    RenderHelp.release();
                }
                if (!this.outline) {
                    continue;
                }
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.line_a.get_value(1), "all");
                RenderHelp.release();
            }
        }
    }
    
    public List<BlockPos> sphere(final BlockPos pos, final float r, final int h) {
        final boolean hollow = false;
        final boolean sphere = true;
        final int plus_y = 0;
        final List<BlockPos> sphere_block = new ArrayList<BlockPos>();
        final int cx = pos.func_177958_n();
        final int cy = pos.func_177956_o();
        final int cz = pos.func_177952_p();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos spheres = new BlockPos(x, y + plus_y, z);
                        sphere_block.add(spheres);
                    }
                }
            }
        }
        return sphere_block;
    }
    
    public BlockPos player_as_blockpos() {
        return new BlockPos(Math.floor(WurstplusHoleESP.mc.field_71439_g.field_70165_t), Math.floor(WurstplusHoleESP.mc.field_71439_g.field_70163_u), Math.floor(WurstplusHoleESP.mc.field_71439_g.field_70161_v));
    }
}
