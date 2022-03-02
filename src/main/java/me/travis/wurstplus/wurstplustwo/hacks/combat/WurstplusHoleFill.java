package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class WurstplusHoleFill extends WurstplusHack
{
    WurstplusSetting hole_toggle;
    WurstplusSetting hole_rotate;
    WurstplusSetting hole_range;
    WurstplusSetting swing;
    private final ArrayList<BlockPos> holes;
    
    public WurstplusHoleFill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.hole_toggle = this.create("Toggle", "HoleFillToggle", true);
        this.hole_rotate = this.create("Rotate", "HoleFillRotate", true);
        this.hole_range = this.create("Range", "HoleFillRange", 4, 1, 6);
        this.swing = this.create("Swing", "HoleFillSwing", "Mainhand", this.combobox(new String[] { "Mainhand", "Offhand", "Both", "None" }));
        this.holes = new ArrayList<BlockPos>();
        this.name = "Hole Fill";
        this.tag = "HoleFill";
        this.description = "Turn holes into floors";
    }
    
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
        this.find_new_holes();
    }
    
    public void disable() {
        this.holes.clear();
    }
    
    public void update() {
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (this.holes.isEmpty()) {
            if (!this.hole_toggle.get_value(true)) {
                this.set_disable();
                WurstplusMessageUtil.toggle_message((WurstplusHack)this);
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (final BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) {
                continue;
            }
            final WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result == WurstplusBlockInteractHelper.ValidResult.Ok) {
                pos_to_fill = pos;
                break;
            }
            this.holes.remove(pos);
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (pos_to_fill != null && WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.hole_rotate.get_value(true), this.hole_rotate.get_value(true), this.swing)) {
            this.holes.remove(pos_to_fill);
        }
    }
    
    public void find_new_holes() {
        this.holes.clear();
        for (final BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), (float)this.hole_range.get_value(1), this.hole_range.get_value(1), false, true, 0)) {
            if (!WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            if (!WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a)) {
                continue;
            }
            boolean possible = true;
            for (final BlockPos seems_blocks : new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) }) {
                final Block block = WurstplusHoleFill.mc.field_71441_e.func_180495_p(pos.func_177971_a((Vec3i)seems_blocks)).func_177230_c();
                if (block != Blocks.field_150357_h && block != Blocks.field_150343_Z && block != Blocks.field_150477_bB && block != Blocks.field_150467_bQ) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                continue;
            }
            this.holes.add(pos);
        }
    }
    
    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusHoleFill.mc.field_71439_g.field_71071_by.func_70301_a(i);
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
