package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class WurstplusAutoTotem extends WurstplusHack
{
    WurstplusSetting delay;
    private boolean switching;
    private int last_slot;
    
    public WurstplusAutoTotem() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "TotemDelay", false);
        this.switching = false;
        this.name = "Auto Totem";
        this.tag = "AutoTotem";
        this.description = "put totem in offhand";
    }
    
    public void update() {
        if (WurstplusAutoTotem.mc.field_71462_r == null || WurstplusAutoTotem.mc.field_71462_r instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            if (WurstplusAutoTotem.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190931_a) {
                this.swap_items(this.get_item_slot(), this.delay.get_value(true) ? 1 : 0);
            }
        }
    }
    
    private int get_item_slot() {
        if (Items.field_190929_cY == WurstplusAutoTotem.mc.field_71439_g.func_184592_cb().func_77973_b()) {
            return -1;
        }
        int i = 36;
        while (i >= 0) {
            final Item item = WurstplusAutoTotem.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == Items.field_190929_cY) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
            else {
                --i;
            }
        }
        return -1;
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
        }
        if (step == 1) {
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
            WurstplusAutoTotem.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.field_71439_g);
            this.switching = false;
        }
        WurstplusAutoTotem.mc.field_71442_b.func_78765_e();
    }
}
