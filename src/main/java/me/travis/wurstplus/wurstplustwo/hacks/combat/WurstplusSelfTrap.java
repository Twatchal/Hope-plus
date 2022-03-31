package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSelfTrap
extends WurstplusHack {
    WurstplusSetting toggle;
    WurstplusSetting rotate;
    WurstplusSetting swing;
    private BlockPos trap_pos;

    public WurstplusSelfTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.toggle = this.create("Toggle", "SelfTrapToggle", false);
        this.rotate = this.create("Rotate", "SelfTrapRotate", false);
        this.swing = this.create("Swing", "SelfTrapSwing", "Mainhand", this.combobox(new String[]{"Mainhand", "Offhand", "Both", "None"}));
        this.name = "Self Trap";
        this.tag = "SelfTrap";
        this.description = "oh 'eck, ive trapped me sen again";
    }

    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
    }

    public void update() {
        Vec3d pos = WurstplusMathUtil.interpolateEntity((Entity)WurstplusSelfTrap.mc.field_71439_g, (float)mc.func_184121_ak());
        this.trap_pos = new BlockPos(pos.field_72450_a, pos.field_72448_b + 2.0, pos.field_72449_c);
        if (this.is_trapped() && !this.toggle.get_value(true)) {
            this.toggle();
            return;
        }
        WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid((BlockPos)this.trap_pos);
        if (result == WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere && !WurstplusSelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (result == WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
            BlockPos[] tests;
            for (BlockPos pos_ : tests = new BlockPos[]{this.trap_pos.func_177978_c(), this.trap_pos.func_177968_d(), this.trap_pos.func_177974_f(), this.trap_pos.func_177976_e(), this.trap_pos.func_177984_a(), this.trap_pos.func_177977_b().func_177976_e()}) {
                WurstplusBlockInteractHelper.ValidResult result_ = WurstplusBlockInteractHelper.valid((BlockPos)pos_);
                if (result_ == WurstplusBlockInteractHelper.ValidResult.NoNeighbors || result_ == WurstplusBlockInteractHelper.ValidResult.NoEntityCollision || !WurstplusBlockUtil.placeBlock((BlockPos)pos_, (int)this.find_in_hotbar(), (boolean)this.rotate.get_value(true), (boolean)this.rotate.get_value(true), (WurstplusSetting)this.swing)) continue;
                return;
            }
            return;
        }
        WurstplusBlockUtil.placeBlock((BlockPos)this.trap_pos, (int)this.find_in_hotbar(), (boolean)this.rotate.get_value(true), (boolean)this.rotate.get_value(true), (WurstplusSetting)this.swing);
    }

    public boolean is_trapped() {
        if (this.trap_pos == null) {
            return false;
        }
        IBlockState state = WurstplusSelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos);
        return state.func_177230_c() != Blocks.field_150350_a && state.func_177230_c() != Blocks.field_150355_j && state.func_177230_c() != Blocks.field_150353_l;
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSelfTrap.mc.field_71439_g.field_71071_by.func_70301_a(i);
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
}
