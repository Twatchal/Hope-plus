package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class WurstplusAutoArmour extends WurstplusHack
{
    WurstplusSetting delay;
    WurstplusSetting smart_mode;
    WurstplusSetting put_back;
    WurstplusSetting player_range;
    WurstplusSetting crystal_range;
    WurstplusSetting boot_percent;
    WurstplusSetting chest_percent;
    private int delay_count;
    
    public WurstplusAutoArmour() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.delay = this.create("Delay", "AADelay", 2, 0, 5);
        this.smart_mode = this.create("Smart Mode", "AASmartMode", true);
        this.put_back = this.create("Equip Armour", "AAEquipArmour", true);
        this.player_range = this.create("Player Range", "AAPlayerRange", 8, 0, 20);
        this.crystal_range = this.create("Crystal Range", "AACrystalRange", 13, 0, 20);
        this.boot_percent = this.create("Boot Percent", "AATBootPercent", 80, 0, 100);
        this.chest_percent = this.create("Chest Percent", "AATChestPercent", 80, 0, 100);
        this.name = "Auto Armour";
        this.tag = "AutoArmour";
        this.description = "WATCH UR BOOTS";
    }
    
    protected void enable() {
        this.delay_count = 0;
    }
    
    public void update() {
        if (WurstplusAutoArmour.mc.field_71439_g.field_70173_aa % 2 == 0) {
            return;
        }
        boolean flag = false;
        if (this.delay_count < this.delay.get_value(0)) {
            ++this.delay_count;
            return;
        }
        this.delay_count = 0;
        if (this.smart_mode.get_value(true) && !this.is_crystal_in_range(this.crystal_range.get_value(1)) && !this.is_player_in_range(this.player_range.get_value(1))) {
            flag = true;
        }
        if (flag) {
            if (WurstplusAutoArmour.mc.field_71474_y.field_74313_G.func_151470_d() && WurstplusAutoArmour.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151062_by) {
                this.take_off();
            }
            return;
        }
        if (!this.put_back.get_value(true)) {
            return;
        }
        if (WurstplusAutoArmour.mc.field_71462_r instanceof GuiContainer && !(WurstplusAutoArmour.mc.field_71462_r instanceof InventoryEffectRenderer)) {
            return;
        }
        final int[] bestArmorSlots = new int[4];
        final int[] bestArmorValues = new int[4];
        for (int armorType = 0; armorType < 4; ++armorType) {
            final ItemStack oldArmor = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType);
            if (oldArmor.func_77973_b() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.func_77973_b()).field_77879_b;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            final ItemStack stack = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70301_a(slot);
            if (stack.func_190916_E() <= 1) {
                if (stack.func_77973_b() instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor)stack.func_77973_b();
                    final int armorType2 = armor.field_77881_a.ordinal() - 2;
                    if (armorType2 != 2 || !WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType2).func_77973_b().equals(Items.field_185160_cR)) {
                        final int armorValue = armor.field_77879_b;
                        if (armorValue > bestArmorValues[armorType2]) {
                            bestArmorSlots[armorType2] = slot;
                            bestArmorValues[armorType2] = armorValue;
                        }
                    }
                }
            }
        }
        for (int armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 != -1) {
                final ItemStack oldArmor2 = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType);
                if (oldArmor2 != ItemStack.field_190927_a || WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70447_i() != -1) {
                    if (slot2 < 9) {
                        slot2 += 36;
                    }
                    WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
                    WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
                    break;
                }
            }
        }
    }
    
    public boolean is_player_in_range(final int range) {
        for (final Entity player : (List)WurstplusAutoArmour.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_())).collect(Collectors.toList())) {
            if (player == WurstplusAutoArmour.mc.field_71439_g) {
                continue;
            }
            if (WurstplusAutoArmour.mc.field_71439_g.func_70032_d(player) < range) {
                return true;
            }
        }
        return false;
    }
    
    public boolean is_crystal_in_range(final int range) {
        for (final Entity c : (List)WurstplusAutoArmour.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList())) {
            if (WurstplusAutoArmour.mc.field_71439_g.func_70032_d(c) < range) {
                return true;
            }
        }
        return false;
    }
    
    public void take_off() {
        if (!this.is_space()) {
            return;
        }
        for (final Map.Entry<Integer, ItemStack> armorSlot : get_armour().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (this.is_healed(stack)) {
                WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, (int)armorSlot.getKey(), 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
            }
        }
    }
    
    public boolean is_space() {
        for (final Map.Entry<Integer, ItemStack> invSlot : get_inv().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.func_77973_b() == Items.field_190931_a) {
                return true;
            }
        }
        return false;
    }
    
    private static Map<Integer, ItemStack> get_inv() {
        return get_inv_slots(9, 44);
    }
    
    private static Map<Integer, ItemStack> get_armour() {
        return get_inv_slots(5, 8);
    }
    
    private static Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoArmour.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
    
    public boolean is_healed(final ItemStack item) {
        if (item.func_77973_b() == Items.field_151175_af || item.func_77973_b() == Items.field_151161_ac) {
            final double max_dam = item.func_77958_k();
            final double dam_left = item.func_77958_k() - item.func_77952_i();
            final double percent = dam_left / max_dam * 100.0;
            return percent >= this.boot_percent.get_value(1);
        }
        final double max_dam = item.func_77958_k();
        final double dam_left = item.func_77958_k() - item.func_77952_i();
        final double percent = dam_left / max_dam * 100.0;
        return percent >= this.chest_percent.get_value(1);
    }
}
