package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSurround
extends WurstplusHack {
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
        this.swing = this.create("Swing", "SurroundSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.y_level = 0;
        this.tick_runs = 0;
        this.offset_step = 0;
        this.center_block = Vec3d.field_186680_a;
        this.surround_targets = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
        this.surround_targets_face = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
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
            this.y_level = (int)Math.round((double)WurstplusSurround.mc.field_71439_g.field_70163_u);
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
                double x_diff = Math.abs((double)(this.center_block.field_72450_a - WurstplusSurround.mc.field_71439_g.field_70165_t));
                double z_diff = Math.abs((double)(this.center_block.field_72449_c - WurstplusSurround.mc.field_71439_g.field_70161_v));
                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    this.center_block = Vec3d.field_186680_a;
                } else {
                    double motion_x = this.center_block.field_72450_a - WurstplusSurround.mc.field_71439_g.field_70165_t;
                    double motion_z = this.center_block.field_72449_c - WurstplusSurround.mc.field_71439_g.field_70161_v;
                    WurstplusSurround.mc.field_71439_g.field_70159_w = motion_x / 2.0;
                    WurstplusSurround.mc.field_71439_g.field_70179_y = motion_z / 2.0;
                }
            }
            if ((int)Math.round((double)WurstplusSurround.mc.field_71439_g.field_70163_u) != this.y_level && this.hybrid.get_value(true)) {
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
                BlockPos offsetPos = new BlockPos(this.block_head.get_value(true) ? this.surround_targets_face[this.offset_step] : this.surround_targets[this.offset_step]);
                BlockPos targetPos = new BlockPos(WurstplusSurround.mc.field_71439_g.func_174791_d()).func_177982_a(offsetPos.func_177958_n(), offsetPos.func_177956_o(), offsetPos.func_177952_p());
                boolean try_to_place = true;
                if (!WurstplusSurround.mc.field_71441_e.func_180495_p(targetPos).func_185904_a().func_76222_j()) {
                    try_to_place = false;
                }
                for (Entity entity : WurstplusSurround.mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }
                if (try_to_place && WurstplusBlockUtil.placeBlock((BlockPos)targetPos, (int)this.find_in_hotbar(), (boolean)this.rotate.get_value(true), (boolean)this.rotate.get_value(true), (WurstplusSetting)this.swing)) {
                    ++blocks_placed;
                }
                ++this.offset_step;
            }
            ++this.tick_runs;
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSurround.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
            if (block instanceof BlockEnderChest) {
                return i;
            }
            if (!(block instanceof BlockObsidian)) continue;
            return i;
        }
        return -1;
    }

    public Vec3d get_center(double posX, double posY, double posZ) {
        double x = Math.floor((double)posX) + 0.5;
        double y = Math.floor((double)posY);
        double z = Math.floor((double)posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}
