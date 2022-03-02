package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class WurstplusSurround extends WurstplusHack
{
    WurstplusSetting rotate;
    WurstplusSetting hybrid;
    WurstplusSetting triggerable;
    WurstplusSetting center;
    WurstplusSetting block_head;
    WurstplusSetting tick_for_place;
    WurstplusSetting tick_timeout;
    WurstplusSetting swing;
    private int y_level;
    private int tick_runs;
    private int offset_step;
    private Vec3d center_block;
    Vec3d[] surround_targets;
    Vec3d[] surround_targets_face;
    
    public WurstplusSurround() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.rotate = this.create("Rotate", "SurroundSmoth", true);
        this.hybrid = this.create("Hybrid", "SurroundHybrid", true);
        this.triggerable = this.create("Toggle", "SurroundToggle", true);
        this.center = this.create("Center", "SurroundCenter", true);
        this.block_head = this.create("Block Face", "SurroundBlockFace", false);
        this.tick_for_place = this.create("Blocks per tick", "SurroundTickToPlace", 2, 1, 8);
        this.tick_timeout = this.create("Ticks til timeout", "SurroundTicks", 20, 10, 50);
        this.swing = this.create("Swing", "SurroundSwing", "Mainhand", this.combobox(new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.y_level = 0;
        this.tick_runs = 0;
        this.offset_step = 0;
        this.center_block = Vec3d.field_186680_a;
        this.surround_targets = new Vec3d[] { new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0) };
        this.surround_targets_face = new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0) };
        this.name = "Surround";
        this.tag = "Surround";
        this.description = "surround urself with obi and such";
    }
    
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
        if (WurstplusSurround.mc.field_71439_g != null) {
            this.y_level = (int)Math.round(WurstplusSurround.mc.field_71439_g.field_70163_u);
            this.center_block = this.get_center(WurstplusSurround.mc.field_71439_g.field_70165_t, WurstplusSurround.mc.field_71439_g.field_70163_u, WurstplusSurround.mc.field_71439_g.field_70161_v);
            if (this.center.get_value(true)) {
                WurstplusSurround.mc.field_71439_g.field_70159_w = 0.0;
                WurstplusSurround.mc.field_71439_g.field_70179_y = 0.0;
            }
        }
    }
    
    public void update() {
        if (WurstplusSurround.mc.field_71439_g != null) {
            if (this.center_block != Vec3d.field_186680_a && this.center.get_value(true)) {
                final double x_diff = Math.abs(this.center_block.field_72450_a - WurstplusSurround.mc.field_71439_g.field_70165_t);
                final double z_diff = Math.abs(this.center_block.field_72449_c - WurstplusSurround.mc.field_71439_g.field_70161_v);
                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    this.center_block = Vec3d.field_186680_a;
                }
                else {
                    final double motion_x = this.center_block.field_72450_a - WurstplusSurround.mc.field_71439_g.field_70165_t;
                    final double motion_z = this.center_block.field_72449_c - WurstplusSurround.mc.field_71439_g.field_70161_v;
                    WurstplusSurround.mc.field_71439_g.field_70159_w = motion_x / 2.0;
                    WurstplusSurround.mc.field_71439_g.field_70179_y = motion_z / 2.0;
                }
            }
            if ((int)Math.round(WurstplusSurround.mc.field_71439_g.field_70163_u) != this.y_level && this.hybrid.get_value(true)) {
                this.set_disable();
                return;
            }
            if (!this.triggerable.get_value(true) && this.tick_runs >= this.tick_timeout.get_value(1)) {
                this.tick_runs = 0;
                this.set_disable();
                return;
            }
            int blocks_placed = 0;
            while (blocks_placed < this.tick_for_place.get_value(1)) {
                if (this.offset_step >= (this.block_head.get_value(true) ? this.surround_targets_face.length : this.surround_targets.length)) {
                    this.offset_step = 0;
                    break;
                }
                final BlockPos offsetPos = new BlockPos(this.block_head.get_value(true) ? this.surround_targets_face[this.offset_step] : this.surround_targets[this.offset_step]);
                final BlockPos targetPos = new BlockPos(WurstplusSurround.mc.field_71439_g.func_174791_d()).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
                boolean try_to_place = true;
                if (!WurstplusSurround.mc.field_71441_e.func_180495_p(targetPos).func_185904_a().func_76222_j()) {
                    try_to_place = false;
                }
                for (final Entity entity : WurstplusSurround.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(targetPos))) {
                    if (!(entity instanceof EntityItem)) {
                        if (entity instanceof EntityXPOrb) {
                            continue;
                        }
                        try_to_place = false;
                        break;
                    }
                }
                if (try_to_place && WurstplusBlockUtil.placeBlock(targetPos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                    ++blocks_placed;
                }
                ++this.offset_step;
            }
            ++this.tick_runs;
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSurround.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockEnderChest) {
                    return i;
                }
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public Vec3d get_center(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}
