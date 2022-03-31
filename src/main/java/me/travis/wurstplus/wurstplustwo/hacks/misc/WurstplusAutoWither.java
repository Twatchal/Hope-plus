package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class WurstplusAutoWither
extends WurstplusHack {
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
        int i;
        ItemStack stack;
        int count = 0;
        for (i = 0; i < 9; ++i) {
            Block block;
            stack = WurstplusAutoWither.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || !((block = ((ItemBlock)stack.func_77973_b()).func_179223_d()) instanceof BlockSoulSand)) continue;
            this.sand_slot = i;
            ++count;
            break;
        }
        for (i = 0; i < 9; ++i) {
            stack = WurstplusAutoWither.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() != Items.field_151144_bL || stack.func_77952_i() != 1) continue;
            this.head_slot = i;
            ++count;
            break;
        }
        if (count != 2) {
            return false;
        }
        return true;
    }
}
