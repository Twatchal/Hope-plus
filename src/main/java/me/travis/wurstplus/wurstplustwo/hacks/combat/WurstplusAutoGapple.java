package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WurstplusAutoGapple
extends WurstplusHack {
    WurstplusSetting delay;
    private boolean switching;
    private int last_slot;

    public WurstplusAutoGapple() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "GappleDelay", false);
        this.switching = false;
        this.name = "Auto Gapple";
        this.tag = "AutoGapple";
        this.description = "put gapple in offhand";
    }

    public void update() {
        if (WurstplusAutoGapple.mc.field_71462_r == null || WurstplusAutoGapple.mc.field_71462_r instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            this.swap_items(this.get_item_slot(), this.delay.get_value(true) ? 1 : 0);
        }
    }

    private int get_item_slot() {
        if (Items.field_151153_ao == WurstplusAutoGapple.mc.field_71439_g.func_184592_cb().func_77973_b()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            Item item = WurstplusAutoGapple.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item != Items.field_151153_ao) continue;
            if (i < 9) {
                return -1;
            }
            return i;
        }
        return -1;
    }

    public void swap_items(int slot, int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
        }
        if (step == 1) {
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
            WurstplusAutoGapple.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoGapple.mc.field_71439_g);
            this.switching = false;
        }
        WurstplusAutoGapple.mc.field_71442_b.func_78765_e();
    }
}
