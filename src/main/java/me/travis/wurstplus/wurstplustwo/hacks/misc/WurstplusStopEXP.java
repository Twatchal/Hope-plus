package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

public class WurstplusStopEXP extends WurstplusHack
{
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
        this.packet_event = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (event.get_packet() instanceof CPacketPlayerTryUseItem && this.should_cancel) {
                event.cancel();
            }
        }, new Predicate[0]);
        this.name = "Stop EXP";
        this.tag = "StopEXP";
        this.description = "jumpy has a good idea?? (nvm this is dumb)";
    }
    
    public void update() {
        int counter = 0;
        for (final Map.Entry<Integer, ItemStack> armor_slot : this.get_armor().entrySet()) {
            ++counter;
            if (armor_slot.getValue().func_190926_b()) {
                continue;
            }
            final ItemStack stack = armor_slot.getValue();
            final double max_dam = stack.func_77958_k();
            final double dam_left = stack.func_77958_k() - stack.func_77952_i();
            final double percent = dam_left / max_dam * 100.0;
            if (counter == 1 || counter == 4) {
                if (percent >= this.helmet_boot_percent.get_value(1)) {
                    if (this.is_holding_exp()) {
                        this.should_cancel = true;
                    }
                    else {
                        this.should_cancel = false;
                    }
                }
                else {
                    this.should_cancel = false;
                }
            }
            if (counter != 2 && counter != 3) {
                continue;
            }
            if (percent >= this.chest_leggings_percent.get_value(1)) {
                if (this.is_holding_exp()) {
                    this.should_cancel = true;
                }
                else {
                    this.should_cancel = false;
                }
            }
            else {
                this.should_cancel = false;
            }
        }
    }
    
    private Map<Integer, ItemStack> get_armor() {
        return this.get_inv_slots(5, 8);
    }
    
    private Map<Integer, ItemStack> get_inv_slots(int current, final int last) {
        final Map<Integer, ItemStack> full_inv_slots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            full_inv_slots.put(current, (ItemStack)WurstplusStopEXP.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
            ++current;
        }
        return full_inv_slots;
    }
    
    public boolean is_holding_exp() {
        return WurstplusStopEXP.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemExpBottle || WurstplusStopEXP.mc.field_71439_g.func_184592_cb().func_77973_b() instanceof ItemExpBottle;
    }
}
