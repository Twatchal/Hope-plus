package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class WurstplusKillAura
extends WurstplusHack {
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
        this.mode = this.create("Mode", "KillAuraMode", "Normal", this.combobox(new String[]{"A32k", "Normal"}));
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
            this.tick += 1.0;
            if (WurstplusKillAura.mc.field_71439_g.field_70128_L | WurstplusKillAura.mc.field_71439_g.func_110143_aJ() <= 0.0f) {
                return;
            }
            if (this.mode.in("Normal")) {
                if (!(WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && this.sword.get_value(true)) {
                    this.start_verify = false;
                } else if (WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && this.sword.get_value(true)) {
                    this.start_verify = true;
                } else if (!this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                Entity entity = this.find_entity();
                if (entity != null && this.start_verify) {
                    boolean is_possible_attack;
                    float tick_to_hit = 20.0f - Wurstplus.get_event_handler().get_tick_rate();
                    boolean bl = is_possible_attack = WurstplusKillAura.mc.field_71439_g.func_184825_o(this.sync_tps.get_value(true) ? - tick_to_hit : 0.0f) >= 1.0f;
                    if (is_possible_attack) {
                        this.attack_entity(entity);
                    }
                }
            } else {
                if (!(WurstplusKillAura.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
                    return;
                }
                if (this.tick < (double)this.delay.get_value(1)) {
                    return;
                }
                this.tick = 0.0;
                Entity entity = this.find_entity();
                if (entity != null) {
                    this.attack_entity(entity);
                }
            }
        }
    }

    public void attack_entity(Entity entity) {
        ItemStack off_hand_item;
        if (this.mode.in("A32k")) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = WurstplusKillAura.mc.field_71439_g.field_71071_by.func_70301_a(i);
                if (stack == ItemStack.field_190927_a || !this.checkSharpness(stack)) continue;
                newSlot = i;
                break;
            }
            if (newSlot != -1) {
                WurstplusKillAura.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
            }
        }
        if ((off_hand_item = WurstplusKillAura.mc.field_71439_g.func_184592_cb()).func_77973_b() == Items.field_185159_cQ) {
            WurstplusKillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, WurstplusKillAura.mc.field_71439_g.func_174811_aO()));
        }
        WurstplusKillAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
        WurstplusKillAura.mc.field_71439_g.func_184609_a(this.actual_hand);
        WurstplusKillAura.mc.field_71439_g.func_184821_cY();
    }

    public Entity find_entity() {
        Entity entity_requested = null;
        for (Entity player : (List)WurstplusKillAura.mc.field_71441_e.field_73010_i.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend((String)entityPlayer.func_70005_c_())).collect(Collectors.toList())) {
            if (player == null || !this.is_compatible(player) || (double)WurstplusKillAura.mc.field_71439_g.func_70032_d(player) > this.range.get_value(1.0)) continue;
            entity_requested = player;
        }
        return entity_requested;
    }

    public boolean is_compatible(Entity entity) {
        EntityLivingBase entity_living_base;
        if (this.player.get_value(true) && entity instanceof EntityPlayer && entity != WurstplusKillAura.mc.field_71439_g && !entity.func_70005_c_().equals((Object)WurstplusKillAura.mc.field_71439_g.func_70005_c_())) {
            return true;
        }
        if (this.hostile.get_value(true) && entity instanceof IMob) {
            return true;
        }
        if (entity instanceof EntityLivingBase && (entity_living_base = (EntityLivingBase)entity).func_110143_aJ() <= 0.0f) {
            return false;
        }
        return false;
    }

    private boolean checkSharpness(ItemStack stack) {
        if (stack.func_77978_p() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList)stack.func_77978_p().func_74781_a("ench");
        if (enchants == null) {
            return false;
        }
        for (int i = 0; i < enchants.func_74745_c(); ++i) {
            NBTTagCompound enchant = enchants.func_150305_b(i);
            if (enchant.func_74762_e("id") != 16) continue;
            int lvl = enchant.func_74762_e("lvl");
            if (lvl <= 5) break;
            return true;
        }
        return false;
    }
}
