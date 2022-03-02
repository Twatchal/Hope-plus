package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WurstplusAutoWither extends WurstplusHack
{
    WurstplusSetting range;
    private int head_slot;
    private int sand_slot;
    
    public WurstplusAutoWither() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.range = this.create("Range", "WitherRange", 4, 0, 6);
        this.name = "Auto Wither";
        this.tag = "AutoWither";
        this.description = "makes withers";
    }
    
    protected void enable() {
    }
    
    public boolean has_blocks() {
        int count = 0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWither.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                if (block instanceof BlockSoulSand) {
                    this.sand_slot = i;
                    ++count;
                    break;
                }
            }
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = WurstplusAutoWither.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == Items.field_151144_bL && stack.func_77952_i() == 1) {
                this.head_slot = i;
                ++count;
                break;
            }
        }
        return count == 2;
    }
}
