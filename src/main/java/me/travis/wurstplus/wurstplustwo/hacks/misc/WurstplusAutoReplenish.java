package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;

public class WurstplusAutoReplenish extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting threshold;
    WurstplusSetting tickdelay;
    private int delay_step;
    
    public WurstplusAutoReplenish() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.mode = this.create("Mode", "AutoReplenishMode", "All", this.combobox(new String[] { "All", "Crystals", "Xp", "Both" }));
        this.threshold = this.create("Threshold", "AutoReplenishThreshold", 32, 1, 63);
        this.tickdelay = this.create("Delay", "AutoReplenishDelay", 2, 1, 10);
        this.delay_step = 0;
        this.name = "Hotbar Replenish";
        this.tag = "HotbarReplenish";
        this.description = "chad this doesnt desync you i swear";
    }
    
    public void update() {
        if (WurstplusAutoReplenish.mc.field_71462_r instanceof GuiContainer) {
            return;
        }
        if (this.delay_step < this.tickdelay.get_value(1)) {
            ++this.delay_step;
            return;
        }
        this.delay_step = 0;
        final WurstplusPair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        final int inventorySlot = (int)slots.getKey();
        final int hotbarSlot = (int)slots.getValue();
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_78765_e();
    }
    
    private WurstplusPair<Integer, Integer> findReplenishableHotbarSlot() {
        WurstplusPair<Integer, Integer> returnPair = null;
        for (final Map.Entry<Integer, ItemStack> hotbarSlot : this.get_hotbar().entrySet()) {
            final ItemStack stack = hotbarSlot.getValue();
            if (!stack.field_190928_g) {
                if (stack.func_77973_b() == Items.field_190931_a) {
                    continue;
                }
                if (!stack.func_77985_e()) {
                    continue;
                }
                if (stack.field_77994_a >= stack.func_77976_d()) {
                    continue;
                }
                if (stack.field_77994_a > this.threshold.get_value(1)) {
                    continue;
                }
                final int inventorySlot = this.findCompatibleInventorySlot(stack);
                if (inventorySlot == -1) {
                    continue;
                }
                returnPair = (WurstplusPair<Integer, Integer>)new WurstplusPair((Object)inventorySlot, (Object)hotbarSlot.getKey());
            }
        }
        return returnPair;
    }
    
    private int findCompatibleInventorySlot(final ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (final Map.Entry<Integer, ItemStack> entry : this.get_inventory().entrySet()) {
            final ItemStack inventoryStack = entry.getValue();
            if (!inventoryStack.field_190928_g) {
                if (inventoryStack.func_77973_b() == Items.field_190931_a) {
                    continue;
                }
                if (!this.isCompatibleStacks(hotbarStack, inventoryStack)) {
                    continue;
                }
                final int currentStackSize = ((ItemStack)WurstplusAutoReplenish.mc.field_71439_g.field_71069_bz.func_75138_a().get((int)entry.getKey())).field_77994_a;
                if (smallestStackSize <= currentStackSize) {
                    continue;
                }
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }
    
    private boolean isCompatibleStacks(final ItemStack stack1, final ItemStack stack2) {
        if (!stack1.func_77973_b().equals(stack2.func_77973_b())) {
            return false;
        }
        if (stack1.func_77973_b() instanceof ItemBlock && stack2.func_77973_b() instanceof ItemBlock) {
            final Block block1 = ((ItemBlock)stack1.func_77973_b()).func_179223_d();
            final Block block2 = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
            if (!block1.field_149764_J.equals(block2.field_149764_J)) {
                return false;
            }
        }
        return stack1.func_82833_r().equals(stack2.func_82833_r()) && stack1.func_77952_i() == stack2.func_77952_i();
    }
    
    private Map<Integer, ItemStack> get_inventory() {
        return this.get_inv_slots(9, 35);
    }
    
    private Map<Integer, ItemStack> get_hotbar() {
        return this.get_inv_slots(36, 44);
    }
    
    private Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoReplenish.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
}
