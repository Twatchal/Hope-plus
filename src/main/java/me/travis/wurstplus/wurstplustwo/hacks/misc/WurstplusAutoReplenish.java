package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class WurstplusAutoReplenish
extends WurstplusHack {
    WurstplusSetting mode;
    WurstplusSetting threshold;
    WurstplusSetting tickdelay;
    private int delay_step;

    public WurstplusAutoReplenish() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.mode = this.create("Mode", "AutoReplenishMode", "All", this.combobox(new String[]{"All", "Crystals", "Xp", "Both"}));
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
        WurstplusPair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        int inventorySlot = (Integer)slots.getKey();
        int hotbarSlot = (Integer)slots.getValue();
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_187098_a(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.field_71439_g);
        WurstplusAutoReplenish.mc.field_71442_b.func_78765_e();
    }

    private WurstplusPair<Integer, Integer> findReplenishableHotbarSlot() {
        WurstplusPair returnPair = null;
        for (Map.Entry hotbarSlot : this.get_hotbar().entrySet()) {
            int inventorySlot;
            ItemStack stack = (ItemStack)hotbarSlot.getValue();
            if (stack.field_190928_g || stack.func_77973_b() == Items.field_190931_a || !stack.func_77985_e() || stack.field_77994_a >= stack.func_77976_d() || stack.field_77994_a > this.threshold.get_value(1) || (inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) continue;
            returnPair = new WurstplusPair((Object)inventorySlot, hotbarSlot.getKey());
        }
        return returnPair;
    }

    private int findCompatibleInventorySlot(ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry entry : this.get_inventory().entrySet()) {
            int currentStackSize;
            ItemStack inventoryStack = (ItemStack)entry.getValue();
            if (inventoryStack.field_190928_g || inventoryStack.func_77973_b() == Items.field_190931_a || !this.isCompatibleStacks(hotbarStack, inventoryStack) || smallestStackSize <= (currentStackSize = ((ItemStack)WurstplusAutoReplenish.mc.field_71439_g.field_71069_bz.func_75138_a().get((int)((Integer)entry.getKey()).intValue())).field_77994_a)) continue;
            smallestStackSize = currentStackSize;
            inventorySlot = (Integer)entry.getKey();
        }
        return inventorySlot;
    }

    private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.func_77973_b().equals((Object)stack2.func_77973_b())) {
            return false;
        }
        if (stack1.func_77973_b() instanceof ItemBlock && stack2.func_77973_b() instanceof ItemBlock) {
            Block block1 = ((ItemBlock)stack1.func_77973_b()).func_179223_d();
            Block block2 = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
            if (!block1.field_149764_J.equals((Object)block2.field_149764_J)) {
                return false;
            }
        }
        return stack1.func_82833_r().equals((Object)stack2.func_82833_r()) && stack1.func_77952_i() == stack2.func_77952_i();
    }

    private Map<Integer, ItemStack> get_inventory() {
        return this.get_inv_slots(9, 35);
    }

    private Map<Integer, ItemStack> get_hotbar() {
        return this.get_inv_slots(36, 44);
    }

    private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap fullInventorySlots = new HashMap();
        while (current <= last) {
            fullInventorySlots.put((Object)current, (Object)((ItemStack)WurstplusAutoReplenish.mc.field_71439_g.field_71069_bz.func_75138_a().get(current)));
            ++current;
        }
        return fullInventorySlots;
    }
}
