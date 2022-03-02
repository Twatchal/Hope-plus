package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import net.minecraft.util.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import me.travis.wurstplus.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;

public class WurstplusKillAura extends WurstplusHack
{
    WurstplusSetting mode;
    WurstplusSetting player;
    WurstplusSetting hostile;
    WurstplusSetting sword;
    WurstplusSetting sync_tps;
    WurstplusSetting range;
    WurstplusSetting delay;
    boolean start_verify;
    EnumHand actual_hand;
    double tick;
    
    public WurstplusKillAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.mode = this.create("Mode", "KillAuraMode", "Normal", this.combobox(new String[] { "A32k", "Normal" }));
        this.player = this.create("Player", "KillAuraPlayer", true);
        this.hostile = this.create("Hostile", "KillAuraHostile", false);
        this.sword = this.create("Sword", "KillAuraSword", true);
        this.sync_tps = this.create("Sync TPS", "KillAuraSyncTps", true);
        this.range = this.create("Range", "KillAuraRange", 5.0, 0.5, 6.0);
        this.delay = this.create("Delay", "KillAuraDelay", 2, 0, 10);
        this.start_verify = true;
        this.actual_hand = EnumHand.MAIN_HAND;
        this.tick = 0.0;
        this.name = "Kill Aura";
        this.tag = "KillAura";
        this.description = "To able hit enemies in a range.";
    }
    
    protected void enable() {
        this.tick = 0.0;
    }
    
    public void update() {
        if (WurstplusKillAura.mc.field_71439_g != null && WurstplusKillAura.mc.field_71441_e != null) {
            ++this.tick;
            if (WurstplusKillAura.mc.field_71439_g.field_70128_L | WurstplusKillAura.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
                return;
            }
            if (this.mode.in("Normal")) {
                if (!(WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && this.sword.get_value(true)) {
                    this.start_verify = false;
                }
                else if (WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                else if (!this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                final Entity entity = this.find_entity();
                if (entity != null && this.start_verify) {
                    final float tick_to_hit = 20.0f - Wurstplus.get_event_handler().get_tick_rate();
                    final boolean is_possible_attack = WurstplusKillAura.mc.field_71439_g.func_184825_o(this.sync_tps.get_value(true) ? (-tick_to_hit) : 0.0f) >= 1.0f;
                    if (is_possible_attack) {
                        this.attack_entity(entity);
                    }
                }
            }
            else {
                if (!(WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
                    return;
                }
                if (this.tick < this.delay.get_value(1)) {
                    return;
                }
                this.tick = 0.0;
                final Entity entity = this.find_entity();
                if (entity != null) {
                    this.attack_entity(entity);
                }
            }
        }
    }
    
    public void attack_entity(final Entity entity) {
        if (this.mode.in("A32k")) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = WurstplusKillAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack != ItemStack.field_190927_a) {
                    if (this.checkSharpness(stack)) {
                        newSlot = i;
                        break;
                    }
                }
            }
            if (newSlot != -1) {
                WurstplusKillAura.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
            }
        }
        final ItemStack off_hand_item = WurstplusKillAura.mc.field_71439_g.func_184592_cb();
        if (off_hand_item.func_77973_b() == Items.field_185159_cQ) {
            WurstplusKillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, WurstplusKillAura.mc.field_71439_g.func_174811_aO()));
        }
        WurstplusKillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
        WurstplusKillAura.mc.field_71439_g.func_184609_a(this.actual_hand);
        WurstplusKillAura.mc.field_71439_g.func_184821_cY();
    }
    
    public Entity find_entity() {
        Entity entity_requested = null;
        for (final Entity player : (List)WurstplusKillAura.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.func_70005_c_())).collect(Collectors.toList())) {
            if (player != null && this.is_compatible(player) && WurstplusKillAura.mc.field_71439_g.func_70032_d(player) <= this.range.get_value(1.0)) {
                entity_requested = player;
            }
        }
        return entity_requested;
    }
    
    public boolean is_compatible(final Entity entity) {
        if (this.player.get_value(true) && entity instanceof EntityPlayer && entity != WurstplusKillAura.mc.field_71439_g && !entity.func_70005_c_().equals(WurstplusKillAura.mc.field_71439_g.func_70005_c_())) {
            return true;
        }
        if (this.hostile.get_value(true) && entity instanceof IMob) {
            return true;
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entity_living_base = (EntityLivingBase)entity;
            if (entity_living_base.func_110143_aJ() <= 0.0f) {
                return false;
            }
        }
        return false;
    }
    
    private boolean checkSharpness(final ItemStack stack) {
        if (stack.func_77978_p() == null) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.func_74745_c()) {
            final NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") == 16) {
                final int lvl = enchant.func_74762_e("lvl");
                if (lvl > 5) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
}
