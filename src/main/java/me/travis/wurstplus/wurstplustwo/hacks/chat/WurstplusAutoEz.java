package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import java.util.concurrent.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import net.minecraftforge.event.entity.living.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;

public class WurstplusAutoEz extends WurstplusHack
{
    int delay_count;
    WurstplusSetting discord;
    WurstplusSetting custom;
    private static final ConcurrentHashMap targeted_players;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    @EventHandler
    private Listener<LivingDeathEvent> living_death_listener;
    
    public WurstplusAutoEz() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.delay_count = 0;
        this.discord = this.create("Discord", "EzDiscord", false);
        this.custom = this.create("Custom", "EzCustom", false);
        this.send_listener = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (WurstplusAutoEz.mc.field_71439_g == null) {
                return;
            }
            if (event.get_packet() instanceof CPacketUseEntity) {
                final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.get_packet();
                if (cPacketUseEntity.func_149565_c().equals((Object)CPacketUseEntity.Action.ATTACK)) {
                    final Entity target_entity = cPacketUseEntity.func_149564_a((World)WurstplusAutoEz.mc.field_71441_e);
                    if (target_entity instanceof EntityPlayer) {
                        add_target(target_entity.func_70005_c_());
                    }
                }
            }
        }, new Predicate[0]);
        this.living_death_listener = (Listener<LivingDeathEvent>)new Listener(event -> {
            if (WurstplusAutoEz.mc.field_71439_g == null) {
                return;
            }
            final EntityLivingBase e = event.getEntityLiving();
            if (e == null) {
                return;
            }
            if (e instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)e;
                if (player.func_110143_aJ() <= 0.0f && WurstplusAutoEz.targeted_players.containsKey(player.func_70005_c_())) {
                    this.announce(player.func_70005_c_());
                }
            }
        }, new Predicate[0]);
        this.name = "Auto GG";
        this.tag = "AutoEz";
        this.description = "GG, hope+ owns me and all (\u25cf'\u25e1'\u25cf)";
    }
    
    public void update() {
        for (final Entity entity : WurstplusAutoEz.mc.field_71441_e.func_72910_y()) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)entity;
                if (player.func_110143_aJ() > 0.0f || !WurstplusAutoEz.targeted_players.containsKey(player.func_70005_c_())) {
                    continue;
                }
                this.announce(player.func_70005_c_());
            }
        }
        WurstplusAutoEz.targeted_players.forEach((name, timeout) -> {
            if (timeout <= 0) {
                WurstplusAutoEz.targeted_players.remove(name);
            }
            else {
                WurstplusAutoEz.targeted_players.put(name, timeout - 1);
            }
            return;
        });
        ++this.delay_count;
    }
    
    public void announce(final String name) {
        if (this.delay_count < 150) {
            return;
        }
        this.delay_count = 0;
        WurstplusAutoEz.targeted_players.remove(name);
        String message = "";
        if (this.custom.get_value(true)) {
            message += WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "");
        }
        else {
            message += "GG, hope+ owns me and all (\u25cf'\u25e1'\u25cf)";
        }
        if (this.discord.get_value(true)) {
            message += " - discord.gg/sus";
        }
        WurstplusAutoEz.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(message));
    }
    
    public static void add_target(final String name) {
        if (!Objects.equals(name, WurstplusAutoEz.mc.field_71439_g.func_70005_c_())) {
            WurstplusAutoEz.targeted_players.put(name, 20);
        }
    }
    
    static {
        targeted_players = new ConcurrentHashMap();
    }
}
