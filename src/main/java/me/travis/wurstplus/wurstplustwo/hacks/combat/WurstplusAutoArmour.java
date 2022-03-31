package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class WurstplusAutoArmour
extends WurstplusHack {
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
        int armorType;
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
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType);
            if (oldArmor.func_77973_b() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.func_77973_b()).field_77879_b;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            int armorValue;
            ItemStack stack = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70301_a(slot);
            if (stack.func_190916_E() > 1 || !(stack.func_77973_b() instanceof ItemArmor)) continue;
            ItemArmor armor = (ItemArmor)stack.func_77973_b();
            int armorType2 = armor.field_77881_a.ordinal() - 2;
            if (armorType2 == 2 && WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType2).func_77973_b().equals((Object)Items.field_185160_cR) || (armorValue = armor.field_77879_b) <= bestArmorValues[armorType2]) continue;
            bestArmorSlots[armorType2] = slot;
            bestArmorValues[armorType2] = armorValue;
        }
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor;
            int slot = bestArmorSlots[armorType];
            if (slot == -1 || (oldArmor = WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70440_f(armorType)) == ItemStack.field_190927_a && WurstplusAutoArmour.mc.field_71439_g.field_71071_by.func_70447_i() == -1) continue;
            if (slot < 9) {
                slot += 36;
            }
            WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
            WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
            break;
        }
    }

    public boolean is_player_in_range(int range) {
        for (Entity player : (List)WurstplusAutoArmour.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend((String)entityPlayer.func_70005_c_())).collect(Collectors.toList())) {
            if (player == WurstplusAutoArmour.mc.field_71439_g || WurstplusAutoArmour.mc.field_71439_g.func_70032_d(player) >= (float)range) continue;
            return true;
        }
        return false;
    }

    public boolean is_crystal_in_range(int range) {
        for (Entity c : (List)WurstplusAutoArmour.mc.field_71441_e.field_72996_f.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList())) {
            if (WurstplusAutoArmour.mc.field_71439_g.func_70032_d(c) >= (float)range) continue;
            return true;
        }
        return false;
    }

    public void take_off() {
        if (!this.is_space()) {
            return;
        }
        for (Map.Entry armorSlot : WurstplusAutoArmour.get_armour().entrySet()) {
            ItemStack stack = (ItemStack)armorSlot.getValue();
            if (!this.is_healed(stack)) continue;
            WurstplusAutoArmour.mc.field_71442_b.func_187098_a(0, ((Integer)armorSlot.getKey()).intValue(), 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.field_71439_g);
            return;
        }
    }

    public boolean is_space() {
        for (Map.Entry invSlot : WurstplusAutoArmour.get_inv().entrySet()) {
            ItemStack stack = (ItemStack)invSlot.getValue();
            if (stack.func_77973_b() != Items.field_190931_a) continue;
            return true;
        }
        return false;
    }

    private static Map<Integer, ItemStack> get_inv() {
        return WurstplusAutoArmour.get_inv_slots(9, 44);
    }

    private static Map<Integer, ItemStack> get_armour() {
        return WurstplusAutoArmour.get_inv_slots(5, 8);
    }

    private static Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap fullInventorySlots = new HashMap();
        while (current <= last) {
            fullInventorySlots.put((Object)current, WurstplusAutoArmour.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

    public boolean is_healed(ItemStack item) {
        if (item.func_77973_b() == Items.field_151175_af || item.func_77973_b() == Items.field_151161_ac) {
            double max_dam = item.func_77958_k();
            double dam_left = item.func_77958_k() - item.func_77952_i();
            double percent = dam_left / max_dam * 100.0;
            return percent >= (double)this.boot_percent.get_value(1);
        }
        double max_dam = item.func_77958_k();
        double dam_left = item.func_77958_k() - item.func_77952_i();
        double percent = dam_left / max_dam * 100.0;
        return percent >= (double)this.chest_percent.get_value(1);
    }
}
