package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.client.gui.inventory.*;
import me.travis.wurstplus.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;

public class WurstplusOffhand extends WurstplusHack
{
    WurstplusSetting switch_mode;
    WurstplusSetting totem_switch;
    WurstplusSetting gapple_in_hole;
    WurstplusSetting gapple_hole_hp;
    WurstplusSetting delay;
    private boolean switching;
    private int last_slot;
    
    public WurstplusOffhand() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.switch_mode = this.create("Offhand", "OffhandOffhand", "Crystal", this.combobox(new String[] { "Totem", "Crystal", "Gapple" }));
        this.totem_switch = this.create("Totem HP", "OffhandTotemHP", 8, 0, 36);
        this.gapple_in_hole = this.create("Gapple In Hole", "OffhandGapple", true);
        this.gapple_hole_hp = this.create("Gapple Hole HP", "OffhandGappleHP", 12, 0, 36);
        this.delay = this.create("Delay", "OffhandDelay", false);
        this.switching = false;
        this.name = "Offhand";
        this.tag = "Offhand";
        this.description = "Switches shit to ur offhand";
    }
    
    public void update() {
        if (WurstplusOffhand.mc.field_71462_r == null || WurstplusOffhand.mc.field_71462_r instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            final float hp = WurstplusOffhand.mc.field_71439_g.func_110143_aJ() + WurstplusOffhand.mc.field_71439_g.func_110139_bj();
            if (hp <= this.totem_switch.get_value(1)) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Crystal") && Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
                this.swap_items(this.get_item_slot(Items.field_185158_cP), 0);
                return;
            }
            if (this.gapple_in_hole.get_value(true) && hp > this.gapple_hole_hp.get_value(1) && this.is_in_hole()) {
                this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Totem")) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Gapple")) {
                this.swap_items(this.get_item_slot(Items.field_151153_ao), this.delay.get_value(true) ? 1 : 0);
                return;
            }
            if (this.switch_mode.in("Crystal") && !Wurstplus.get_hack_manager().get_module_with_tag("AutoCrystal").is_active()) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), 0);
                return;
            }
            if (WurstplusOffhand.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190931_a) {
                this.swap_items(this.get_item_slot(Items.field_190929_cY), this.delay.get_value(true) ? 1 : 0);
            }
        }
    }
    
    public void swap_items(final int slot, final int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
        }
        if (step == 1) {
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
            WurstplusOffhand.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusOffhand.mc.field_71439_g);
            this.switching = false;
        }
        WurstplusOffhand.mc.field_71442_b.func_78765_e();
    }
    
    private boolean is_in_hole() {
        final BlockPos player_block = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        return WurstplusOffhand.mc.field_71441_e.func_180495_p(player_block.func_177974_f()).func_177230_c() != Blocks.field_150350_a && WurstplusOffhand.mc.field_71441_e.func_180495_p(player_block.func_177976_e()).func_177230_c() != Blocks.field_150350_a && WurstplusOffhand.mc.field_71441_e.func_180495_p(player_block.func_177978_c()).func_177230_c() != Blocks.field_150350_a && WurstplusOffhand.mc.field_71441_e.func_180495_p(player_block.func_177968_d()).func_177230_c() != Blocks.field_150350_a;
    }
    
    private int get_item_slot(final Item input) {
        if (input == WurstplusOffhand.mc.field_71439_g.func_184592_cb().func_77973_b()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = WurstplusOffhand.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.field_151153_ao) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
