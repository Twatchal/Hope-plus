package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import java.text.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;
import me.travis.wurstplus.wurstplustwo.util.*;

public final class WurstplusChatMods extends WurstplusHack
{
    WurstplusSetting timestamps;
    WurstplusSetting dateformat;
    WurstplusSetting name_highlight;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> PacketEvent;
    
    public WurstplusChatMods() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.timestamps = this.create("Timestamps", "ChatModsTimeStamps", true);
        this.dateformat = this.create("Date Format", "ChatModsDateFormat", "24HR", this.combobox(new String[] { "24HR", "12HR" }));
        this.name_highlight = this.create("Name Highlight", "ChatModsNameHighlight", true);
        this.PacketEvent = (Listener<WurstplusEventPacket.ReceivePacket>)new Listener(event -> {
            if (event.get_packet() instanceof SPacketChat) {
                final SPacketChat packet = (SPacketChat)event.get_packet();
                if (packet.func_148915_c() instanceof TextComponentString) {
                    final TextComponentString component = (TextComponentString)packet.func_148915_c();
                    if (this.timestamps.get_value(true)) {
                        String date = "";
                        if (this.dateformat.in("12HR")) {
                            date = new SimpleDateFormat("h:mm a").format(new Date());
                        }
                        if (this.dateformat.in("24HR")) {
                            date = new SimpleDateFormat("k:mm").format(new Date());
                        }
                        component.field_150267_b = "§7[" + date + "]§r " + component.field_150267_b;
                    }
                    String text = component.func_150254_d();
                    if (text.contains("combat for")) {
                        return;
                    }
                    if (this.name_highlight.get_value(true) && WurstplusChatMods.mc.field_71439_g != null && text.toLowerCase().contains(WurstplusChatMods.mc.field_71439_g.func_70005_c_().toLowerCase())) {
                        text = text.replaceAll("(?i)" + WurstplusChatMods.mc.field_71439_g.func_70005_c_(), ChatFormatting.GOLD + WurstplusChatMods.mc.field_71439_g.func_70005_c_() + ChatFormatting.RESET);
                    }
                    event.cancel();
                    WurstplusMessageUtil.client_message(text);
                }
            }
        }, new Predicate[0]);
        this.name = "Chat Modifications";
        this.tag = "ChatModifications";
        this.description = "this breaks things";
    }
}
