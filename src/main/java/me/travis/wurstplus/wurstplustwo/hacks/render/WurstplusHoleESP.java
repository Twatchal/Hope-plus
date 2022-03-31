package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.List;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class WurstplusHoleESP
extends WurstplusHack {
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
        this.mode = this.create("Mode", "HoleESPMode", "Pretty", this.combobox(new String[]{"Pretty", "Solid", "Outline"}));
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
        this.holes = new ArrayList();
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
            int colapso_range = (int)Math.ceil((double)this.range.get_value(1));
            List<BlockPos> spheres = this.sphere(this.player_as_blockpos(), colapso_range, colapso_range);
            for (BlockPos pos : spheres) {
                if (!WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals((Object)Blocks.field_150350_a) || !WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals((Object)Blocks.field_150350_a) || !WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals((Object)Blocks.field_150350_a)) continue;
                boolean possible = true;
                this.safe_sides = 0;
                for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                    Block block = WurstplusHoleESP.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                    if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                        possible = false;
                        break;
                    }
                    if (block != Blocks.field_150357_h) continue;
                    ++this.safe_sides;
                }
                if (!possible) continue;
                if (this.safe_sides == 5) {
                    if (!this.bedrock_enable.get_value(true)) continue;
                    this.holes.add((Object)new WurstplusPair((Object)pos, (Object)true));
                    continue;
                }
                if (!this.obsidian_enable.get_value(true)) continue;
                this.holes.add((Object)new WurstplusPair((Object)pos, (Object)false));
            }
        }
    }

    public void render(WurstplusEventRender event) {
        float off_set_h = 0.0f;
        if (!this.holes.isEmpty()) {
            off_set_h = (float)this.off_set.get_value(1.0);
            for (WurstplusPair hole : this.holes) {
                if (((Boolean)hole.getValue()).booleanValue()) {
                    this.color_r = this.color_r_b;
                    this.color_g = this.color_g_b;
                    this.color_b = this.color_b_b;
                    this.color_a = this.ab.get_value(1);
                } else {
                    if (((Boolean)hole.getValue()).booleanValue()) continue;
                    this.color_r = this.color_r_o;
                    this.color_g = this.color_g_o;
                    this.color_b = this.color_b_o;
                    this.color_a = this.ao.get_value(1);
                }
                if (this.hide_own.get_value(true) && ((BlockPos)hole.getKey()).equals((Object)new BlockPos(WurstplusHoleESP.mc.field_71439_g.field_70165_t, WurstplusHoleESP.mc.field_71439_g.field_70163_u, WurstplusHoleESP.mc.field_71439_g.field_70161_v))) continue;
                if (this.solid) {
                    RenderHelp.prepare((String)"quads");
                    RenderHelp.draw_cube((BufferBuilder)RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), (float)1.0f, (float)off_set_h, (float)1.0f, (int)this.color_r, (int)this.color_g, (int)this.color_b, (int)this.color_a, (String)"all");
                    RenderHelp.release();
                }
                if (!this.outline) continue;
                RenderHelp.prepare((String)"lines");
                RenderHelp.draw_cube_line((BufferBuilder)RenderHelp.get_buffer_build(), (float)((BlockPos)hole.getKey()).func_177958_n(), (float)((BlockPos)hole.getKey()).func_177956_o(), (float)((BlockPos)hole.getKey()).func_177952_p(), (float)1.0f, (float)off_set_h, (float)1.0f, (int)this.color_r, (int)this.color_g, (int)this.color_b, (int)this.line_a.get_value(1), (String)"all");
                RenderHelp.release();
            }
        }
    }

    public List<BlockPos> sphere(BlockPos pos, float r, int h) {
        boolean hollow = false;
        boolean sphere = true;
        int plus_y = 0;
        ArrayList sphere_block = new ArrayList();
        int cx = pos.func_177958_n();
        int cy = pos.func_177956_o();
        int cz = pos.func_177952_p();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while ((float)y < (sphere ? (float)cy + r : (float)(cy + h))) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(dist >= (double)(r * r) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos spheres = new BlockPos(x, y + plus_y, z);
                        sphere_block.add((Object)spheres);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return sphere_block;
    }

    public BlockPos player_as_blockpos() {
        return new BlockPos(Math.floor((double)WurstplusHoleESP.mc.field_71439_g.field_70165_t), Math.floor((double)WurstplusHoleESP.mc.field_71439_g.field_70163_u), Math.floor((double)WurstplusHoleESP.mc.field_71439_g.field_70161_v));
    }
}
