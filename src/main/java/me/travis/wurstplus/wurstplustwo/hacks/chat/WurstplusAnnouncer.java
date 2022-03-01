package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import java.text.*;
import net.minecraft.util.math.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import java.util.*;
import me.travis.wurstplus.*;
import net.minecraft.network.*;
import java.math.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import java.util.concurrent.*;

public class WurstplusAnnouncer extends WurstplusHack
{
    WurstplusSetting min_distance;
    WurstplusSetting max_distance;
    WurstplusSetting delay;
    WurstplusSetting queue_size;
    WurstplusSetting units;
    WurstplusSetting movement_string;
    WurstplusSetting suffix;
    WurstplusSetting smol;
    private static DecimalFormat df;
    private static final Queue<String> message_q;
    private static final Map<String, Integer> mined_blocks;
    private static final Map<String, Integer> placed_blocks;
    private static final Map<String, Integer> dropped_items;
    private static final Map<String, Integer> consumed_items;
    private boolean first_run;
    private static Vec3d thisTickPos;
    private static Vec3d lastTickPos;
    private static int delay_count;
    private static double distanceTraveled;
    private static float thisTickHealth;
    private static float lastTickHealth;
    private static float gainedHealth;
    private static float lostHealth;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_listener;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener;
    
    public WurstplusAnnouncer() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.min_distance = this.create("Min Distance", "AnnouncerMinDist", 12, 1, 100);
        this.max_distance = this.create("Max Distance", "AnnouncerMaxDist", 144, 12, 1200);
        this.delay = this.create("Delay Seconds", "AnnouncerDelay", 4, 0, 20);
        this.queue_size = this.create("Queue Size", "AnnouncerQueueSize", 5, 1, 20);
        this.units = this.create("Units", "AnnouncerUnits", "Meters", this.combobox(new String[] { "Meters", "Feet", "Yards", "Inches" }));
        this.movement_string = this.create("Movement", "AnnouncerMovement", "Aha x", this.combobox(new String[] { "Aha x", "Leyta", "FUCK" }));
        this.suffix = this.create("Suffix", "AnnouncerSuffix", true);
        this.smol = this.create("Small Text", "AnnouncerSmallText", false);
        this.receive_listener = (Listener<WurstplusEventPacket.ReceivePacket>)new Listener(event -> {
            if (WurstplusAnnouncer.mc.field_71441_e == null) {
                return;
            }
            if (event.get_packet() instanceof SPacketUseBed) {
                this.queue_message("I am going to bed now, goodnight");
            }
        }, new Predicate[0]);
        this.send_listener = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (WurstplusAnnouncer.mc.field_71441_e == null) {
                return;
            }
            if (event.get_packet() instanceof CPacketPlayerDigging) {
                final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.get_packet();
                if (WurstplusAnnouncer.mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_190931_a && (packet.func_180762_c().equals((Object)CPacketPlayerDigging.Action.DROP_ITEM) || packet.func_180762_c().equals((Object)CPacketPlayerDigging.Action.DROP_ALL_ITEMS))) {
                    final String name = WurstplusAnnouncer.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r();
                    if (WurstplusAnnouncer.dropped_items.containsKey(name)) {
                        WurstplusAnnouncer.dropped_items.put(name, WurstplusAnnouncer.dropped_items.get(name) + 1);
                    }
                    else {
                        WurstplusAnnouncer.dropped_items.put(name, 1);
                    }
                }
                if (packet.func_180762_c().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                    final String name = WurstplusAnnouncer.mc.field_71441_e.func_180495_p(packet.func_179715_a()).func_177230_c().func_149732_F();
                    if (WurstplusAnnouncer.mined_blocks.containsKey(name)) {
                        WurstplusAnnouncer.mined_blocks.put(name, WurstplusAnnouncer.mined_blocks.get(name) + 1);
                    }
                    else {
                        WurstplusAnnouncer.mined_blocks.put(name, 1);
                    }
                }
            }
            else {
                if (event.get_packet() instanceof CPacketUpdateSign) {
                    this.queue_message("I just updated a sign with some epic text");
                }
                if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
                    final ItemStack stack = WurstplusAnnouncer.mc.field_71439_g.field_71071_by.func_70448_g();
                    if (stack.func_190926_b()) {
                        return;
                    }
                    if (stack.func_77973_b() instanceof ItemBlock) {
                        final String name = WurstplusAnnouncer.mc.field_71439_g.field_71071_by.func_70448_g().func_82833_r();
                        if (WurstplusAnnouncer.placed_blocks.containsKey(name)) {
                            WurstplusAnnouncer.placed_blocks.put(name, WurstplusAnnouncer.placed_blocks.get(name) + 1);
                        }
                        else {
                            WurstplusAnnouncer.placed_blocks.put(name, 1);
                        }
                        return;
                    }
                    if (stack.func_77973_b() == Items.field_185158_cP) {
                        final String name = "Crystals";
                        if (WurstplusAnnouncer.placed_blocks.containsKey(name)) {
                            WurstplusAnnouncer.placed_blocks.put(name, WurstplusAnnouncer.placed_blocks.get(name) + 1);
                        }
                        else {
                            WurstplusAnnouncer.placed_blocks.put(name, 1);
                        }
                    }
                }
            }
        }, new Predicate[0]);
        this.name = "Announcer";
        this.tag = "Announcer";
        this.description = "how to get muted 101";
    }
    
    public void update() {
        if (WurstplusAnnouncer.mc.field_71439_g == null || WurstplusAnnouncer.mc.field_71441_e == null) {
            this.set_disable();
            return;
        }
        try {
            this.get_tick_data();
        }
        catch (Exception ignored) {
            this.set_disable();
            return;
        }
        this.send_cycle();
    }
    
    private void get_tick_data() {
        WurstplusAnnouncer.lastTickPos = WurstplusAnnouncer.thisTickPos;
        WurstplusAnnouncer.thisTickPos = WurstplusAnnouncer.mc.field_71439_g.func_174791_d();
        WurstplusAnnouncer.distanceTraveled += WurstplusAnnouncer.thisTickPos.func_72438_d(WurstplusAnnouncer.lastTickPos);
        WurstplusAnnouncer.lastTickHealth = WurstplusAnnouncer.thisTickHealth;
        WurstplusAnnouncer.thisTickHealth = WurstplusAnnouncer.mc.field_71439_g.func_110143_aJ() + WurstplusAnnouncer.mc.field_71439_g.func_110139_bj();
        final float healthDiff = WurstplusAnnouncer.thisTickHealth - WurstplusAnnouncer.lastTickHealth;
        if (healthDiff < 0.0f) {
            WurstplusAnnouncer.lostHealth += healthDiff * -1.0f;
        }
        else {
            WurstplusAnnouncer.gainedHealth += healthDiff;
        }
    }
    
    public void send_cycle() {
        ++WurstplusAnnouncer.delay_count;
        if (WurstplusAnnouncer.delay_count > this.delay.get_value(1) * 20) {
            WurstplusAnnouncer.delay_count = 0;
            this.composeGameTickData();
            this.composeEventData();
            final Iterator<String> iterator = WurstplusAnnouncer.message_q.iterator();
            if (iterator.hasNext()) {
                final String message = iterator.next();
                this.send_message(message);
                WurstplusAnnouncer.message_q.remove(message);
            }
        }
    }
    
    private void send_message(String s) {
        if (this.suffix.get_value(true)) {
            final String i = " - ";
            s = s + i + Wurstplus.smoth("powered by hope+");
        }
        if (this.smol.get_value(true)) {
            s = Wurstplus.smoth(s.toLowerCase());
        }
        WurstplusAnnouncer.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(s.replaceAll("§", "")));
    }
    
    public void queue_message(final String m) {
        if (WurstplusAnnouncer.message_q.size() > this.queue_size.get_value(1)) {
            return;
        }
        WurstplusAnnouncer.message_q.add(m);
    }
    
    protected void enable() {
        this.first_run = true;
        (WurstplusAnnouncer.df = new DecimalFormat("#.#")).setRoundingMode(RoundingMode.CEILING);
        final Vec3d pos = WurstplusAnnouncer.thisTickPos = (WurstplusAnnouncer.lastTickPos = WurstplusAnnouncer.mc.field_71439_g.func_174791_d());
        WurstplusAnnouncer.distanceTraveled = 0.0;
        final float health = WurstplusAnnouncer.thisTickHealth = (WurstplusAnnouncer.lastTickHealth = WurstplusAnnouncer.mc.field_71439_g.func_110143_aJ() + WurstplusAnnouncer.mc.field_71439_g.func_110139_bj());
        WurstplusAnnouncer.lostHealth = 0.0f;
        WurstplusAnnouncer.gainedHealth = 0.0f;
        WurstplusAnnouncer.delay_count = 0;
    }
    
    public static double round(final double unrounded, final int precision) {
        final BigDecimal bd = new BigDecimal(unrounded);
        final BigDecimal rounded = bd.setScale(precision, 4);
        return rounded.doubleValue();
    }
    
    private void composeGameTickData() {
        if (this.first_run) {
            this.first_run = false;
            return;
        }
        if (WurstplusAnnouncer.distanceTraveled >= 1.0) {
            if (WurstplusAnnouncer.distanceTraveled < this.delay.get_value(1) * this.min_distance.get_value(1)) {
                return;
            }
            if (WurstplusAnnouncer.distanceTraveled > this.delay.get_value(1) * this.max_distance.get_value(1)) {
                WurstplusAnnouncer.distanceTraveled = 0.0;
                return;
            }
            final CharSequence sb = new StringBuilder();
            if (this.movement_string.in("Aha x")) {
                ((StringBuilder)sb).append("aha x, I just traveled ");
            }
            if (this.movement_string.in("FUCK")) {
                ((StringBuilder)sb).append("FUCK, I just fucking traveled ");
            }
            if (this.movement_string.in("Leyta")) {
                ((StringBuilder)sb).append("leyta bitch, I just traveled ");
            }
            if (this.units.in("Feet")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 3.2808, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Foot");
                }
                else {
                    ((StringBuilder)sb).append(" Feet");
                }
            }
            if (this.units.in("Yards")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 1.0936, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Yard");
                }
                else {
                    ((StringBuilder)sb).append(" Yards");
                }
            }
            if (this.units.in("Inches")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled * 39.37, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Inch");
                }
                else {
                    ((StringBuilder)sb).append(" Inches");
                }
            }
            if (this.units.in("Meters")) {
                ((StringBuilder)sb).append(round(WurstplusAnnouncer.distanceTraveled, 2));
                if ((int)WurstplusAnnouncer.distanceTraveled == 1.0) {
                    ((StringBuilder)sb).append(" Meter");
                }
                else {
                    ((StringBuilder)sb).append(" Meters");
                }
            }
            this.queue_message(sb.toString());
            WurstplusAnnouncer.distanceTraveled = 0.0;
        }
        if (WurstplusAnnouncer.lostHealth != 0.0f) {
            final CharSequence sb = "HECK! I just lost " + WurstplusAnnouncer.df.format(WurstplusAnnouncer.lostHealth) + " health D:";
            this.queue_message((String)sb);
            WurstplusAnnouncer.lostHealth = 0.0f;
        }
        if (WurstplusAnnouncer.gainedHealth != 0.0f) {
            final CharSequence sb = "#ezmode I now have " + WurstplusAnnouncer.df.format(WurstplusAnnouncer.gainedHealth) + " more health";
            this.queue_message((String)sb);
            WurstplusAnnouncer.gainedHealth = 0.0f;
        }
    }
    
    private void composeEventData() {
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.mined_blocks.entrySet()) {
            this.queue_message("We be mining " + kv.getValue() + " " + kv.getKey() + " out here");
            WurstplusAnnouncer.mined_blocks.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.placed_blocks.entrySet()) {
            this.queue_message("We be placing " + kv.getValue() + " " + kv.getKey() + " out here");
            WurstplusAnnouncer.placed_blocks.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.dropped_items.entrySet()) {
            this.queue_message("I just dropped " + kv.getValue() + " " + kv.getKey() + ", whoops!");
            WurstplusAnnouncer.dropped_items.remove(kv.getKey());
        }
        for (final Map.Entry<String, Integer> kv : WurstplusAnnouncer.consumed_items.entrySet()) {
            this.queue_message("NOM NOM, I just ate " + kv.getValue() + " " + kv.getKey() + ", yummy");
            WurstplusAnnouncer.consumed_items.remove(kv.getKey());
        }
    }
    
    static {
        WurstplusAnnouncer.df = new DecimalFormat();
        message_q = new ConcurrentLinkedQueue<String>();
        mined_blocks = new ConcurrentHashMap<String, Integer>();
        placed_blocks = new ConcurrentHashMap<String, Integer>();
        dropped_items = new ConcurrentHashMap<String, Integer>();
        consumed_items = new ConcurrentHashMap<String, Integer>();
    }
}
