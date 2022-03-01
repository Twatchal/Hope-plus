package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.*;
import com.mojang.realmsclient.gui.*;
import me.travis.wurstplus.wurstplustwo.hacks.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;

public class WurstplusMessageUtil
{
    public static final Minecraft mc;
    public static ChatFormatting g;
    public static ChatFormatting b;
    public static ChatFormatting a;
    public static ChatFormatting r;
    public static String opener;
    
    public static void toggle_message(final WurstplusHack module) {
        if (module.is_active()) {
            if (module.get_tag().equals("AutoCrystal")) {
                client_message_simple(WurstplusMessageUtil.opener + "§rAutocrystal §7toggled" + ChatFormatting.DARK_GREEN + " §aon");
            }
            else {
                client_message_simple(WurstplusMessageUtil.opener + WurstplusMessageUtil.r + module.get_name() + ChatFormatting.DARK_GREEN + " §7toggled §aon");
            }
        }
        else if (module.get_tag().equals("AutoCrystal")) {
            client_message_simple(WurstplusMessageUtil.opener + "§rAutocrystal §7toggled" + ChatFormatting.RED + " §coff" + WurstplusMessageUtil.r + "");
        }
        else {
            client_message_simple(WurstplusMessageUtil.opener + WurstplusMessageUtil.r + module.get_name() + ChatFormatting.RED + " §7toggled §coff");
        }
    }
    
    public static void client_message_simple(final String message) {
        if (WurstplusMessageUtil.mc.field_71439_g != null) {
            final ITextComponent itc = new TextComponentString(message).func_150255_a(new Style().func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("ez"))));
            WurstplusMessageUtil.mc.field_71456_v.func_146158_b().func_146234_a(itc, 5936);
        }
    }
    
    public static void client_message(final String message) {
        if (WurstplusMessageUtil.mc.field_71439_g != null) {
            WurstplusMessageUtil.mc.field_71439_g.func_145747_a((ITextComponent)new WurstplusMessageUtil.ChatMessage(message));
        }
    }
    
    public static void send_client_message_simple(final String message) {
        client_message(ChatFormatting.GOLD + "§9Hope §8»" + " " + WurstplusMessageUtil.r + message);
    }
    
    public static void send_client_message(final String message) {
        client_message(ChatFormatting.GOLD + "§9Hope §8»" + " " + WurstplusMessageUtil.r + message);
    }
    
    public static void send_client_error_message(final String message) {
        client_message(ChatFormatting.RED + "§4Hope §8»" + " " + WurstplusMessageUtil.r + message);
    }
    
    static {
        mc = Minecraft.func_71410_x();
        WurstplusMessageUtil.g = ChatFormatting.GOLD;
        WurstplusMessageUtil.b = ChatFormatting.BLUE;
        WurstplusMessageUtil.a = ChatFormatting.DARK_AQUA;
        WurstplusMessageUtil.r = ChatFormatting.RESET;
        WurstplusMessageUtil.opener = WurstplusMessageUtil.g + "§9Hope §8»" + ChatFormatting.GRAY + " " + WurstplusMessageUtil.r;
    }
}
