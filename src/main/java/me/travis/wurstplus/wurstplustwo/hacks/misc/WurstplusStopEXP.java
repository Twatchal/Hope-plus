package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.EventHook;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.NonNullList;

public class WurstplusStopEXP
extends WurstplusHack {
    WurstplusSetting helmet_boot_percent;
    WurstplusSetting chest_leggings_percent;
    private boolean should_cancel;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> packet_event;

    public WurstplusStopEXP() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.helmet_boot_percent = this.create("Helment Boots %", "StopEXHelmet", 80, 0, 100);
        this.chest_leggings_percent = this.create("Chest Leggins %", "StopEXChest", 100, 0, 100);
        this.should_cancel = false;
        this.packet_event = new Listener(event -> {
            if (event.get_packet() instanceof CPacketPlayerTryUseItem && this.should_cancel) {
                event.cancel();
            }
        }
        , new Predicate[0]);
        this.name = "Stop EXP";
        this.tag = "StopEXP";
        this.description = "jumpy has a good idea?? (nvm this is dumb)";
    }

    public void update() {
        int counter = 0;
        for (Map.Entry armor_slot : this.get_armor().entrySet()) {
            ++counter;
            if (((ItemStack)armor_slot.getValue()).func_190926_b()) continue;
            ItemStack stack = (ItemStack)armor_slot.getValue();
            double max_dam = stack.func_77958_k();
            double dam_left = stack.func_77958_k() - stack.func_77952_i();
            double percent = dam_left / max_dam * 100.0;
            if (counter == 1 || counter == 4) {
                this.should_cancel = percent >= (double)this.helmet_boot_percent.get_value(1) ? this.is_holding_exp() : false;
            }
            if (counter != 2 && counter != 3) continue;
            if (percent >= (double)this.chest_leggings_percent.get_value(1)) {
                if (this.is_holding_exp()) {
                    this.should_cancel = true;
                    continue;
                }
                this.should_cancel = false;
                continue;
            }
            this.should_cancel = false;
        }
    }

    private Map<Integer, ItemStack> get_armor() {
        return this.get_inv_slots(5, 8);
    }

    private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap full_inv_slots = new HashMap();
        while (current <= last) {
            full_inv_slots.put((Object)current, WurstplusStopEXP.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return full_inv_slots;
    }

    public boolean is_holding_exp() {
        if (WurstplusStopEXP.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle || WurstplusStopEXP.mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemExpBottle) {
            return true;
        }
        return false;
    }
}
