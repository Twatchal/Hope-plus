package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.entity.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class WurstplusSelfTrap extends WurstplusHack
{
    WurstplusSetting toggle;
    WurstplusSetting rotate;
    WurstplusSetting swing;
    private BlockPos trap_pos;
    
    public WurstplusSelfTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.toggle = this.create("Toggle", "SelfTrapToggle", false);
        this.rotate = this.create("Rotate", "SelfTrapRotate", false);
        this.swing = this.create("Swing", "SelfTrapSwing", "Mainhand", this.combobox(new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.name = "Self Trap";
        this.tag = "SelfTrap";
        this.description = "oh 'eck, ive trapped me sen again";
    }
    
    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
    }
    
    public void update() {
        final Vec3d pos = WurstplusMathUtil.interpolateEntity((Entity)WurstplusSelfTrap.mc.field_71439_g, WurstplusSelfTrap.mc.func_184121_ak());
        this.trap_pos = new BlockPos(pos.field_72450_a, pos.field_72448_b + 2.0, pos.field_72449_c);
        if (this.is_trapped() && !this.toggle.get_value(true)) {
            this.toggle();
            return;
        }
        final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(this.trap_pos);
        if (result == WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere && !WurstplusSelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos).func_185904_a().func_76222_j()) {
            return;
        }
        if (result == WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
            final BlockPos[] array;
            final BlockPos[] tests = array = new BlockPos[] { this.trap_pos.func_177978_c(), this.trap_pos.func_177968_d(), this.trap_pos.func_177974_f(), this.trap_pos.func_177976_e(), this.trap_pos.func_177984_a(), this.trap_pos.func_177977_b().func_177976_e() };
            for (final BlockPos pos_ : array) {
                final WurstplusBlockInteractHelper.ValidResult result_ = WurstplusBlockInteractHelper.valid(pos_);
                if (result_ != WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
                    if (result_ != WurstplusBlockInteractHelper.ValidResult.NoEntityCollision) {
                        if (WurstplusBlockUtil.placeBlock(pos_, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                            return;
                        }
                    }
                }
            }
            return;
        }
        WurstplusBlockUtil.placeBlock(this.trap_pos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }
    
    public boolean is_trapped() {
        if (this.trap_pos == null) {
            return false;
        }
        final IBlockState state = WurstplusSelfTrap.mc.field_71441_e.func_180495_p(this.trap_pos);
        return state.func_177230_c() != Blocks.field_150350_a && state.func_177230_c() != Blocks.field_150355_j && state.func_177230_c() != Blocks.field_150353_l;
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusSelfTrap.mc.field_71439_g.field_71071_by.func_70301_a(i);
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
}
