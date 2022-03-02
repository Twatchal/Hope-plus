package me.travis.wurstplus.wurstplustwo.hacks.chat;

import com.mojang.realmsclient.gui.*;
import me.travis.wurstplus.wurstplustwo.event.events.*;
import me.zero.alpine.fork.listener.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import me.travis.wurstplus.wurstplustwo.util.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class WurstplusTotempop extends WurstplusHack
{
    public static final HashMap<String, Integer> totem_pop_counter;
    public static ChatFormatting red;
    public static ChatFormatting green;
    public static ChatFormatting gold;
    public static ChatFormatting grey;
    public static ChatFormatting bold;
    public static ChatFormatting reset;
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> packet_event;
    
    public WurstplusTotempop() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.packet_event = (Listener<WurstplusEventPacket.ReceivePacket>)new Listener(event -> {
            if (event.get_packet() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus)event.get_packet();
                if (packet.func_149160_c() == 35) {
                    final Entity entity = packet.func_149161_a((World)WurstplusTotempop.mc.field_71441_e);
                    int count = 1;
                    if (WurstplusTotempop.totem_pop_counter.containsKey(entity.func_70005_c_())) {
                        count = WurstplusTotempop.totem_pop_counter.get(entity.func_70005_c_());
                        WurstplusTotempop.totem_pop_counter.put(entity.func_70005_c_(), ++count);
                    }
                    else {
                        WurstplusTotempop.totem_pop_counter.put(entity.func_70005_c_(), count);
                    }
                    if (entity == WurstplusTotempop.mc.field_71439_g) {
                        return;
                    }
                    if (WurstplusFriendUtil.isFriend(entity.func_70005_c_())) {
                        WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "dude, " + WurstplusTotempop.bold + WurstplusTotempop.green + entity.func_70005_c_() + WurstplusTotempop.reset + " has popped " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. you should go help them");
                    }
                    else {
                        WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "dude, " + WurstplusTotempop.bold + WurstplusTotempop.red + entity.func_70005_c_() + WurstplusTotempop.reset + " has popped " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. what a loser");
                    }
                }
            }
        }, new Predicate[0]);
        this.name = "Totem Pop Counter";
        this.tag = "TotemPopCounter";
        this.description = "dude idk wurst+ is just outdated";
    }
    
    public void update() {
        for (final EntityPlayer player : WurstplusTotempop.mc.field_71441_e.field_73010_i) {
            if (!WurstplusTotempop.totem_pop_counter.containsKey(player.func_70005_c_())) {
                continue;
            }
            if (!player.field_70128_L && player.func_110143_aJ() > 0.0f) {
                continue;
            }
            final int count = WurstplusTotempop.totem_pop_counter.get(player.func_70005_c_());
            WurstplusTotempop.totem_pop_counter.remove(player.func_70005_c_());
            if (player == WurstplusTotempop.mc.field_71439_g) {
                continue;
            }
            if (WurstplusFriendUtil.isFriend(player.func_70005_c_())) {
                WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "dude, " + WurstplusTotempop.bold + WurstplusTotempop.green + player.func_70005_c_() + WurstplusTotempop.reset + " just fucking DIED after popping " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems. RIP :pray:");
            }
            else {
                WurstplusMessageUtil.send_client_message(WurstplusTotempop.red + "" + WurstplusTotempop.bold + " TotemPop " + WurstplusTotempop.reset + WurstplusTotempop.grey + " > " + WurstplusTotempop.reset + "dude, " + WurstplusTotempop.bold + WurstplusTotempop.red + player.func_70005_c_() + WurstplusTotempop.reset + " just fucking DIED after popping " + WurstplusTotempop.bold + count + WurstplusTotempop.reset + " totems");
            }
        }
    }
    
    static {
        totem_pop_counter = new HashMap<String, Integer>();
        WurstplusTotempop.red = ChatFormatting.RED;
        WurstplusTotempop.green = ChatFormatting.GREEN;
        WurstplusTotempop.gold = ChatFormatting.GOLD;
        WurstplusTotempop.grey = ChatFormatting.GRAY;
        WurstplusTotempop.bold = ChatFormatting.BOLD;
        WurstplusTotempop.reset = ChatFormatting.RESET;
    }
}
