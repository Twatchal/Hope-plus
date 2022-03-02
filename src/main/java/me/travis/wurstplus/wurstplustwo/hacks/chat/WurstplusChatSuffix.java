package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import java.util.*;
import me.travis.wurstplus.*;
import net.minecraft.network.play.client.*;

public class WurstplusChatSuffix extends WurstplusHack
{
    WurstplusSetting ignore;
    WurstplusSetting type;
    boolean accept_suffix;
    boolean suffix_default;
    boolean suffix_random;
    StringBuilder suffix;
    String[] random_client_name;
    String[] random_client_finish;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener;
    
    public WurstplusChatSuffix() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.ignore = this.create("Ignore", "ChatSuffixIgnore", true);
        this.type = this.create("Type", "ChatSuffixType", "Default", this.combobox(new String[] { "Default", "6b6t" }));
        this.random_client_name = new String[] { " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+", " | Hope+" };
        this.random_client_finish = new String[] { "", "", "", "", "", "", "", "", "" };
        this.listener = (Listener<WurstplusEventPacket.SendPacket>)new Listener(event -> {
            if (!(event.get_packet() instanceof CPacketChatMessage)) {
                return;
            }
            this.accept_suffix = true;
            final boolean ignore_prefix = this.ignore.get_value(true);
            String message = ((CPacketChatMessage)event.get_packet()).func_149439_c();
            if (message.startsWith("/") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("\\") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("!") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith(":") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith(";") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith(".") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith(",") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("@") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("&") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("*") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("$") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("#") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith("(") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (message.startsWith(")") && ignore_prefix) {
                this.accept_suffix = false;
            }
            if (this.type.in("Default")) {
                this.suffix_default = true;
                this.suffix_random = false;
            }
            if (this.type.in("6b6t")) {
                this.suffix_default = false;
                this.suffix_random = true;
            }
            if (this.accept_suffix) {
                if (this.suffix_default) {
                    message = message + " " + this.convert_base("| \u029c\u1d0f\u1d18\u1d07+");
                }
                if (this.suffix_random) {
                    final StringBuilder suffix_with_randoms = new StringBuilder();
                    suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_name)));
                    suffix_with_randoms.append(this.convert_base(this.random_string(this.random_client_finish)));
                    message = message + " " + suffix_with_randoms.toString();
                }
                if (message.length() >= 256) {
                    message.substring(0, 256);
                }
            }
            ((CPacketChatMessage)event.get_packet()).field_149440_a = message;
        }, new Predicate[0]);
        this.name = "Chat Suffix";
        this.tag = "ChatSuffix";
        this.description = "show off how cool u are";
    }
    
    public String random_string(final String[] list) {
        return list[new Random().nextInt(list.length)];
    }
    
    public String convert_base(final String base) {
        return Wurstplus.smoth(base);
    }
    
    public String array_detail() {
        return this.type.get_current_value();
    }
}
